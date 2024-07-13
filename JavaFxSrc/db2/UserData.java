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

import Controller.MainController;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author aotugalu
 */
public class UserData {
    private String username, first, last, password, type;
    private MainController mc = new MainController();
    
    
    public UserData(){
        username = first = last = password = type = "a";
    }
    
    public void setLast(String l){
        last = l;
    }
    
    public void setFirst(String f){
        first = f;
    }
    
    public void setUsername(String u){
        username = u;
    }
    
    public void setPassword(String p){
        try {
            password = Password.getSaltedHash(password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void setType (String t){
        type = t;     
    }
   
    public boolean storeUser (){
       String query = "INSERT INTO users(username,password,first,last,type)"
                + "VALUES('" + username + "','" + password + "','" + first +"','" + last + "','" +type +"');";
        	
        DatabaseFactory.executeStatement(query);  
        
        JOptionPane.showMessageDialog(null, "Account Successfully Created");
        return true;
        
    }
    
}
