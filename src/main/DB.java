import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    /* Database connection settings, change dbName, dbusername, dbpassword */
	private final String dbServer = "sql7.freesqldatabase.com";
	private final String dbServerPort = "3306";
	private final String dbName = "sql7755696";
	private final String dbusername = "sql7755696";
	private final String dbpassword = "tGKZtBXEFM";

	private Connection con = null;
	
	/**
	 * Establishes a connection with the database, initializes and returns
	 * the Connection object.
	 * 
	 * @return Connection, the Connection object
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("MySQL Driver not found: " + e.getMessage());
        }

        try {
            // Establish connection
            String url = "jdbc:mysql://" + dbServer + ":" + dbServerPort + "/" + dbName 
                         + "?useSSL=false&serverTimezone=UTC";
            con = DriverManager.getConnection(url, dbusername, dbpassword);
            System.out.println("Connection established.");
            return con;
        } catch (SQLException e) {
            throw new Exception("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
}
