import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("Search");
    private JButton backButton = new JButton("Back");

    private JRadioButton rdBtnInventory = new JRadioButton("Inventory");
    private JRadioButton rdBtnUsers = new JRadioButton("Users");
    private JRadioButton rdBtnVendors = new JRadioButton("Vendors");
    private JRadioButton rdBtnCustomers = new JRadioButton("Customers");
    private JRadioButton rdBtnOrders = new JRadioButton("Orders");
    private JRadioButton rdBtnSales = new JRadioButton("Sales");
    private JRadioButton rdBtnAcctRx= new JRadioButton("Accounts Receivable");
    private JRadioButton rdBtnAcctPay = new JRadioButton("Accounts Payable");

    private JLabel space = new JLabel("     ");
    SearchFrame(Component parent){
        setTitle("Search");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        
        backButton.addActionListener(this);
        searchButton.addActionListener(this);

        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new GridBagLayout());

        Box box1 = Box.createVerticalBox();
        ButtonGroup group1 = new ButtonGroup();
        group1.add(rdBtnInventory);
        group1.add(rdBtnUsers);
        group1.add(rdBtnVendors);
        group1.add(rdBtnCustomers);
        group1.add(rdBtnOrders);
        group1.add(rdBtnSales);

        box1.add(rdBtnInventory);
        box1.add(rdBtnUsers);
        box1.add(rdBtnVendors);
        box1.add(rdBtnCustomers);
        box1.add(rdBtnOrders);
        box1.add(rdBtnSales);

        addItem(insidePanel, box1, 0,0,2,2, GridBagConstraints.NORTH);

        Box box2 = Box.createVerticalBox();
        ButtonGroup group2 = new ButtonGroup();
        box2.add(rdBtnAcctRx);
        box2.add(rdBtnAcctPay);

        group2.add(rdBtnAcctRx);
        group2.add(rdBtnAcctPay);

        addItem(insidePanel, box2, 2,0,2,1, GridBagConstraints.NORTH);

        addItem(insidePanel, searchField, 2,0,1,1, GridBagConstraints.CENTER);

        Box box3 = Box.createHorizontalBox();
        ButtonGroup group3 = new ButtonGroup();
        box3.add(searchButton);
        box3.add(space);
        box3.add(backButton);

        group3.add(searchButton);
        group3.add(backButton);
        addItem(insidePanel, box3, 2,3,1,1, GridBagConstraints.CENTER);

        add(insidePanel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == backButton) {
    		this.dispose();
    	}
    	else if(e.getSource() == searchButton) {
    		String tableName = "";
    		String searchInfo = "";
	        if (rdBtnInventory.isSelected()){
	        	tableName = "inventory";
	        }
	        else if (rdBtnUsers.isSelected()){
	        	tableName = "users";
	        }
	        else if (rdBtnVendors.isSelected()){
	        	tableName = "vendor";
	        }
	        else if (rdBtnCustomers.isSelected()){
	        	tableName = "customer";
	        }
	        else if (rdBtnOrders.isSelected()){
	        	tableName = "vendor_order";
	        }
	        else if (rdBtnSales.isSelected()){
	        	tableName = "customer_order";
	        }
	        else if (rdBtnAcctRx.isSelected()){
	        	tableName = "customer_invoice";
	        }
	        else if (rdBtnAcctPay.isSelected()){
	        	tableName = "vendor_invoice";
	        }
	        else {
	        	JOptionPane.showMessageDialog(this, "No Table Selected");
	        	return;
	        }
	        searchInfo = searchField.getText();
	        if(searchInfo.equals("")) {
	        	JOptionPane.showMessageDialog(this, "No Search Criteria Entered");
	        	return;
	        }
	        
	        new SearchResultsFrame(this, tableName, searchInfo);
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
