import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private String tableName;
    private Vector<String> labels;
    private Vector<String> headerLabels;
    private Vector<JTextField> textAreas;
    private ResultSet resultSet;

    private JButton addButton;
    private JButton cancelButton;

    public AddFrame(Component parent, Vector<String> labels, Vector<String> headerLabels, String table, ResultSet rs) {
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        this.labels = labels;
        this.headerLabels = headerLabels;
        tableName = table;
        resultSet = rs;

        JPanel outsidePanel = new JPanel();
        // add some padding
        outsidePanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel insidePanel = null;
        if(tableName.equals("vendor_order")) {
        	insidePanel = createOrderPanel();
        }
        else if(tableName.equals("customer_order")) {
        	insidePanel = createSalePanel();
        }
        else {
        	insidePanel = createPanelDirect();
        }

        outsidePanel.add(insidePanel);

        add(outsidePanel, BorderLayout.CENTER);

        // buttons panel
        JPanel buttonsPanel = new JPanel();
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonsPanel.add(addButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonsPanel.add(cancelButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            if(tableName.equals("vendor_order")) {
            	addOrder();
            }
            else if (tableName.equals("customer_order")) {
            	addSale();
            }
            else {
            	addDirect();
            }
        } 
        else if (e.getSource() == cancelButton) {
            this.dispose();
        }

    }
    
    private JPanel createOrderPanel() {
    	//add additional labels for additional entries into multiple tables
    	//4
    	labels.remove(4);
    	labels.add("Vendor Name");
    	labels.add("total_cost");
    	labels.add("expiration_date");
    	labels.add("due_date");
    	
    	return createPanelDirect();
    }
    
    private JPanel createSalePanel() {
    	//add additional labels for additional entries into multiple tables
    	labels.add("total_cost");
    	
    	return createPanelDirect();
    }
    
    private JPanel createPanelDirect() {
    	JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new GridLayout(labels.size(), 2, 15, 15));

        textAreas = new Vector<>();

        for (int i = 0; i < labels.size(); i++) {
            insidePanel.add(new JLabel(labels.get(i)));
            JTextField field = new JTextField(10);
            if (i == 0) {
                field.setEditable(false);
            }
            textAreas.add(field);
            insidePanel.add(textAreas.get(i));
        }
        return insidePanel;
    }
    
    private void addDirect() {
    	String statement = "INSERT INTO " + tableName + " (";
        String fields = "";
        for (int i = 1; i < labels.size(); i++) {
            fields += labels.get(i);
            if (i < labels.size() - 1) {
                fields += ", ";
            }
        }
        statement += fields + ") values(";
        String data = "";
        for (int i = 1; i < textAreas.size(); i++) {
            String type = "";
            String name = "";
            try {
                type = resultSet.getMetaData().getColumnTypeName(i + 1);
                name = resultSet.getMetaData().getColumnName(i + 1);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            if (type.equals("VARCHAR")) {
                data += "\"" + textAreas.get(i).getText() + "\"";
                if(name.equals("state")) {
                	if(textAreas.get(i).getText().length() != 2) {
                		JOptionPane.showMessageDialog(this, "State must be two characters");
                		return;
                	}
                }
            } else if (type.equals("DATE")) {
            	
            	String date = textAreas.get(i).getText();
            	if(!checkDate(date)) {
            		data += "'" + date + "'";
            		JOptionPane.showMessageDialog(this,"Invalid Date Format. Please Enter Date As: yyyy-MM-DD");
            		return;
            	}
	    
            data += textAreas.get(i).getText();
            } else if (type.equals("INT")) {
                data += textAreas.get(i).getText();
                if(name.equals("zip_code")) {
                	if(textAreas.get(i).getText().length() != 5) {
                		JOptionPane.showMessageDialog(this, "Zip Code must be 5 digits");
                		return;
                	}
                }
            }
            if (i < textAreas.size() - 1) {
                data += ", ";
            }
        }
        statement += data + ")";
        try {
            DatabaseFactory.executeStatement(statement);
            JOptionPane.showMessageDialog(this, "Item added successfully");
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(this, "Error:\n" + e1.getMessage());
        }
    }
    
    private void addOrder() {
    	//boolean success = false;
    	
    	String itemID = textAreas.get(1).getText();
    	String dateOrdered = textAreas.get(2).getText();
    	String quantityOrdered = textAreas.get(3).getText();
    	String vendorID = textAreas.get(4).getText();
    	String totalCost = textAreas.get(5).getText();
    	String expirationDate = textAreas.get(6).getText();
    	String dateDue = textAreas.get(7).getText();
    	String newKey = "";
    	
    	if(!checkDate(dateOrdered) || !checkDate(expirationDate) || !checkDate(dateDue)) {
    		JOptionPane.showMessageDialog(this, "Invalid Date Format. Please Enter Date As: yyyy-MM-DD");
    		return;
    	}

    	//insert into vendor_order
    	//find vendor_id
    	String query = "select vendor_id from vendor where company_name = '" + vendorID + "'";
    	int id;
    	try {
			ResultSet rs = DatabaseFactory.executeQuery(query);
			if(rs.next()) {
				id = rs.getInt("vendor_id");
				vendorID = Integer.toString(id);
			} 
			else {
				JOptionPane.showMessageDialog(this, "Vendor name not found");
				return;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Vendor name not found");
			return;
		}
    	
    	String statement = "insert into vendor_order (item_id, date_ordered, quantity_ordered, vendor_id) "
    					 + "values (" + itemID + ",'" +  dateOrdered.toString() + "', " 
    					 + quantityOrdered + ", " + vendorID + ")";
    	try {
            newKey = DatabaseFactory.getKey(statement);
            JOptionPane.showMessageDialog(this, "Item added successfully to vendor_order");
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(this, "Error:\n" + e1.getMessage());
        }
    	
    	//insert into inventory
    	statement = "";
    	statement = "insert inventory (quantity_onhand, cost, item_id, vendor_id) "
    			  + "values(" + quantityOrdered + ", " + totalCost + ", "
    			  + itemID + ", " + vendorID + ")";
    	try {
            DatabaseFactory.executeStatement(statement);
            JOptionPane.showMessageDialog(this, "Item added successfully to inventory");
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(this, "Error:\n" + e1.getMessage());
        }
        
    	
    	//create new vendor_invoice
    	statement = "insert into vendor_invoice (vendor_id, date_created, date_due, total_billed, vendor_order_id) "
    			         + "values (" + vendorID + ",'" + dateOrdered + "', " + "'" + dateDue + "', "
    			         + totalCost + ", " + newKey + ")";
    	System.out.println(statement);
    	try {
            DatabaseFactory.executeStatement(statement);
            JOptionPane.showMessageDialog(this, "New vendor invoice created");
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(this, "Error:\n" + e1.getMessage());
        }
    }
    
    private void addSale() {
    	String itemID = textAreas.get(1).getText();
    	String dateOrdered = textAreas.get(2).getText();
    	String quantityOrdered = textAreas.get(3).getText();
    	String customerID = textAreas.get(4).getText();
    	String totalCost = textAreas.get(5).getText();
    	String customerOrderKey = "";
    	int totalLeftToPurchase;
    	
    	if(!checkDate(dateOrdered)) {
    		JOptionPane.showMessageDialog(this, "Invalid Date Format. Please Enter Date As: yyyy-MM-DD");
    		return;
    	}
    	
    	// check inventory to see if the item_id is available
    	int quantityAvailable = 0;
    	int quantityLeft = 0;
    	ResultSet rs;
    	try {
    		totalLeftToPurchase = Integer.parseInt(quantityOrdered);
			rs = DatabaseFactory.executeQuery("select * from inventory where item_id=" + itemID);
			while(rs.next()) {
				quantityAvailable += rs.getInt("quantity_onhand");
			} 
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error:\n" + e.getMessage());
			return;
		}
    	if(quantityAvailable < Integer.parseInt(quantityOrdered)) {
    		JOptionPane.showMessageDialog(this, "No enough quantity in current inventory to complete sale.  Total available " + quantityAvailable);
    		return;
    	}
    	else {
    		try {
				rs = DatabaseFactory.executeQuery("select * from inventory where item_id=" + itemID + " order by item_id");
				while(rs.next()) {
					int inventoryID = rs.getInt("inventory_id");
					int quantityInRecord = rs.getInt("quantity_onhand");
					if(quantityInRecord > totalLeftToPurchase) {
						String statement = "update inventory "
										 + "set quantity_onhand=" + (quantityInRecord - totalLeftToPurchase)
										 + " where inventory_id=" + inventoryID;
						DatabaseFactory.executeStatement(statement);
						break;
					}
					else if(quantityInRecord == totalLeftToPurchase) {
						String statement = "delete from inventory where inventory_id=" + Integer.toString(inventoryID);
						DatabaseFactory.executeStatement(statement);
						break;
					}
					else {
						totalLeftToPurchase -= quantityInRecord;
						String statement = "delete from inventory where inventory_id=" + Integer.toString(inventoryID);
						DatabaseFactory.executeStatement(statement);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Error:\n" + e.getMessage());
				return;
			}
    		JOptionPane.showMessageDialog(this, "Inventory table updated");
    	}
    	
    	
    	//insert into customer_order
    	String statement = "insert into customer_order (item_id, date_ordered, quantity_ordered, customer_id) "
    					 + "values(" + itemID + ", '" + dateOrdered + "', " + quantityOrdered + ", " + customerID + ")"; 
    	try {
    		customerOrderKey = DatabaseFactory.getKey(statement);
            JOptionPane.showMessageDialog(this, "Item added successfully to customer_order");
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(this, "Error:\n" + e1.getMessage());
        }
    	
    	
    	//insert into customer_bill
    	
    	statement = "insert into customer_bill (date_created, total_billed, customer_order_id, customer_id) "
    					 + "values('" + dateOrdered + "', " + totalCost + ", " + customerOrderKey
    					 + ", " + customerID + ")";
    	try {
			DatabaseFactory.executeStatement(statement);
			JOptionPane.showMessageDialog(this, "Customer bill created");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error:\n" + e.getMessage());
		}
		
    }
    
    private boolean checkDate(String input) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	     try {
	          format.parse(input);
	          return true;
	     }
	     catch(ParseException e){
	          return false;
	     }
	}

}
