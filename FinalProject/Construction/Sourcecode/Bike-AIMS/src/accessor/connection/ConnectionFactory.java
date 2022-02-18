package accessor.connection;

import accessor.connection.DBConnection;
import accessor.connection.MySQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/bike_rental?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "phucnguyenphi123";

    /**
     * Create connection to mysql server
     * @return connection
     */
    public DBConnection getDBConnection(){
        MySQLConnection mySQLConnection = MySQLConnection.getInstance();
        if(mySQLConnection.getConn() != null){
            return mySQLConnection;
        }

        Connection conn = null;
        Statement stmt = null;
        try{
            //registering the jdbc driver here, your string to use
            //here depends on what driver you are using.
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            mySQLConnection.setConn(conn);
            System.out.println("Finish connecting to database...");
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return mySQLConnection;
    }

}
