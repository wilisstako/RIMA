import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton reportButton = new JButton("Report");
    private JButton backButton = new JButton("Back");

    private JRadioButton rdBtnNeedInventory = new JRadioButton("Needed Inventory");
    private JRadioButton rdBtnExpItem = new JRadioButton("Expiring Items");
    private JRadioButton rdBtnTotalOrders = new JRadioButton("Total Orders");
    private JRadioButton rdBtnTotalSales = new JRadioButton("Total Sales");
    private JRadioButton rdBtnNewCustomers = new JRadioButton("New Customers");
    private JRadioButton rdBtnCurrentWeek = new JRadioButton("Current Week");
    private JRadioButton rdBtnCurrentMonth = new JRadioButton("Current Month");
    private JRadioButton rdBtnCurrentYear = new JRadioButton("Current Year");
    private JRadioButton rdBtn12Months = new JRadioButton("Last 12 Months");

    private JLabel space = new JLabel("     ");
    
    ReportsFrame(Component parent){
        setTitle("Reports");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        
        backButton.addActionListener(this);
        reportButton.addActionListener(this);

        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new GridBagLayout());

        Box queryBox = Box.createVerticalBox();
        ButtonGroup queryGroup = new ButtonGroup();
        queryGroup.add(rdBtnNeedInventory);
        queryGroup.add(rdBtnExpItem);
        queryGroup.add(rdBtnTotalOrders);
        queryGroup.add(rdBtnTotalSales);
        queryGroup.add(rdBtnNewCustomers);


        queryBox.add(rdBtnNeedInventory);
        queryBox.add(rdBtnExpItem);
        queryBox.add(rdBtnTotalOrders);
        queryBox.add(rdBtnTotalSales);
        queryBox.add(rdBtnNewCustomers);


        addItem(insidePanel, queryBox, 0,0,2,2, GridBagConstraints.NORTH);

        Box timeBox = Box.createVerticalBox();
        ButtonGroup timeGroup = new ButtonGroup();
        timeBox.add(rdBtnCurrentWeek);
        timeBox.add(rdBtnCurrentMonth);
        timeBox.add(rdBtnCurrentYear);
        timeBox.add(rdBtn12Months);


        timeGroup.add(rdBtnCurrentWeek);
        timeGroup.add(rdBtnCurrentMonth);
        timeGroup.add(rdBtnCurrentYear);
        timeGroup.add(rdBtn12Months);

        addItem(insidePanel, timeBox, 2,0,2,1, GridBagConstraints.NORTH);

        Box box3 = Box.createHorizontalBox();
        ButtonGroup group3 = new ButtonGroup();
        box3.add(reportButton);
        box3.add(space);
        box3.add(backButton);

        group3.add(reportButton);
        group3.add(backButton);
        addItem(insidePanel, box3, 2,3,1,1, GridBagConstraints.CENTER);

        add(insidePanel);
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    	String tableName;
    	Date firstDate;
    	Date lastDate;
    	String query;
    	boolean skipDate = false;
    	long DAY_IN_MS = 1000 * 60 * 60 * 24;
    	
    	if(e.getSource() == backButton) {
    		this.dispose();
    	}
    	else if(e.getSource() == reportButton) {
        
            if (rdBtnNeedInventory.isSelected()){
            	tableName = "inventory";
            	query = "select inventory.inventory_id, inventory.quantity_onhand, item_description.name "
            		  +	"from inventory "
            		  +	"inner join item_description on inventory.item_id = item_description.item_id "
            		  +	"where inventory.quantity_onhand < 2";
            	skipDate = true;
            }
            else if (rdBtnExpItem.isSelected()){
            	tableName = "inventory";
            	query = "select a.inventory_id, b.name, c.exp_date "
            		  +	"from inventory a "
            		  +	"inner join item_description b on a.item_id = b.item_id "
            		  + "inner join item_expiration c on b.item_exp_id = c.item_exp_id "
            		  +	"where c.exp_date between '";
            }
            else if (rdBtnTotalOrders.isSelected()){
            	tableName = "vendor_order ";
            	query = "select a.date_ordered, b.name, c.cost, a.quantity_ordered, round(c.cost * a.quantity_ordered, 2) as total, round((@runtot := @runtot + (c.cost * a.quantity_ordered)), 2) AS running_total "
              		  +	"from vendor_order a "
              		  +	"inner join item_description b on a.item_id = b.item_id "
              		  +	"inner join inventory c on a.item_id = c.item_id "
              		  +	"where a.date_ordered between '";
            }
            else if (rdBtnTotalSales.isSelected()){
            	tableName = "customer_order";
            	query = "select a.date_ordered, b.name, c.cost, a.quantity_ordered, round(c.cost * a.quantity_ordered, 2) as total, round((@runtot := @runtot + (c.cost * a.quantity_ordered)), 2) AS running_total "
            		  +	"from customer_order a "
            		  +	"inner join item_description b on a.item_id = b.item_id "
            		  +	"inner join inventory c on a.item_id = c.item_id "
            		  +	"where a.date_ordered between '";
            }
            else if (rdBtnNewCustomers.isSelected()){
            	tableName = "Customer";
            	query = "select * from customer order by customer_id limit 10";
            	skipDate = true;
            } 
            else {
            	JOptionPane.showMessageDialog(this, "No Table Selected");
	        	return;
            }
            if(!skipDate) {
	            if (rdBtnCurrentWeek.isSelected()){
	            	firstDate = new Date();
	            	lastDate = new Date(firstDate.getTime() + (7 * DAY_IN_MS));
	            	query += getMysqlDate(firstDate) + "' and '" + getMysqlDate(lastDate) + "'";
	            }
	            else if (rdBtnCurrentMonth.isSelected()){
	            	firstDate = new Date();
	            	lastDate = new Date(firstDate.getTime() + (30 * DAY_IN_MS));
	            	query += getMysqlDate(firstDate) + "' and '" + getMysqlDate(lastDate) + "'";
	            }
	            else if (rdBtnCurrentYear.isSelected()){
	            	firstDate = new Date();
	            	lastDate = new Date(firstDate.getTime() + (365 * DAY_IN_MS));
	            	query += getMysqlDate(firstDate) + "' and '" + getMysqlDate(lastDate) + "'";
	            }
	            else if (rdBtn12Months.isSelected()){
	            	firstDate = new Date();
	            	lastDate = new Date(firstDate.getTime() - (365 * DAY_IN_MS));
	            	query += getMysqlDate(lastDate) + "' and '" + getMysqlDate(firstDate) + "'";
	            }
	            else {
	            	JOptionPane.showMessageDialog(this, "No Date Selected");
		        	return;
	            }
            }

            new ReportResultsFrame(this, tableName, query);
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
    
    private String getMysqlDate(Date d) {
    	String pattern = "yyyy-MM-dd";
    	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(d);
    }
}
