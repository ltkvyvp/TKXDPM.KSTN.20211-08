package controller;

import barcode.BarcodeProcessor;
import barcode.exception.BarcodeException;
import checkout.CreditCard;
import checkout.InterbankInterface;
import checkout.InterbankSubsystem;
import checkout.PaymentTransaction;
import checkout.exception.PaymentException;
import entity.Bike;
import accessor.*;
import entity.Dock;
import entity.Rent;
import utils.Configs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Date;
import java.sql.Timestamp;
import controller.calculator.*;

/**
 * This class controls flow of system when user rents bike.
 */
public class RentBikeController extends BaseController {

    /**
     * BarcodeProcessor for interacting with BarcodeSubsystem
     */
    private BarcodeProcessor barcodeProcessor;

    private BikeAccessor bikeAccessor;
    private RentAccessor rentAccessor;
    private DockAccessor dockAccessor;

    /**
     * InterbankInterface object for interacting with Interbank Subsystem
     */
    private InterbankInterface interbank;

    public RentBikeController() {
        this.barcodeProcessor = new BarcodeProcessor();
        this.bikeAccessor = new BikeAccessor();
        this.dockAccessor = new DockAccessor();
        this.rentAccessor = new RentAccessor();
        this.interbank = new InterbankSubsystem();
    }

    /**
     * request barcode server to get bike's id from a barcode
     * @param barcode barcode of the bike
     * @return bike matched with barcode
     */
    public Bike requestBike(String barcode) {
        try {
            int bikeId = this.barcodeProcessor.processBarcode(barcode);
            return bikeAccessor.get(bikeId);
        } catch(BarcodeException | MalformedURLException e){
            System.out.print(e.getMessage());
            return null;
        }

    }

    /**
     * calculate deposit of bike
     * @param bike bike rent by user
     * @return deposit
     */
    public float calculateDeposit(Bike bike) {
        float deposit = -1;
        try {
            Class<?> clazz = Class.forName("controller.calculator." + bike.getBikeTypeName() + "CostCalculator");
            CostComputer calculator = (CostComputer) clazz.getConstructor().newInstance();
            deposit = calculator.getDeposit(bike);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
        System.out.println("Calculated Deposit: " + String.valueOf(deposit));
        return deposit;
    }

    /**
     * Request interbank to deposit a bike
     * @param userId id of user
     * @param bike the bike wanted to rent by user
     * @param creditCard credit card of user
     * @return notification string
     */
    public String requestRentBike(int userId, Bike bike, CreditCard creditCard) {
        if (bike.isStatus()){
            return Configs.BIKE_IS_RENTED;
        }

        int deposit = (int) this.calculateDeposit(bike);
        System.out.println("Deposit in request: " + String.valueOf(deposit));
        try {
            PaymentTransaction paymentTransaction = this.interbank.payRental(creditCard, deposit, "rent bike" + bike.getBikeName());
            Date date  = new Date();
            Timestamp startTime = new Timestamp(date.getTime());
            int rentId = -1;
            // Decrease available bikes of dock:
            Dock dock = this.dockAccessor.get(bike.getDockId());
            int curAvailableBikes = dock.getAvailableBikes();
            dock.setAvailableBikes(curAvailableBikes-1);
            this.dockAccessor.update(dock);
            // Set bike is rented:
            bike.setStatus(true);
            this.bikeAccessor.update(bike);

            // Save rent:
            Rent rent = new Rent(-1, userId, bike, deposit, startTime, null);
            this.rentAccessor.save(rent);
            return Configs.SUCCESS_NOTIFICATION;

        } catch (PaymentException e) {
            return e.getMessage();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return "Cannot process transaction!";
    }
}
