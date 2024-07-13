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

public class SearchResultsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Vector<String> colNames = new Vector<String>();
	private Vector<String> colTypes = new Vector<String>();
	private Vector<Object> data = new Vector<Object>();
	private int colCount;
	private DefaultTableModel model;
	private JButton backButton;
	
	public SearchResultsFrame(Component parent, String tableName, String searchInfo) {
		setSize(800, 600);
		setLocationRelativeTo(parent);
		setTitle("Search Results");
		setLayout(new BorderLayout());
		
		// get column names
		try {
			//String query = "select column_name, column_type from information_schema.columns where table_name='" + tableName + "'";
			String query = "select * from master." + tableName;
			ResultSet rs = DatabaseFactory.executeQuery(query);
			colCount = rs.getMetaData().getColumnCount();
			
			for(int i = 0; i < colCount; i++) {
				colNames.addElement(rs.getMetaData().getColumnName(i+1));
				colTypes.addElement(rs.getMetaData().getColumnTypeName(i+1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//search each column and add and entry to data Vector if match is found
		try {
			for(int i = 0; i < colNames.size(); i++) {
				String query = "select * from master." + tableName + " where " + colNames.get(i) 
							 +  " like '%" + searchInfo + "%'";
				ResultSet rs = DatabaseFactory.executeQuery(query);
				while(rs.next()) {
					Vector<Object> vector = new Vector<Object>();
					for(int j = 1; j < colCount+1; j++) {
						vector.add(rs.getObject(j));
					}
					data.add(vector);
				}
			}
			if(data.size() == 0) {
				JOptionPane.showMessageDialog(this, "No Records Found");
				return;
			}
					
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// add table to JFrame
		JTable dataTable = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
        
        //add back button
        JPanel buttonPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		
	}

}
