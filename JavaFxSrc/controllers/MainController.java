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
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author aotugalu
 */
public class MainController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    @FXML
    private Menu display;
    @FXML
    private MenuItem inventory;
    @FXML
    private MenuItem orders;
    @FXML
    private MenuItem vendors;
    @FXML
    private MenuItem customers;
    @FXML
    private MenuItem payable;
    @FXML
    private MenuItem receivable;
    @FXML
    private Menu edit;
    @FXML
    private MenuItem editcust;
    @FXML
    private MenuItem addCust;
    @FXML
    private MenuItem deleteCust;
    @FXML
    private MenuItem editVend;
    @FXML
    private MenuItem addVend;
    @FXML
    private MenuItem deleteVend;
    @FXML
    private TreeView<String> dbTree;
    @FXML
    private TabPane t_Pane;
    
    private final Node image1 = new ImageView(new Image(getClass().getResourceAsStream("billtotal.jpg")));
    private final Node image2 = new ImageView(new Image(getClass().getResourceAsStream("billpay.png")));
    private final Node image3 = new ImageView(new Image(getClass().getResourceAsStream("customerorder.jpg")));
    private final Node image4 = new ImageView(new Image(getClass().getResourceAsStream("customer.jpg")));
    private final Node image5 = new ImageView(new Image(getClass().getResourceAsStream("invoicepayment.jpg")));
    private final Node image6 = new ImageView(new Image(getClass().getResourceAsStream("vinvoice.png")));
    private final Node image7 = new ImageView(new Image(getClass().getResourceAsStream("vendor.jpg")));
    private final Node image8 = new ImageView(new Image(getClass().getResourceAsStream("database.png")));
    private final Node image9 = new ImageView(new Image(getClass().getResourceAsStream("vendororder.jpg")));
    private final Node image10 = new ImageView(new Image(getClass().getResourceAsStream("item.png")));
    private final Node image11 = new ImageView(new Image(getClass().getResourceAsStream("inventory.jpg")));
    private final Node image12 = new ImageView(new Image(getClass().getResourceAsStream("expiration.jpg")));
    private final Node image13 = new ImageView(new Image(getClass().getResourceAsStream("user.jpg")));
    @FXML
    private MenuItem editOrders;
    @FXML
    private MenuItem addOrder;
    @FXML
    private MenuItem editPay;
    @FXML
    private MenuItem addPay;
    @FXML
    private MenuItem deletePay;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
            //LoginController login = (LoginController)loader.getController();

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Please Login or Create Account to Continue");
            alert.setResizable(true);
            alert.setDialogPane(loader.load());
            alert.initModality(Modality.WINDOW_MODAL);

            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> window.hide());

            alert.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //Load Database Structure
            loadTree();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
    }

    public void loadTree() throws SQLException{
        ResultSet rs = DatabaseFactory.getSchema();
        String tableName = null;
        
        TreeItem<String> root = new TreeItem<>("DB2", image8);
        TreeItem<String> table = new TreeItem<>();
        TreeItem<String> column;
        
        root.setExpanded(true);
        dbTree.setEditable(true);
        dbTree.setCellFactory((TreeView<String> p) -> new TextFieldTreeCellImpl());

        ResultSet rsCol = null;
        ResultSet rsTab = DatabaseFactory.getTables();
        while (rsTab.next()) {
            tableName = rsTab.getString(3);                 
            switch (tableName) {
                case "Bill_Payment":
                    table = new TreeItem<>(tableName, image2);
                    break;
                case "Customer":
                    table = new TreeItem<>(tableName, image4);
                    break;
                case "Customer_Bill":
                    table = new TreeItem<>(tableName, image1);
                    break;
                case "Customer_Order":
                    table = new TreeItem<>(tableName, image3);
                    break;
                case "Invoice_Payment":
                    table = new TreeItem<>(tableName, image5);
                    break;
                case "Vendor_Invoice":
                    table = new TreeItem<>(tableName, image6);
                    break;
                case "Vendor":
                    table = new TreeItem<>(tableName, image7);
                    break;
                case "Vendor_Order":
                    table = new TreeItem<>(tableName, image9);
                    break;
                case "Item_Description":
                    table = new TreeItem<>(tableName, image10);
                    break;
                case "Item_Expiration":
                    table = new TreeItem<>(tableName, image11);
                    break;
                case "Users":
                    table = new TreeItem<>(tableName, image12);
                    break;
                default:
                    break;
            }//end of switch statement
            
                           
            rsCol = DatabaseFactory.getColumns(tableName);
            while (rsCol.next()) {
                column = new TreeItem<>( rsCol.getString(4));
                table.getChildren().add(column);
            }//end of inner whilee
            root.getChildren().add(table);
        }//end of outer while
        
        dbTree.setRoot(root);
       
    }
    
    @FXML
    private void displayInventory(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/inventory.fxml"));
        Tab t;
        
        t = new Tab("Inventory");
        try {
            t.setContent(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        t_Pane.getTabs().add(t);
        t.setClosable(true);
        t_Pane.autosize();
        t_Pane.getSelectionModel().select(t);
        
    }

    @FXML
    private void displayOrders(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/orders.fxml"));
        Tab t;

        t = new Tab("Orders");
        try {
            t.setContent(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        t_Pane.getTabs().add(t);
        t.setClosable(true);
        t_Pane.autosize();
        t_Pane.getSelectionModel().select(t);
    }

    @FXML
    private void displayVendors(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/vendors.fxml"));
        Tab t;

        t = new Tab("Vendors");
        try {
            t.setContent(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        t_Pane.getTabs().add(t);
        t.setClosable(true);
        t_Pane.autosize();
        t_Pane.getSelectionModel().select(t);
    }

    @FXML
    private void displayCustomers(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/customers.fxml"));
        Tab t;

        t = new Tab("Customers");
        try {
            t.setContent(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        t_Pane.getTabs().add(t);
        t.setClosable(true);
        t_Pane.autosize();
        t_Pane.getSelectionModel().select(t);
    }

    @FXML
    private void displayAccountsP(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/accountspayable.fxml"));
        Tab t;

        t = new Tab("Accounts Payable");
        try {
            t.setContent(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        t_Pane.getTabs().add(t);
        t_Pane.getSelectionModel().select(t);
    }

    @FXML
    private void displayAccountsR(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/accountsreceivable.fxml"));
        Tab t;

        t = new Tab("Accounts Receivable");
        try {
            t.setContent(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        t_Pane.getTabs().add(t);
        t.setClosable(true);
        t_Pane.autosize();
        t_Pane.getSelectionModel().select(t);
    }

    @FXML
    private void displayTree(MouseDragEvent event) {
    }

    @FXML
    private void displayTableTree(MouseEvent event) {
    }


    @FXML
    private void editCustomer(ActionEvent event) {
    }

    @FXML
    private void addCustomer(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addcustomer.fxml"));
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setResizable(true);
        try {
            alert.setDialogPane(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        alert.initModality(Modality.WINDOW_MODAL);

        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(evt -> window.hide());

        alert.showAndWait();
    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
    }

    @FXML
    private void editVendor(ActionEvent event) {
    }

    @FXML
    private void addVend(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addvendor.fxml"));
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setResizable(true);
        try {
            alert.setDialogPane(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        alert.initModality(Modality.WINDOW_MODAL);

        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(evt -> window.hide());

        alert.showAndWait();
    }

    @FXML
    private void deleteVend(ActionEvent event) {
    }
    @FXML
    private void openEdit(ActionEvent event) {
    }

    @FXML
    private void editOrder(ActionEvent event) {
    }

    @FXML
    private void addOrder(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addorders.fxml"));
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setResizable(true);
        try {
            alert.setDialogPane(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        alert.initModality(Modality.WINDOW_MODAL);

        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(evt -> window.hide());

        alert.showAndWait();
    }

    @FXML
    private void deleteOrder(ActionEvent event) {
    }

    @FXML
    private void editPay(ActionEvent event) {
    }

    @FXML
    private void addPay(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addpayment.fxml"));
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setResizable(true);
        try {
            alert.setDialogPane(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        alert.initModality(Modality.WINDOW_MODAL);

        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(evt -> window.hide());

        alert.showAndWait();
    }

    @FXML
    private void deletePay(ActionEvent event) {
    }
    
    //code retrieved from https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
    
    private final class TextFieldTreeCellImpl extends TreeCell<String> {

        private TextField textField;
        
        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }

}
