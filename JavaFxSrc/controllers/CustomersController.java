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
public class CustomersController implements Initializable {

    @FXML
    private BorderPane customerPane;
    @FXML
    private TableView<Custpojo> customerTable;
    @FXML
    private TableColumn<Custpojo, String> id_no;
    @FXML
    private TableColumn<Custpojo, String> name;
    @FXML
    private TableColumn<Custpojo, String> address;
    @FXML
    private TableColumn<Custpojo, String> phone_no;
    @FXML
    private TableColumn<Custpojo, String> email;

    private ObservableList<CustomersController.Custpojo> data;

   public void showTable() {
        String query = "Select customer.customer_id as 'ID No.', customer.last || ', ' || customer.first AS 'Name', "
                + " customer.street_name || ' ' || customer.city || ', ' ||customer.state ||' ' ||customer.zip_code "
                + "AS 'Address', customer.phone_no AS 'Phone No.',  customer.email AS 'Email'\n"
                + "From customer;";

        ResultSet rs = null;
        rs = DatabaseFactory.executeQuery(query);

        data = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                CustomersController.Custpojo inv = new CustomersController.Custpojo();
                inv.idno.set(rs.getString("ID No."));
                inv.name.set(rs.getString("Name"));
                inv.address.set(rs.getString("address"));
                inv.phoneNo.set(rs.getString("Phone No."));
                inv.email.set(rs.getString("Email"));
                data.add(inv);
            }
            customerTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText("Error Loading Data");
            msg.showAndWait();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id_no.setCellValueFactory(new PropertyValueFactory<>("idno"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone_no.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        
        showTable();
    }

    @FXML
    private void sortCustomer(SortEvent<Custpojo> event) {
    }

    public class Custpojo {

        private final SimpleStringProperty idno = new SimpleStringProperty();
        private final SimpleStringProperty phoneNo = new SimpleStringProperty();
        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleStringProperty address = new SimpleStringProperty();
        private final SimpleStringProperty email = new SimpleStringProperty();

        public String getIdno() {
            return idno.get();
        }

        public String getPhoneNo() {
            return phoneNo.get();
        }

        public String getName() {
            return name.get();
        }

        public String getAddress() {
            return address.get();
        }

        public String getEmail() {
            return email.get();
        }

    }
}
