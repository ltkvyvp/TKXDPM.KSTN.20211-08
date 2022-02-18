package accessor.connection;
import java.sql.*;

public interface DBConnection {

    public ResultSet query(String q);
    public void execute(String q);
    public void executeUpdate(String q);
    
}
