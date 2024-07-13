import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TablesFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	JButton inventoryButton;
	JButton vendorsButton;
	JButton ordersButton;
	JButton reportsButton;
	JButton accountsPayableButton;
	JButton usersButton;
	JButton customersButton;
	JButton salesButton;
	JButton searchButton;
	JButton accountsReceivableButton;
	JButton alertsButton;
	JButton logoutButton;
	
	public TablesFrame() {
		setTitle("Tables");
		setSize(600, 400);
		//setLayout(new GridLayout(5, 2, 15, 15));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(100, 100);
        
        JPanel outsidePanel = new JPanel();
        // add some padding
        outsidePanel.setBorder(new EmptyBorder(15,15,15,15));
        
        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new GridLayout(6, 2, 15, 15));
        
        //instantiate button and add action listeners
        inventoryButton = new JButton("Inventory");
        inventoryButton.addActionListener(this);
        insidePanel.add(inventoryButton);
        usersButton = new JButton("Users");
        usersButton.addActionListener(this);
        insidePanel.add(usersButton);
        vendorsButton = new JButton("Vendors");
        vendorsButton.addActionListener(this);
        insidePanel.add(vendorsButton);
        customersButton = new JButton("Customers");
        customersButton.addActionListener(this);
        insidePanel.add(customersButton);
        ordersButton = new JButton("Orders");
        ordersButton.addActionListener(this);
        insidePanel.add(ordersButton);
        salesButton = new JButton("Sales");
        salesButton.addActionListener(this);
        insidePanel.add(salesButton);
        reportsButton = new JButton("Reports");
        reportsButton.addActionListener(this);
        insidePanel.add(reportsButton);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        insidePanel.add(searchButton);
        accountsPayableButton = new JButton("Accounts Payable");
        accountsPayableButton.addActionListener(this);
        insidePanel.add(accountsPayableButton);
        accountsReceivableButton = new JButton("Accounts Receivable");
        accountsReceivableButton.addActionListener(this);
        insidePanel.add(accountsReceivableButton);
        alertsButton = new JButton("Alerts");
        alertsButton.addActionListener(this);
        insidePanel.add(alertsButton);
        logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(this);
        insidePanel.add(logoutButton);
        
        outsidePanel.add(insidePanel);
        
        add(outsidePanel);
        
        setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//check which button was clicked
		if(e.getSource() == inventoryButton) {
			ResultSet rs = null;
			try {
				String query = "Select item_description.name AS 'Name', \n"
                        + "item_description.paragraph AS 'Description', inventory.cost AS 'Cost', inventory.quantity_onhand AS 'Stock Quantity', vendor.company_name AS\n"
                        + "'Supplier'\n"
                        + "From ((inventory INNER JOIN item_description ON inventory.item_id = item_description.item_id)\n"
                        + "INNER JOIN vendor ON inventory.vendor_id = vendor.vendor_id)\n"
                        + "WHERE inventory.quantity_onhand >= 0\n"
                        + "Order BY inventory.item_id, item_description.name, item_description.paragraph, inventory.quantity_onhand;";
				rs = DatabaseFactory.executeQuery(query);
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "inventory");
		}
		
		//this is to do, here we will fire new frames based on what button is pressed
		else if(e.getSource() == vendorsButton) {
			ResultSet rs = null;
			try {
				rs = DatabaseFactory.executeQuery("SELECT * FROM vendor");
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "vendor");
		}
		else if (e.getSource() == ordersButton) {
			ResultSet rs = null;
			try {
				rs = DatabaseFactory.executeQuery("SELECT * FROM vendor_order");
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "vendor_order");
			
		}
		else if(e.getSource() == reportsButton) {
			new ReportsFrame(this);
		}
		else if(e.getSource() == accountsPayableButton) {
			ResultSet rs = null;
			try {
				
				String query = "select vendor.company_name AS 'Company Paid', invoice_payment.vendor_invoice_id as 'Invoice No.', \n" +
                        "invoice_payment.bill_total as 'Amount Due', invoice_payment.total_debit as 'Amount Paid', invoice_payment.balance as\n" +
                        "'Balance', invoice_payment.payment_date as 'Payment Date'\n" +
                        "from ((invoice_payment inner join vendor_invoice on invoice_payment.vendor_invoice_id = vendor_invoice.vendor_invoice_id)\n" +
                        "inner join vendor on vendor_invoice.vendor_id = vendor.vendor_id)\n" +
                        "group by invoice_payment.vendor_invoice_id\n" +
                        "order by invoice_payment.vendor_invoice_id;";
                        
				//String query = "select * from vendor_order";
				rs = DatabaseFactory.executeQuery(query);
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "vendor_invoice");
		}
		else if(e.getSource() == usersButton) {
			ResultSet rs = null;
			try {
				rs = DatabaseFactory.executeQuery("SELECT * FROM users");
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "users");
		}
		else if(e.getSource() == customersButton) {
			ResultSet rs = null;
			try {
				rs = DatabaseFactory.executeQuery("SELECT * FROM customer");
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "Customer");
		}
		else if(e.getSource() == salesButton) {
			ResultSet rs = null;
			try {
				rs = DatabaseFactory.executeQuery("SELECT * FROM customer_order");
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "customer_order");
		}
		else if(e.getSource() == searchButton) {
			new SearchFrame(this);
		}
		else if(e.getSource() == accountsReceivableButton) {
			ResultSet rs = null;
			try {
				
				String query = "select concat (customer.last,', ', customer.first) as 'Customer Name', bill_payment.customer_bill_id as 'Receipt No.', \n" +
                        "customer_bill.total_billed as 'Amount Due', bill_payment.total_paid as 'Amount Received', bill_payment.payment_type as\n" +
                        "'Payment Type', bill_payment.payment_date as 'Payment Date'\n" +
                        "from ((bill_payment inner join customer_bill on customer_bill.customer_bill_id  = bill_payment.customer_bill_id)\n" +
                        "inner join customer on customer_bill.customer_id = customer.customer_id)\n" +
                        "group by customer_bill.customer_bill_id\n" +
                        "order by customer_bill.customer_bill_id;";
                        
				//String query = "select * from customer_invoice";
				rs = DatabaseFactory.executeQuery(query);
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			new ShowTableFrame(this, rs, "customer_bill");
		}
		else if(e.getSource() == alertsButton) {
			new AlertsFrame(this);
		}
		else if(e.getSource() == logoutButton) {
			this.dispose();
			System.gc();
			for (Window window : Window.getWindows()) {
			    window.dispose();
			}
			new LoginFrame();
		}
		
		
	}
	

}
