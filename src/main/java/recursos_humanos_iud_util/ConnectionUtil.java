
package recursos_humanos_iud_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    
    private static final String URL = "jdbc:sqlserver://127.0.0.1:8030;databaseName=recursos_humanos_iud_db;Trusted_Connection=True;trustServerCertificate=true;";
    private static final String USER  = "milo";
    private static final String PASSWORD = "milo1234";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}
