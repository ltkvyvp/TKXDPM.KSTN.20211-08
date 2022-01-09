package entity;

import java.sql.Timestamp;

public class Rent {
    private int rentId;
    private int userId;
    private Bike rentedBike;
    private int debit;
    private Timestamp startTime;
    private Timestamp endTime;

    public Rent(int rentId, int userId, Bike rentedBike, int debit, Timestamp startTime, Timestamp endTime) {
        this.rentId = rentId;
        this.userId = userId;
        this.rentedBike = rentedBike;
        this.debit = debit;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getRentId() {
        return rentId;
    }

    public int getUserId() {
        return userId;
    }

    public Bike getRentedBike() {
        return rentedBike;
    }

    public int getDebit() {
        return debit;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Xe số " + this.rentedBike.getBikeId() + " thuê ở bãi xe số " + this.rentedBike.getDockId() + " vào lúc " + this.startTime.toString();
    }
}
