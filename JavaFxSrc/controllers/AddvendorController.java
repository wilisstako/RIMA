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
public class AddvendorController implements Initializable {

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
    private TextField poc;
    @FXML
    private Button addButton;
    @FXML
    private DialogPane addVendorDia;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(null);
        stName.setText(null);
        city.setText(null);
        state.setText(null);
        zipcode.setText(null);
        phone.setText(null);
        poc.setText(null);
        
    }    

    @FXML
    private void addVendor(ActionEvent event) {
        String query = "insert into vendor(company_name,phone_no, street_name, state, zip_code,city,company_poc) values"
                + "('"+ name.getText()+ "','" + phone.getText() + "','" + stName.getText() + "','" + state.getText()
                + "','" + zipcode.getText() + "','" + city.getText() + "','" + poc.getText() + "');";
        DatabaseFactory.executeStatement(query);
    }

    @FXML
    private void storeName(ActionEvent event) {
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
        poc.requestFocus();
    }

    @FXML
    private void storePoc(ActionEvent event) {
        addButton.requestFocus();
    }

}
