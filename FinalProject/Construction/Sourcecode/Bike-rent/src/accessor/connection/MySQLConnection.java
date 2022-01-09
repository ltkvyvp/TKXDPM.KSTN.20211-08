package accessor.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection implements DBConnection {

    private Connection conn = null;
    private static MySQLConnection instance;

    private MySQLConnection(){}

    /**
     * Use singleton to create only one instance of MySQLConnection
     * @return
     */
    public static MySQLConnection getInstance(){
        if(instance == null){
            instance = new MySQLConnection();
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * This function will create the Statement of connection,
     * run the query Select to database
     * @param q: the query in type String
     * @return result of query
     */
    @Override
    public ResultSet query(String q) {

        ResultSet rs = null;
        try{
//            System.out.println("Creating statement...");
            Statement stmt = this.conn.createStatement();
            rs = stmt.executeQuery(q);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources

        }//end try
        return rs;
    }

    /**
     * This function will create the Statement of connection,
     * run the query Delete to database
     * @param q: the query in type String
     */
    @Override
    public void execute(String q) {
        try{
            Statement stmt = this.conn.createStatement();
            stmt.execute(q);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return;
    }

    /**
     * This function will create the Statement of connection,
     * run the query Update or Insert to database
     * @param q: the query in type String
     */
    @Override
    public void executeUpdate(String q) {
        try{
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(q);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return;
    }
}
