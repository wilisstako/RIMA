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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author aotugalu
 */
public class AddpaymentController implements Initializable {

    @FXML
    private Button addPayment;
    @FXML
    private TextField invoiceNo;
    @FXML
    private TextField paymentType;
    @FXML
    private TextField idNo;
   
    @FXML
    private TextField amountPd;
    @FXML
    private DatePicker paymentDate;
    @FXML
    private DatePicker billDate;
    @FXML
    private CheckBox customer;
    @FXML
    private CheckBox vendor;
    
    private Double total, balance;
    private String dateCreated;
    @FXML
    private DialogPane diaPane;
    
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void storePayment(ActionEvent event) {
        balance = balance - Double.parseDouble(amountPd.getText());
        if(customer.isSelected()){
            String query = "insert into Bill_Payment(customer_id, customer_bill_id, payment_date, total_paid, payment_type) values"
                    + "('" + idNo.getText() + "','" + invoiceNo.getText() + "','" + paymentDate.getValue().toString() +"','" + Double.parseDouble(amountPd.getText())
                    + "','" + paymentType.getText() + "');";
            DatabaseFactory.executeStatement(query);
        }else if (vendor.isSelected()){
            String query = "insert into Invoice_Payment(vendor_invoice_id, payment_date, bill_total, total_debit, balance, date_due, date_created"
                    + " ) values"
                    + "('" + invoiceNo.getText() + "','" + paymentDate.getValue().toString() +"','" + total +"','" + Double.parseDouble(amountPd.getText())
                    + "','" + balance +"','" + billDate.getValue().toString() + "','" + dateCreated + "');";
            DatabaseFactory.executeStatement(query);   
        }else{
            Alert alertMsg = new Alert(Alert.AlertType.ERROR);
            alertMsg.setTitle("!!!!!!!Selection Missing!!!!!!!!!");
            alertMsg.setContentText("Please Check Customer or Vendor Below");
            alertMsg.showAndWait();            
        }
       
        
    }


    @FXML
    private void storeInvoiceID(ActionEvent event) {
        if (customer.isSelected()) {
            String query = "Select customer_id AS 'ID', total_billed AS 'Total' from customer_bill where customer_bill.customer_bill_id =" + invoiceNo.getText() + ";";
            ResultSet rs = DatabaseFactory.executeQuery(query);
            try {
                idNo.setText(rs.getString("ID"));
                total = rs.getDouble("Total");
            } catch (SQLException ex) {
                idNo.setText("N/A");
                Alert alertMsg = new Alert(Alert.AlertType.ERROR);
                alertMsg.setTitle("!!!!!!!!Error!!!!!!!!!");
                alertMsg.setContentText("No Customer Associated With This Bill");
                alertMsg.showAndWait();
            }
        } else if (vendor.isSelected()) {
            String query = "Select vendor_id AS 'ID', total_billed AS 'Total', date_created AS 'Date'"
                    + " from vendor_invoice where vendor_invoice.vendor_invoice_id = " + invoiceNo.getText() + ";";
            ResultSet rs = DatabaseFactory.executeQuery(query);
            try {
                idNo.setText(rs.getString("ID"));
                total = rs.getDouble("Total");
                dateCreated = rs.getString("Date");
            } catch (SQLException ex) {
                idNo.setText("N/A");
                Alert alertMsg = new Alert(Alert.AlertType.ERROR);
                alertMsg.setTitle("!!!!!!!!Error!!!!!!!!!");
                alertMsg.setContentText("No Vendor Associated With This Invoice");
                alertMsg.showAndWait();
            }
        }
        paymentType.requestFocus();      
    }

    @FXML
    private void storeType(ActionEvent event) {
        idNo.requestFocus();
    }
    
    

    @FXML
    private void storeQty(ActionEvent event) {
        amountPd.requestFocus();
    }

    @FXML
    private void storeAmtPd(ActionEvent event) {
        balance = 0.0;
        Double temp;
        String query = "Select balance as 'Balance' from Invoice_Payment where Invoice_Payment.vendor_invoice_id ="
                + invoiceNo.getText() + ";";
        ResultSet rs = DatabaseFactory.executeQuery(query);
        try {
            while(rs.next()){
                temp = rs.getDouble("Balance");
                if((temp > balance)&&(balance == 0)){
                    balance = temp;
                }else if(temp < balance){
                    balance = temp;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        billDate.requestFocus();      
    }

    @FXML
    private void convertDate(ActionEvent event) {
      /*final String pattern = "yyyy-MM-dd";
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        paymentDate.setConverter(converter);*/
    }

    @FXML
    private void convertBillDate(ActionEvent event) {
        /*final String pattern = "yyyy-MM-dd";
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        paymentDate.setConverter(converter);*/
    }

    @FXML
    private void handle(KeyEvent event) {
        String number, query;
        
        if((event.getCode() == KeyCode.TAB) || (idNo.isFocused())){
            if (customer.isSelected()){
              query = "Select customer_id AS 'ID', total_billed AS 'Total' from customer_bill where customer_bill.customer_bill_id =" + invoiceNo.getText() +";";  
              ResultSet rs = DatabaseFactory.executeQuery(query);
                try {
                    //idNo.setText(rs.getString("ID"));
                    System.out.println("Customer ID: " + rs.getString("ID") );
                    total = rs.getDouble("Total");
                } catch (SQLException ex) {
                    idNo.setText("N/A");
                    Alert alertMsg = new Alert(Alert.AlertType.ERROR);
                    alertMsg.setTitle("!!!!!!!!Error!!!!!!!!!");
                    alertMsg.setContentText("No Customer Associated With This Bill");
                    alertMsg.showAndWait();
                }
            }else if(vendor.isSelected()){
                query = "Select vendor_id AS 'ID', total_billed AS 'Total', date_created AS 'Date'"
                        + " from vendor_invoice where vendor_invoice.vendor_invoice_id = " + invoiceNo.getText() + ";";
                ResultSet rs = DatabaseFactory.executeQuery(query);
                try {
                    idNo.setText(rs.getString("ID"));
                    total = rs.getDouble("Total");
                    dateCreated = rs.getString("Date");
                } catch (SQLException ex) {
                    idNo.setText("N/A");
                    Alert alertMsg = new Alert(Alert.AlertType.ERROR);
                    alertMsg.setTitle("!!!!!!!!Error!!!!!!!!!");
                    alertMsg.setContentText("No Vendor Associated With This Invoice");
                    alertMsg.showAndWait();
                }
            }
            amountPd.requestFocus();
        }else if(event.getCode() == KeyCode.TAB) {
            if (invoiceNo.isFocused()) {
                paymentType.requestFocus();
            } else if (paymentType.isFocused()) {
                idNo.requestFocus();
            } else if ((amountPd.isFocused())) {
                billDate.requestFocus();
            }
        }//end of first else if
    }
    @FXML
    private void setCustomer(MouseEvent event) {
        invoiceNo.requestFocus();       
    }

    @FXML
    private void setVendor(MouseEvent event) {
        invoiceNo.requestFocus();
    }
    
}
