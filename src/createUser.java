import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

import javax.print.DocFlavor.STRING;

public class createUser {
		public static void main(String[] args) throws SQLException {
			SQLiteDataSource ds = null;

	            ds = new SQLiteDataSource();
	            ds.setUrl("jdbc:sqlite:inventory.db");
	        System.out.println( "Opened database successfully" );
	                
	        String query1 = "INSERT INTO users ( USER_NAME, PASSWORD ) VALUES ( 'test', 'testp' )";
	      Connection conn = ds.getConnection();
	      Statement stmt = conn.createStatement();
          int rv = stmt.executeUpdate( query1 );
          System.out.println( "1st executeUpdate() returned " + rv);          
	  
	}
}