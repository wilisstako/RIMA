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

import db2.UserData;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author aotugalu
 */
public class CreateAccountController implements Initializable {

    @FXML
    private TextField fname;
    @FXML
    private TextField password;
    @FXML
    private Tooltip pw_tool_tip;
    @FXML
    private TextField username;
    @FXML
    private TextField lname;
    @FXML
    private Button createAccount;
    @FXML
    private AnchorPane create_Account;

    private UserData user = new UserData();
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void getFirst (ActionEvent event) {
        fname.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    System.out.println("TextField Text Changed (newValue: " + newValue + ")");
                }
                );
        user.setFirst(fname.getText());
        System.out.println("username: " + fname.getText());
    }

    @FXML
    private void displayToolTip(WindowEvent event) {

    }

    @FXML
    private void getPassword(ActionEvent event) {
        user.setPassword(password.getText());
    }

    @FXML
    private void getUsername(ActionEvent event) {
        user.setUsername(username.getText());
    }

    @FXML
    private void getLast(ActionEvent event) {
        user.setLast(lname.getText());
    }

    @FXML
    private void createAccount(ActionEvent event) {
        boolean isCreated;
        isCreated = user.storeUser();
        if (isCreated) {
            create_Account.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Error Occured Please Try Again");
        }
    }

}
