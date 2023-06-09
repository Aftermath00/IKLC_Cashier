import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    static Connection connection;

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/cashier";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void initDBConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Connection is established");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver: " + JDBC_DRIVER);
        } catch (SQLException e) {
            System.err.println("Could not establish database connection: " + DB_URL);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            initDBConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection is closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection");
                e.printStackTrace();
            }
        }
    }

}