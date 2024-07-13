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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author aotugalu
 */
public class AddordersController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private TextField itemName;
    @FXML
    private TextField description;
    @FXML
    private TextField quantity;
    @FXML
    private TextField orderByFrom;
    @FXML
    private DatePicker datePicker;
    @FXML
    private DatePicker expDate;
    @FXML
    private CheckBox customer;
    @FXML
    private CheckBox vendor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void storeOrder(ActionEvent event) {
    }

    @FXML
    private void storeItem(ActionEvent event) {
    }

    @FXML
    private void storeDesc(ActionEvent event) {
    }

    @FXML
    private void storeQty(ActionEvent event) {
    }

    @FXML
    private void storeWho(ActionEvent event) {
    }

    @FXML
    private void convertDate(ActionEvent event) {
    }

    @FXML
    private void convertExpDate(ActionEvent event) {
    }

    @FXML
    private void setCustomer(MouseEvent event) {
    }

    @FXML
    private void setVendor(MouseEvent event) {
    }
    
}
