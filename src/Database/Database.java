
package Database;

import Extras.Fields;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements Fields {
    private static Connection con;

    public static Connection getConnection() throws ClassNotFoundException, SQLException{ 
            Class.forName(driver);
            con =  (Connection) DriverManager.getConnection(database,user,password);
            return con;
    }
}
