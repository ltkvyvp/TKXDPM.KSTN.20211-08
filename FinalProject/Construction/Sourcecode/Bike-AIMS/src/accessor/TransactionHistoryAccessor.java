package accessor;

import entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryAccessor extends DataAccessor<TransactionHistory>{
    @Override
    public TransactionHistory get(int id) {
        String q = "SELECT * FROM transactionhistory WHERE transactionId = " + id;
        TransactionHistory transactionHistory = null;
        try{
            ResultSet rs = query(q);
            if(!rs.next()){
                return null;
            }
            transactionHistory = new TransactionHistory(id, rs.getInt("userId"), rs.getInt("bikeId"),
                    rs.getFloat("totalCost"), rs.getString("content"),
                    rs.getFloat("rentedDuration"), rs.getTimestamp("createdAt"));

        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return transactionHistory;
    }

    @Override
    public List<TransactionHistory> getAll() {
        String q = "SELECT * FROM transactionhistory" ;
        List<TransactionHistory> transactionHistories = new ArrayList<TransactionHistory>();
        try{
            ResultSet rs = query(q);
            while(rs.next()){
                transactionHistories.add(new TransactionHistory(rs.getInt("transactionId"), rs.getInt("userId"),
                        rs.getInt("bikeId"), rs.getFloat("totalCost"), rs.getString("content"),
                        rs.getFloat("rentedDuration"), rs.getTimestamp("createAt")));
            }
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return transactionHistories;
    }

    @Override
    public void update(TransactionHistory transactionHistory) {
        String q = "UPDATE transactionhistory SET " +
                " userId = " + transactionHistory.getUserId() +
                " , bikeId = " + transactionHistory.getBikeId() +
                " , totalCost = " + transactionHistory.getTotalCost() +
                " , content = " + "\"" + transactionHistory.getContent() + "\"" +
                " , rentedDuration = " + transactionHistory.getRentedDuration() +
                " , createAt = \"" + transactionHistory.getCreateAt() + "\"" +
                " WHERE transactionId = " + transactionHistory.getTransactionId();
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public void save(TransactionHistory transactionHistory) {
        String q = "INSERT INTO transactionhistory(userId, bikeId, totalCost, content, rentedDuration,createAt) VALUES (" +
                transactionHistory.getUserId() +
                " ," + transactionHistory.getBikeId() +
                " ," + transactionHistory.getTotalCost() +
                " ," + "\"" + transactionHistory.getContent() + "\"" +
                " ," + transactionHistory.getRentedDuration() +
                " ," + "\"" + transactionHistory.getCreateAt() + "\"" +
                ")";
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int transactionHistoryId) {
        String q = "DELETE FROM transactionhistory WHERE transactionId = " + transactionHistoryId;
        System.out.println(q);
        try{
            execute(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
