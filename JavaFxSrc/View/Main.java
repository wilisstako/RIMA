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
package View;

import Controller.MainController;
import com.sun.javafx.application.LauncherImpl;
import db2.preloader.DB2_Preloader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author aotugalu
 */
public class Main extends Application {
    
    DB2_Preloader splash = new DB2_Preloader();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //Create DB JDBC driver
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));  
        MainController mc = new MainController();
        System.out.println(loader.toString());
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setTitle("Welcome to DB2 Application");
        primaryStage.setScene(scene);
        
        primaryStage.show();
        
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, DB2_Preloader.class, args);
        
    }
    
}
