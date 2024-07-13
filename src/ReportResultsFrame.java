import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportResultsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int colCount;
	private Vector<String> colNames = new Vector<String>();
	private Vector<String> colTypes = new Vector<String>();
	private Vector<Vector<Object>> data;
	private DefaultTableModel model;
	private JTable dataTable;
	private JButton backButton;
	
	public ReportResultsFrame(Component parent, String tableName, String query) {
		
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setTitle(tableName);
		setLayout(new BorderLayout());
		
		try {
			ResultSet rs = DatabaseFactory.executeQuery(query);
			colCount = rs.getMetaData().getColumnCount();
			
			for(int i = 0; i < colCount; i++) {
				colNames.addElement(rs.getMetaData().getColumnName(i+1));
				colTypes.addElement(rs.getMetaData().getColumnTypeName(i+1));
			}

			data = new Vector<Vector<Object>>();
			while(rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for(int i = 1; i < colCount+1; i++) {
					vector.add(rs.getObject(i));
				}
				data.add(vector);
				
				model = new DefaultTableModel(data, colNames);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String report ="";
		if(data.size() == 0) {
			report = "No Data To Show";
			add(new JLabel(report), BorderLayout.NORTH);
		}
		dataTable = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
        
        //back button panel
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
