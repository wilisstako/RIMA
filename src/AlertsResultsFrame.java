import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AlertsResultsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private ResultSet resultSet;
	private JTable inventoryTable;
	private JButton backButton;
	private DefaultTableModel model;
	
	private int colCount;
	private Vector<String> headers;
	private Vector<Vector<Object>> data;
	
	public AlertsResultsFrame (Component parent, ResultSet rs) {
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setTitle("Results");
		
		this.resultSet = rs;
		
		try {
			//get column names
			colCount = resultSet.getMetaData().getColumnCount();
			headers = new Vector<String>();
			for(int col = 1; col < colCount+1; col++) {
				headers.add(resultSet.getMetaData().getColumnName(col));
			}
			
			//get data
			data = new Vector<Vector<Object>>();
			while(resultSet.next()) {
				Vector<Object> vector = new Vector<Object>();
				for(int i = 1; i < colCount+1; i++) {
					vector.add(rs.getObject(i));
				}
				data.add(vector);
				
				model = new DefaultTableModel(data, headers);
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
		inventoryTable = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane);
        
        // add buttons to frame
        JPanel controlsPanel = new JPanel();
		backButton = new JButton("Back");
		backButton.addActionListener(this);
        controlsPanel.add(backButton);
        
        add(controlsPanel, BorderLayout.SOUTH);
        
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			this.dispose();
		}
		
	}

}
