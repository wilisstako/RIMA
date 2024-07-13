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
import java.sql.SQLException;
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
public class AccountspayableController implements Initializable {

    @FXML
    private BorderPane payablePane;
    @FXML
    private TableView<Paypojo> payableTable;
    @FXML
    private TableColumn<Paypojo, String> name;
    @FXML
    private TableColumn<Paypojo, Integer> invoice_no;
    @FXML
    private TableColumn<Paypojo, Double> amt_due;
    @FXML
    private TableColumn<Paypojo, Double> amt_pd;
    @FXML
    private TableColumn<Paypojo, Double>balance;
    @FXML
    private TableColumn<Paypojo, String> date;

    private ObservableList<AccountspayableController.Paypojo> data;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialzes cells
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        invoice_no.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        amt_due.setCellValueFactory(new PropertyValueFactory<>("amtDue"));
        amt_pd.setCellValueFactory(new PropertyValueFactory<>("amtPaid"));
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        //load data into table
        showTable();
    }

    public void showTable() {
        String query = "select vendor.company_name AS 'Company Paid', invoice_payment.vendor_invoice_id as 'Invoice No.', \n"
                            + "invoice_payment.bill_total as 'Amount Due', invoice_payment.total_debit as 'Amount Paid', invoice_payment.balance as\n"
                            + "'Balance', invoice_payment.payment_date as 'Payment Date'\n"
                            + "from ((invoice_payment inner join vendor_invoice on invoice_payment.vendor_invoice_id = vendor_invoice.vendor_invoice_id)\n"
                            + "inner join vendor on vendor_invoice.vendor_id = vendor.vendor_id)\n"
                            + "group by invoice_payment.vendor_invoice_id\n"
                            + "order by invoice_payment.vendor_invoice_id;";

        ResultSet rs = null;
        rs = DatabaseFactory.executeQuery(query);

        data = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                AccountspayableController.Paypojo inv = new AccountspayableController.Paypojo();
                inv.name.set(rs.getString("Company Paid"));
                inv.invoiceNo.set(rs.getString("Invoice No."));
                inv.amtDue.set(rs.getDouble("Amount Due"));
                inv.amtPaid.set(rs.getDouble("Amount Paid"));
                inv.balance.set(rs.getDouble("Balance"));
                inv.date.set(rs.getString("Payment Date"));
        
                data.add(inv);
            }
            payableTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText("Error Loading Data");
            msg.showAndWait();
        }
    }

    @FXML
    private void sortPayable(SortEvent<Paypojo> event) {
    }

    public class Paypojo {

        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleStringProperty invoiceNo = new SimpleStringProperty();
        private final SimpleDoubleProperty amtDue = new SimpleDoubleProperty();
        private final SimpleStringProperty date = new SimpleStringProperty();
        private final SimpleDoubleProperty amtPaid = new SimpleDoubleProperty();
        private final SimpleDoubleProperty balance = new SimpleDoubleProperty();

        public String getInvoiceNo() {
            return invoiceNo.get();
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

        public Double getAmtPaid() {
            return amtPaid.get();
        }
        
        public Double getBalance() {
            return balance.get();
        }
        
        public void setInvoiceNo(String a) {
           invoiceNo.set(a);
        }

        public void setName(String a) {
           name.set(a);
        }

        public void setDate(String a) {
           date.set(a);
        }

        public void setAmtDue(Double a) {
           amtDue.set(a);
        }

        public void setAmtPaid(Double a) {
           amtPaid.set(a);
        }

        public void setBalance(Double a) {
           balance.set(a);
        }

    }


}
