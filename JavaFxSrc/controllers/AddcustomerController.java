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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author aotugalu
 */
public class AddcustomerController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField stName;
    @FXML
    private TextField city;
    @FXML
    private TextField state;
    @FXML
    private TextField zipcode;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private DialogPane addCustDiaPane;
    @FXML
    private Button addButton;
    private String first,last;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(null); 
        stName.setText(null);
        city.setText(null);
        state.setText(null);
        zipcode.setText(null);
        phone.setText(null);
        email.setText(null);
        first = last = null;
    }    

    @FXML
    private void addCustomer(ActionEvent event) {
        String query = "insert into customer(first,last,phone_no, street_name, state, zip_code,city,email) values"
                + "('" + first +"','" + last +"','" + phone.getText() + "','" + stName.getText() +"','"+ state.getText() 
                +"','"+ zipcode.getText() +"','"+ city.getText() +"','" + email.getText() +"');";
        DatabaseFactory.executeStatement(query);
    }

    @FXML
    private void storeName(ActionEvent event) {
        String[] custName = name.getText().split(",");
        first = custName[0];
        last = custName[1];
        stName.requestFocus();
    }

    @FXML
    private void storeStreet(ActionEvent event) {
        city.requestFocus();
    }

    @FXML
    private void storeCity(ActionEvent event) {
        state.requestFocus();
    }

    @FXML
    private void storeState(ActionEvent event) {
        zipcode.requestFocus();
    }

    @FXML
    private void storeZip(ActionEvent event) {
        phone.requestFocus();
    }

    @FXML
    private void storePhone(ActionEvent event) {
        email.requestFocus();
    }

    @FXML
    private void storeEmail(ActionEvent event) {
        addButton.requestFocus();
    }
    
}
