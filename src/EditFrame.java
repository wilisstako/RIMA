import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EditFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String tableName;
	private Vector<String> labels;
	private Vector<JTextField> textAreas;
	private Vector<Object> data;
	private ResultSet resultSet;
	
	private JButton editButton;
	private JButton cancelButton;
	
	public EditFrame(Component parent, Vector<String> labels, String table, Vector<Object> data, ResultSet rs) {
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);
		this.labels = labels;
		tableName = table;
		this.data = data;
		resultSet = rs;
		
		JPanel outsidePanel = new JPanel();
        // add some padding
        outsidePanel.setBorder(new EmptyBorder(15,15,15,15));
        
        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new GridLayout(labels.size(), 2, 15, 15));
        
        textAreas = new Vector<JTextField>();
        
        for(int i = 0; i < labels.size(); i++) {
        	insidePanel.add(new JLabel(labels.get(i)));
        	JTextField field = new JTextField((String) data.get(i).toString());
        	if(i == 0) {
        		field.setEditable(false);
        	}
        	textAreas.add(field);
        	insidePanel.add(textAreas.get(i));
        }
        
        outsidePanel.add(insidePanel);
        
        add(outsidePanel, BorderLayout.CENTER);
        
        // buttons panel
        JPanel buttonsPanel = new JPanel();
        editButton = new JButton("Edit");
        editButton.addActionListener(this);
        buttonsPanel.add(editButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonsPanel.add(cancelButton);
        
        add(buttonsPanel, BorderLayout.SOUTH);
        
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == editButton) {
			
			String statement = "";
			for(int i = 1; i < labels.size(); i++) {
				statement = "";
				if(!textAreas.get(i).getText().equals(data.get(i).toString())) {
					statement += "UPDATE " + tableName + " SET ";
					statement += labels.get(i) + "=";
					
					String type = "";
					try {
						type = resultSet.getMetaData().getColumnTypeName(i+1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if( type.equals("VARCHAR")) {
						statement += "\"" + textAreas.get(i).getText() + "\"";
					}
					else if(type.equals("DATE")) {
						
						String date = textAreas.get(i).getText();
							if(!checkDate(date)) {
			            		JOptionPane.showMessageDialog(this,"Invalid Date Format. Please Enter Date As: yyyy-MM-DD");
			            		return;
			            	}
						/*This code formats to the corrected date
                       				 DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                   				Date date = null;
                    				try {
                        			date = (Date) formatter.parse(textAreas.get(i).getText());
                   				} catch (ParseException ex) {
                      	   			JOptionPane.showMessageDialog(null,"Unable to Format Date");
                    	   			}
                 				statement += "'" + date.toString() + "'";
		    		    		*/
	           		    	        /*This code just throws an error message claiming incorrect format
		  				DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                   		                formatter.setLenient(false);
		 			        try {
                    		                Date date= formatter.parse(textAreas.get(i).getText());
				                statement += "'" + date.toString() + "'";
                   	                        } catch (ParseException e) {
                                                JOptionPane.showMessageDialog(null,"Invalid Date Format. Please Enter Date As: yyyy-MM-DD");
                  		                }
		    			
		                                */
						//statement += "'" + textAreas.get(i).getText() + "'";
						statement += "'" + date + "'";
					}
					else if(type.equals("INT")) {
						statement += textAreas.get(i).getText();
					}
					
					statement += " WHERE " + labels.get(0) +"=" + data.get(0);
					System.out.println(statement);
					try {
						DatabaseFactory.executeStatement(statement);
						JOptionPane.showMessageDialog(this, "Item updated successfully");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(this, "Error:\n" + e1.getMessage());
					}
				}

			}
			
		}
		
		else if(e.getSource() == cancelButton) {
			this.dispose();
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
