package controller;

import checkout.CreditCard;
import checkout.InterbankInterface;
import checkout.InterbankSubsystem;
import checkout.PaymentTransaction;
import checkout.exception.PaymentException;
import entity.Bike;
import entity.Dock;
import entity.Rent;
import accessor.*;
import controller.calculator.CostComputer;
import controller.calculator.SimpleCostCalculator;
import entity.TransactionHistory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This class controls flow of system when user returns bike.
 */
public class ReturnBikeController extends BaseController{

    /**
     * String returned when user rent bike successfully.
     */
    private String SUCCESS_REFUND = "You returned bike successfully!";

    /**
     * CostComputer object for calculating operation
     */
    private CostComputer calculator;

    private RentAccessor rentAccessor;

    /**
     * BikeAccessor object for accessing to bike in database
     */
    private BikeAccessor bikeAccessor;
    private DockAccessor dockAccessor;

    /**
     * InterbankInterface object for interacting with Interbank Subsystem
     */
    private InterbankInterface interbank;

    private TransactionHistoryAccessor transactionHistoryAccessor;

    public ReturnBikeController() {
        this.bikeAccessor = new BikeAccessor();
        this.rentAccessor = new RentAccessor();
        this.dockAccessor = new DockAccessor();
        this.interbank = new InterbankSubsystem();
        this.transactionHistoryAccessor = new TransactionHistoryAccessor();
    }

    /**
     * calculate cost of bike rented to specific time
     * @param rent bike rented by user
     * @param endTime the time that user rent bike to
     * @return total cost
     */
    public float calculateCost(Rent rent, Timestamp endTime) {
        Bike rentedBike = rent.getRentedBike();
        float totalCost = -1;
        try {
            Class<?> clazz = Class.forName("controller.calculator." + rentedBike.getBikeTypeName() + "CostCalculator");
            CostComputer calculator = (CostComputer) clazz.getConstructor().newInstance();
            totalCost = calculator.checkout(rent, endTime);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
        return totalCost;
    }

    /**
     * request interbank to pay a renting cost for returning bike
     * @param userId id of user
     * @param rent user's rented bike
     * @param creditCard credit card info
     * @param endTime time that user returns bike
     * @return notification string
     */
    public String requestReturnBike(int userId, Rent rent, CreditCard creditCard, Timestamp endTime) {
        if (endTime == null) {
            endTime = new Timestamp((new Date()).getTime());
        }
        int deposit = (int) rent.getDeposit();
        int cost = (int) this.calculateCost(rent, endTime);
        int returnMoney = deposit - cost;
        Bike bike = rent.getRentedBike();
        System.out.println("Total deposit: " + String.valueOf(deposit));
        System.out.println("Total cost: " + String.valueOf(cost));
        System.out.println("Return money: " + String.valueOf(returnMoney));
        try {
            PaymentTransaction paymentTransaction = this.interbank.refund(creditCard, returnMoney, "return bike " + bike.getBikeName());
            // Update bike status:
            bike.setStatus(false);
            this.bikeAccessor.update(bike);
            // Increase dock availableBikes:
            Dock dock = this.dockAccessor.get(bike.getDockId());
            int curAvailableBikes = dock.getAvailableBikes();
            dock.setAvailableBikes(curAvailableBikes+1);
            this.dockAccessor.update(dock);
            // Update rent:
            rent.setEndTime(endTime);
            this.rentAccessor.update(rent);
            // Make transaction
            TransactionHistory transactionHistory = makeTransactionHistory(userId, rent, paymentTransaction);
            transactionHistoryAccessor.save(transactionHistory);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "Error while return bike!";
        } catch (PaymentException e) {
            System.out.print(e.getMessage());
            return e.getMessage();
        }
        return SUCCESS_REFUND;
    }

    /**
     * Make transaction history after payment transaction of returning bike
     * @param userId id of user
     * @param rent
     * @param paymentTransaction
     * @return TransactionHistory
     */
    private TransactionHistory makeTransactionHistory(int userId, Rent rent, PaymentTransaction paymentTransaction) {
        int transactionId = -1;
        int bikeId = rent.getRentedBike().getBikeId();
        float totalCost = (float) paymentTransaction.getAmount();
        String content = paymentTransaction.getTransactionContent();
        Timestamp createAt = new Timestamp(paymentTransaction.getCreateAt().getTime());

        long duration = rent.getEndTime().getTime() - rent.getStartTime().getTime();
        long rentMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        return new TransactionHistory(-1, userId, bikeId, totalCost,content, (float) (rentMinutes / 60.0), createAt);
    }
}
