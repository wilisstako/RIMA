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
package db2;

/**
 *
 * @author aotugalu
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;

public class DatabaseFactory {

    private static Connection con = null;
    private static Statement stmt = null;

    public static void setupDatabase() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:C:\\medocs\\sqlite\\gui\\SQLiteStudio\\");
        if( con == null){
            System.out.println("Connection is null");
        }
        stmt = con.createStatement();
        String s = "CREATE DATABASE INVENTORY";
        stmt.executeUpdate(s);
        s = "USE INVENTORY";
        stmt.executeUpdate(s);

        //create Employee table
        s = "CREATE TABLE EMPLOYEE(ID INTEGER NOT NULL AUTO_INCREMENT, LAST_NAME VARCHAR(50), FIRST_NAME VARCHAR(50),"
                + " PASSWORD VARCHAR(20), MANAGER BOOL, ADMIN BOOL, PRIMARY KEY(ID))";
        stmt.executeUpdate(s);

        //create Item table
        s = "CREATE TABLE ITEM(ID INTEGER NOT NULL AUTO_INCREMENT, DESCRIPTION VARCHAR(50), COST DOUBLE, PRICE DOUBLE,"
                + "PRIMARY KEY(ID))";
        stmt.executeUpdate(s);
    }

    public static void deleteDatabase() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost/?autoReconnect=true&useSSL=false", "test", "password");
        stmt = con.createStatement();
        String s = "DROP DATABASE INVENTORY";
        stmt.executeUpdate(s);
    }

    public static void executeStatement(String statement){
        try{
        con = DriverManager.getConnection("jdbc:sqlite:DB2");
        stmt = con.createStatement();
        stmt.executeUpdate(statement);
        Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
        alertMsg.setTitle("!!!!!!!!!Success!!!!!!!!!");
        alertMsg.setContentText("Information Has Been Added to Database");
        alertMsg.showAndWait();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ResultSet executeQuery(String query){
        try{
        con = DriverManager.getConnection("jdbc:sqlite:DB2");
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
        alertMsg.setTitle("!!!!!!!!!Success!!!!!!!!!");
        alertMsg.setContentText("Information Has Been Updated");
        alertMsg.showAndWait();
        return rs;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null; 
    }
    
    // --- LISTING DATABASE SCHEMA NAMES ---
    public static ResultSet getSchema() throws SQLException{
        ResultSet rs = null;
        DatabaseMetaData md;
        con = DriverManager.getConnection("jdbc:sqlite:DB2");
        try {
          md = con.getMetaData();
          rs = md.getSchemas();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return rs;       
    }
    // --- LISTING DATABASE TABLE NAMES ---
    
    public static ResultSet getTables() throws SQLException{
        con = DriverManager.getConnection("jdbc:sqlite:DB2");
        String[] types = {"TABLE"};
        ResultSet rs = null;
        try {
            rs = con.getMetaData()
                    .getTables("DB2", null, "%", types);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    
    }

    // --- LISTING DATABASE COLUMN NAMES ---
    
    public static ResultSet getColumns(String tableName) throws SQLException{
    ResultSet rs = null;
    con = DriverManager.getConnection("jdbc:sqlite:DB2");
    DatabaseMetaData meta;
        try {
            meta = con.getMetaData();
            rs = meta.getColumns("DB2", null, tableName, "%");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }
    

}    