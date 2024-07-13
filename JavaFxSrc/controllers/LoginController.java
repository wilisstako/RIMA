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
import db2.Password;
import db2.UserData;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author aotugalu
 */
public class LoginController implements Initializable {

    @FXML
    private TextField user_name;
    private UserData user = new UserData();
    
    @FXML
    private TextField password;
    @FXML
    private StackPane loginStack;
    @FXML
    private Button createAccount;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void storeUsername(ActionEvent event) {
        password.requestFocus();
    }
    @FXML
    private void storePassword(ActionEvent event) {
    }
    @FXML
    private void loginUser(ActionEvent event) throws IOException, IOException {
        String query = "SELECT users.password FROM users WHERE users.username = '" + user_name.getText() + "';";
        try{
            ResultSet rs = DatabaseFactory.executeQuery(query);
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "Error in username ");
            } else {
                int i=1;
                while (rs.next()) {
                    System.out.println("Password: " + rs.getString("password"));
                    boolean p = Password.check(password.getText(), rs.getString(i));
                    if (p) {
                        JOptionPane.showMessageDialog(null, "Successfully Logged In");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error in username or password");
                    }
               }
            }
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void createAccount(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/createaccount.fxml"));
        loginStack.getChildren().add(loader.load());
    }

}

