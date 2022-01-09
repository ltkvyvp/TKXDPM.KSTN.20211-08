package entity;

import java.sql.Timestamp;

public class TransactionHistory {
    private int transactionId;
    private int userId;
    private int bikeId;
    private float totalCost;
    private String content;
    private float rentedDuration;
    private Timestamp createAt;
    private String errorCode;

    public TransactionHistory(int transactionId, int userId, int bikeId, float totalCost, String content, float rentedDuration, Timestamp createAt) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bikeId = bikeId;
        this.totalCost = totalCost;
        this.content = content;
        this.rentedDuration = rentedDuration;
        this.createAt = createAt;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public String getContent() {
        return content;
    }

    public float getRentedDuration() {
        return rentedDuration;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

}
