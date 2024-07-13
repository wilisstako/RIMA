import java.sql.Connection;
import org.sqlite.SQLiteDataSource;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseFactory {
	private static Connection con = null;
	private static Statement stmt = null;
	public static SQLiteDataSource ds = null;
	public static void main(String[] args) throws SQLException {
		SQLiteDataSource ds = null;

            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:master.db");
        System.out.println( "Opened database successfully" );
        
        String query = "CREATE TABLE IF NOT EXISTS employee (" +
            "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        	"LAST_NAME TEXT NOT NULL, " +
            "FIRST_NAME TEXT NOT NULL," +
        	"PASSWORD VARCHAR(20) NOT NULL, " +
            "MANAGER BOOL, " + 
        	"ADMIN BOOL )";
        String query1 = "CREATE TABLE IF NOT EXISTS item (" + 
      			"ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
      			"DESCRIPTION VARCHAR(50)," +
      			"COST DOUBLE, " +
      			"PRICE DOUBLE )";
      Connection conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      int rv = stmt.executeUpdate( query );
      System.out.println( "1st executeUpdate() returned " + rv);
      rv = stmt.executeUpdate( query1 );
      System.out.println( "2nd executeUpdate() returned " + rv);
      System.out.println( "Created table successfully" );           
  
}
	
	public static void deleteDatabase() throws SQLException {
		ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:master.db");
		Connection conn = ds.getConnection();
		stmt = con.createStatement();
		String s = "DROP DATABASE MASTER";
		stmt.executeUpdate(s);
	}
	
	public static void executeStatement(String statement) throws SQLException {
		ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:master.db");
		Connection con = ds.getConnection();
		stmt = con.createStatement();
		stmt.executeUpdate(statement);
	}
	
	public static ResultSet executeQuery(String query) throws SQLException {
		ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:master.db");
		Connection con = ds.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}
	
	public static String getKey(String statement) throws SQLException {
		PreparedStatement pstmt = null;
		ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:master.db");
		Connection conn = ds.getConnection();
		pstmt = con.prepareStatement(statement,Statement.RETURN_GENERATED_KEYS);
		pstmt.executeUpdate();
		   ResultSet rs = pstmt.getGeneratedKeys();
		    rs.next();
		   return Integer.toString(rs.getInt(1));
		
	}

}
