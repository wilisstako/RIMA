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
public class VendorsController implements Initializable {

    @FXML
    private BorderPane vendorPane;
    @FXML
    private TableView<Vendorpojo> vendorTable;
    @FXML
    private TableColumn<Vendorpojo, String> id_no;
    @FXML
    private TableColumn<Vendorpojo, String> name;
    @FXML
    private TableColumn<Vendorpojo, String> address;
    @FXML
    private TableColumn<Vendorpojo, String> phone_no;
    @FXML
    private TableColumn<Vendorpojo, String> poc;
    
    private ObservableList<VendorsController.Vendorpojo> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id_no.setCellValueFactory(new PropertyValueFactory<>("idno"));
        poc.setCellValueFactory(new PropertyValueFactory<>("poc"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone_no.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
  
        showTable();
    }  
    
    public void showTable() {
        String query = "Select vendor.vendor_id as 'ID No.', vendor.company_name AS 'Name',  vendor.street_name || ' ' "
                + "|| vendor.city || ', ' || vendor.state || ' ' || vendor.zip_code AS 'Address', vendor.phone_no AS 'Phone No.', "
                + " vendor.company_poc AS 'POC'\n"
                + "From vendor;";
        ResultSet rs = null;
        rs = DatabaseFactory.executeQuery(query);

        data = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                VendorsController.Vendorpojo inv = new VendorsController.Vendorpojo();
                inv.idno.set(rs.getString("ID No."));
                inv.name.set(rs.getString("Name"));
                inv.address.set(rs.getString("address"));
                inv.phoneNo.set(rs.getString("Phone No."));
                inv.poc.set(rs.getString("POC"));
                data.add(inv);
            }
            vendorTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText("Error Loading Data");
            msg.showAndWait();
        }
    }


    @FXML
    private void sortVendor(SortEvent<Vendorpojo> event) {
    }

    public class Vendorpojo {

        private SimpleStringProperty idno = new SimpleStringProperty();
        private SimpleStringProperty phoneNo = new SimpleStringProperty();
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty address = new SimpleStringProperty();
        private SimpleStringProperty poc = new SimpleStringProperty();

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

        public String getPoc() {
            return poc.get();
        }
        
        public void  getIdno(String a) {
           idno.set(a);
        }

        public void  getPhoneNo(String a) {
           phoneNo.set(a);
        }

        public void  getName(String a) {
           name.set(a);
        }

        public void  getAddress(String a) {
           address.set(a);
        }

        public void  getPoc(String a) {
           poc.set(a);
        }

    }
}
