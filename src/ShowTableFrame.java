import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ShowTableFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private ResultSet resultSet;
	private JTable inventoryTable;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton backButton;
	private DefaultTableModel model;
	private String tableName;
	
	private int colCount;
	private Vector<String> headers;
	private Vector<Vector<Object>> data;
	private Vector<String> headerLabels;
	
	public ShowTableFrame(Component parent, ResultSet rs, String tableName) {
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		this.tableName = tableName;
		setTitle(tableName);
		
		this.resultSet = rs;
		
		try {
			//get column names
			colCount = resultSet.getMetaData().getColumnCount();
			headers = new Vector<String>();
			headerLabels = new Vector<String>();
			for(int col = 1; col < colCount+1; col++) {
				headers.add(resultSet.getMetaData().getColumnName(col));
				headerLabels.add(resultSet.getMetaData().getColumnLabel(col));
			}
			
			//get data
			data = new Vector<Vector<Object>>();
			while(resultSet.next()) {
				Vector<Object> vector = new Vector<Object>();
				for(int i = 1; i < colCount+1; i++) {
					vector.add(rs.getObject(i));
				}
				data.add(vector);
				
				model = new DefaultTableModel(data, headerLabels);
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
		//String[] headers = new String[]{"ID", "Quantity", "Cost", "Item ID", "Vendor ID" };
		
		inventoryTable = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane);
        
        // add buttons to frame
        JPanel controlsPanel = new JPanel();
        addButton = new JButton("Add");
		addButton.addActionListener(this);
		editButton = new JButton("Edit");
		editButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		if(tableName.equals("inventory") || tableName.equals("customer_bill") || tableName.equals("vendor_invoice")) {
			addButton.setEnabled(false);
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
        controlsPanel.add(addButton);
        controlsPanel.add(editButton);
        controlsPanel.add(deleteButton);
        controlsPanel.add(backButton);
        
        add(controlsPanel, BorderLayout.SOUTH);
        
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton) {
			new AddFrame(this, headers, headerLabels, tableName, resultSet);
		}
		else if(e.getSource() == editButton) {
			//get the data from the selected row
			int rowNumber = inventoryTable.getSelectedRow();
			Vector<Object> data = new Vector<Object>();
			for(int column = 0; column < colCount; column++) {
				data.add(inventoryTable.getValueAt(rowNumber, column));
			}
			new EditFrame(this, headers, tableName, data, resultSet);
			 
			 
		}
		else if(e.getSource() == deleteButton) {
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you want to delete?",
				    "Confirm",
				    JOptionPane.YES_NO_OPTION);
			System.out.println(n);
			if(n == 0) {
				int rowNumber = inventoryTable.getSelectedRow();
				String statement = "DELETE FROM master." + tableName + " WHERE ";
				statement += headers.get(0) + "=" + inventoryTable.getValueAt(rowNumber, 0);
				//System.out.println(statement);
				
				try {
					DatabaseFactory.executeStatement(statement);
					JOptionPane.showMessageDialog(this, "Item deleted successfully");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "Error:\n" + e1.getMessage());
				}
			}
					
		}
		else if(e.getSource() == backButton) {
			this.dispose();
		}
		
	}

}
