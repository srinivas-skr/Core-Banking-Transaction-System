import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    
    // Oracle Connection String (Standard for XE 11g)
    // Note: We use ':' instead of '/' for 11g SID
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "banking_user";  // The user we created in Step 1
    private static final String PASSWORD = "pass123";

    public static Connection getConnection() throws SQLException {
        try {
            // Load Oracle Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
