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
public class AccountsreceivableController implements Initializable {

    @FXML
    private BorderPane a_receivable_pane;
    @FXML
    private TableView<Recpojo> aReceivableTable;
    @FXML
    private TableColumn<Recpojo, String> name;
    @FXML
    private TableColumn<Recpojo, String> receipt_no;
    @FXML
    private TableColumn<Recpojo, Double> amt_due;
    @FXML
    private TableColumn<Recpojo, Double> amt_rec;
    @FXML
    private TableColumn<Recpojo, String> typePayment;
    @FXML
    private TableColumn<Recpojo, String> date;

    private ObservableList<AccountsreceivableController.Recpojo> data;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        receipt_no.setCellValueFactory(new PropertyValueFactory<>("receiptNo"));
        amt_due.setCellValueFactory(new PropertyValueFactory<>("amtDue"));
        amt_rec.setCellValueFactory(new PropertyValueFactory<>("amtRec"));
        typePayment.setCellValueFactory(new PropertyValueFactory<>("typePayment"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        showTable();
    }

    public void showTable() {
        String query = "select customer.last || ', ' || customer.first as 'Customer Name', bill_payment.customer_bill_id as 'Receipt No.', \n" +
                            "customer_bill.total_billed as 'Amount Due', bill_payment.total_paid as 'Amount Received', bill_payment.payment_type as\n" +
                            "'Payment Type', bill_payment.payment_date as 'Payment Date'\n" +
                            "from ((bill_payment inner join customer_bill on customer_bill.customer_bill_id  = bill_payment.customer_bill_id)\n" +
                            "inner join customer on customer_bill.customer_id = customer.customer_id)\n" +
                            "group by customer_bill.customer_bill_id\n" +
                            "order by customer_bill.customer_bill_id;";

        ResultSet rs = null;

        try {
            rs = DatabaseFactory.executeQuery(query);
            data = FXCollections.observableArrayList();

            while (rs.next()) {
                AccountsreceivableController.Recpojo inv = new AccountsreceivableController.Recpojo();
                inv.name.set(rs.getString("Customer Name"));
                inv.receiptNo.set(rs.getString("Receipt No."));
                inv.amtDue.set(rs.getDouble("Amount Due"));
                inv.amtRec.set(rs.getDouble("Amount Received"));
                inv.typePayment.set(rs.getString("Payment Type"));
                inv.date.set(rs.getString("Payment Date"));

                data.add(inv);
            }
            aReceivableTable.setItems(data);
        }catch (Exception e) {
            e.printStackTrace();
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText("Error Loading Data");
            msg.showAndWait();
        }
    }

    @FXML
    private void sortReceivable(SortEvent<Recpojo> event) {
    }

    public class Recpojo {
        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleStringProperty receiptNo = new SimpleStringProperty();
        private final SimpleDoubleProperty amtDue = new SimpleDoubleProperty();
        private final SimpleStringProperty date = new SimpleStringProperty();
        private final SimpleDoubleProperty amtRec = new SimpleDoubleProperty();
        private final SimpleStringProperty typePayment = new SimpleStringProperty();

        public String getReceiptNo() {
            return receiptNo.get();
        }

        public String getName() {
            return name.get();
        }

        public String getDate() {
            return date.get();
        }

        public Double getAmtDue() {
            return amtDue.get();
        }

        public Double getAmtRec() {
            return amtRec.get();
        }
        
        public String getTypePayment() {
            return typePayment.get();
        }
        
        public void setReceiptNo(String a) {
            receiptNo.set(a);
        }

        public void setName(String a) {
            name.set(a);
        }

        public void setDate(String a) {
            date.set(a);
        }

        public void setAmtDue(Double d) {
            amtDue.set(d);
        }

        public void setAmtRec(Double d) {
            amtRec.set(d);
        }

        public void setTypePayment(String a) {
            typePayment.set(a);
        }

    }


}
