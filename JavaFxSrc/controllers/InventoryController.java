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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
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
public class InventoryController implements Initializable {

    @FXML
    private TableView<Invpojo> inventory;
    @FXML
    private TableColumn<Invpojo, Integer> id_no;
    @FXML
    private TableColumn<Invpojo, String> name;
    @FXML
    private TableColumn<Invpojo, String> description;
    @FXML
    private TableColumn<Invpojo, Integer> stockQty;
    @FXML
    private TableColumn<Invpojo, String> supplier;

    private ObservableList<Invpojo> data;
    @FXML
    private BorderPane inventoryPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id_no.setCellValueFactory(new PropertyValueFactory<>("idno"));
        stockQty.setCellValueFactory(new PropertyValueFactory<>("stckqty"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("vendor"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        showTable();
    }

    public void showTable() {
        String query = "Select inventory.item_id AS 'ID No.', item_description.name AS 'Name', \n"
                + "item_description.paragraph AS 'Description', inventory.quantity_onhand AS 'Stock Quantity', vendor.company_name AS\n"
                + "'Supplier'\n"
                + "From ((inventory INNER JOIN item_description ON inventory.item_id = item_description.item_id)\n"
                + "INNER JOIN vendor ON inventory.vendor_id = vendor.vendor_id)\n"
                + "WHERE inventory.quantity_onhand >= 0\n"
                + "Group BY inventory.item_id, item_description.name, item_description.paragraph, inventory.quantity_onhand;";

        ResultSet rs = null;
        rs = DatabaseFactory.executeQuery(query);

        data = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                Invpojo inv = new Invpojo();
                inv.idno.set(rs.getInt("ID No."));
                inv.name.set(rs.getString("Name"));
                inv.description.set(rs.getString("Description"));
                inv.stckqty.set(rs.getInt("Stock Quantity"));
                inv.vendor.set(rs.getString("Supplier"));
                
                data.add(inv);
            }
            inventory.setItems(data); 
        } catch (Exception e) {
            e.printStackTrace();
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText("Error Loading Data");
            msg.showAndWait();
        }
    }
    @FXML
    private void sortInventory(SortEvent<TableView<Invpojo>> event) {
        event.getSource();
    }

    


    public class Invpojo{

        private SimpleIntegerProperty idno = new SimpleIntegerProperty();
        private SimpleIntegerProperty stckqty = new SimpleIntegerProperty();
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty description = new SimpleStringProperty();
        private SimpleStringProperty vendor = new SimpleStringProperty();

        public Integer getIdno() {
            return idno.get();
        }

        public Integer getStckqty() {
            return stckqty.get();
        }

        public String getName() {
            return name.get();
        }

        public String getDescription() {
            return description.get();
        }

        public String getVendor() {
            return vendor.get();
        }

    }
}
   

