import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AlertsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

    private JButton lowStockButton = new JButton("Low Stock");
    private JButton expiringButton = new JButton("Expiring Items");
    private JTextField lowStockField = new JTextField(10);
    private JTextField expiringField = new JTextField(10);
    private JButton backButton = new JButton("Back");
    
	public AlertsFrame (Component parent) {
        setTitle("Alerts");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new GridBagLayout());

        addItem(insidePanel, new JLabel("Show stock with quantity less than: "), 0,0,1,1, GridBagConstraints.EAST);
        addItem(insidePanel, lowStockField,1,0,1,1, GridBagConstraints.CENTER);
        addItem(insidePanel, lowStockButton, 2,0,1,1, GridBagConstraints.WEST);

        addItem(insidePanel, new JLabel("Show items expiring in how many days: "), 0,1,1,1, GridBagConstraints.EAST);
        addItem(insidePanel, expiringField, 1,1,1,1, GridBagConstraints.CENTER);
        addItem(insidePanel, expiringButton, 2,1,1,1,GridBagConstraints.WEST);

        addItem(insidePanel,backButton, 2,3,1,1,GridBagConstraints.SOUTH);

        lowStockButton.addActionListener(this);
        expiringButton.addActionListener(this);
        backButton.addActionListener(this);

        add(insidePanel);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			this.dispose();
		}
		else if(e.getSource() == lowStockButton) {
			try {
				String query = "Select inventory.item_id, item_description.name From inventory " 
						     + "inner join item_description on inventory.item_id = item_description.item_id "
						     + "Where inventory.quantity_onhand < " + lowStockField.getText() + " " 
						     + "Order by inventory.quantity_onhand DESC";
				ResultSet rs = DatabaseFactory.executeQuery(query);
				new AlertsResultsFrame(this, rs);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "Please input a number into the text field");
			}
		}
		else if(e.getSource() == expiringButton) {
			try {
				String query = "Select item_expiration.item_id, item_description.name "
						     + "From item_expiration "
						     + "inner join item_description on item_expiration.item_id = item_description.item_id "
						     + "Where item_expiration.exp_date >= DATE_ADD( now(), interval + " + expiringField.getText() + " day) "
						     + "Group by item_expiration.exp_date ASC";
				ResultSet rs = DatabaseFactory.executeQuery(query);
				new AlertsResultsFrame(this, rs);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "Please input a number into the text field");
			}
		}
	}

	private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align)
	{//addItem() sets parameters to shorten the addition of adding the panels to the frame.
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = align;
		gc.fill = GridBagConstraints.NONE;
		p.add(c, gc);
	}

}
