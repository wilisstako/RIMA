//******************************************************************************************************************/
/*File name:                                                                                                        *
/*Author:        Abimbola Otugalu                                                                                   *
/*Creation date:                                                                                                    *
/*Last Modified:                                                                                                    *
/*Project:                                                                                                          *
/*Purporse:                                                                                                         *
/*Assumption:                                                                                                       *
/*                                                                                                                  *
//******************************************************************************************************************/
package Controller;

import db2.DatabaseFactory;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author aotugalu
 */
public class OrdersController implements Initializable {

    @FXML
    private BorderPane orderPane;
    @FXML
    private TableView<Orderspojo> orderTable;
    @FXML
    private TableColumn<Orderspojo, String> type;
    @FXML
    private TableColumn<Orderspojo, String> name;
    @FXML
    private TableColumn<Orderspojo, String> poc;
    @FXML
    private TableColumn<Orderspojo, String> date;
    @FXML
    private TableColumn<Orderspojo, String> item;
    @FXML
    private TableColumn<Orderspojo, Double> amt_ordered;
    @FXML
    private TableColumn<Orderspojo, Double> total_billed;

     private ObservableList<OrdersController.Orderspojo> data;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        poc.setCellValueFactory(new PropertyValueFactory<>("poc"));
        item.setCellValueFactory(new PropertyValueFactory<>("item"));
        amt_ordered.setCellValueFactory(new PropertyValueFactory<>("amtOrdered"));
        total_billed.setCellValueFactory(new PropertyValueFactory<>("totalBilled"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        showTable();
    }    
     
    public void showTable() {
        String query = "select 'Customer' AS 'Type', customer.last  || ', ' || customer.first as 'Name',  'N/A ' as 'POC', customer_order.date "
                + "AS 'DATE', item_description.name as 'Item Ordered', customer_order.quantity_ordered as 'Amount Ordered', "
                + "customer_bill.total_billed as 'Total Billed (includes all items)'\n"
                + "from (((customer inner join customer_order ON customer.customer_id = customer_order.customer_id) inner join "
                + "customer_bill on customer.customer_id = customer_bill.customer_bill_id) "
                + "inner join item_description on customer_order.item_id = item_description.item_id) \n"
                + "group by customer_order.date \n"          
                + "union\n"             
                + "select 'Vendor' AS 'Type', vendor.company_name as 'Name', vendor.company_poc as 'POC', "
                + "vendor_order.date AS 'DATE', item_description.name as 'Item Ordered',vendor_order.quantity_ordered "
                + "as 'Amount Ordered', vendor_invoice.total_billed as 'Total Billed (includes all items)'\n"
                + "from (((vendor inner join vendor_order ON vendor.vendor_id = vendor_order.vendor_id) inner join "
                + "vendor_invoice on vendor.vendor_id = vendor_invoice.vendor_invoice_id) inner join item_description "
                + "on vendor_order.item_id = item_description.item_id) \n"
                + "group by vendor_order.date;";

        ResultSet rs = null;
        rs = DatabaseFactory.executeQuery(query);

        data = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                OrdersController.Orderspojo inv = new OrdersController.Orderspojo();
                
                inv.type.set(rs.getString("Type"));
                inv.name.set(rs.getString("Name"));
                inv.poc.set(rs.getString("POC"));
                inv.item.set(rs.getString("Item Ordered"));
                inv.date.set(rs.getString("Date"));
                inv.amtOrdered.set(rs.getDouble("Amount Ordered"));
                inv.totalBilled.set(rs.getDouble("Total Billed (includes all items)"));
                System.out.println("Total: " + rs.getDouble("Total Billed (includes all items)") );
                data.add(inv);
            }
            orderTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText("Error Loading Data");
            msg.showAndWait();
        }
    }

    @FXML
    private void sortOrders(SortEvent<Orderspojo> event) {
    }
    
    public class Orderspojo {

        private SimpleStringProperty type = new SimpleStringProperty();
        private SimpleStringProperty poc = new SimpleStringProperty();
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty date = new SimpleStringProperty();
        private SimpleStringProperty item = new SimpleStringProperty();
        private SimpleDoubleProperty amtOrdered = new SimpleDoubleProperty();
        private SimpleDoubleProperty totalBilled = new SimpleDoubleProperty();

        public String getType() {
            return type.get();
        }
        
        public String getItem() {
            return item.get();
        }

        public String getPoc() {
            return poc.get();
        }

        public String getName() {
            return name.get();
        }

        public String getDate() {
            return date.get();
        }

        public Double getAmtOrdered(){
            return amtOrdered.get();
        }
        
        public Double getTotalBilled(){
            return totalBilled.get();
        }
        
        public void setType(String a) {
            type.set(a);
        }

        public void setItem(String a) {
           item.set(a);
        }

        public void setPOC(String a) {
           poc.set(a);
        }

        public void setName(String a) {
           name.set(a);
        }

        public void setDate(String a) {
           date.set(a);
        }

        public void setAmount(Double d) {
           amtOrdered.set(d);
        }

        public void setTotal(Double d) {
            totalBilled.set(d);
        }

    }

    
}
