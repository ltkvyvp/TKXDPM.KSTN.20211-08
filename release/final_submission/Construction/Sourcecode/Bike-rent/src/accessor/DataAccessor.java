package accessor;

import accessor.connection.ConnectionFactory;
import accessor.connection.DBConnection;

import java.sql.ResultSet;

public abstract class DataAccessor<T> implements DataAccessorInterface<T> {
    accessor.connection.ConnectionFactory connectionFactory = new accessor.connection.ConnectionFactory();
    accessor.connection.DBConnection dbConnection = connectionFactory.getDBConnection();

    public ResultSet query(String q){
        return dbConnection.query(q);
    }

    public void execute(String q){
        dbConnection.execute(q);
    }

    public void executeUpdate(String q){
        dbConnection.executeUpdate(q);
    }
}