package barcode.controller;

import accessor.CategoryAccessor;
import accessor.BikeAccessor;
import accessor.RentAccessor;
import controller.RentBikeController;
import controller.ReturnBikeController;
import entity.*;
import barcode.BarcodeProcessor;
import barcode.exception.BarcodeException;
import checkout.CreditCard;
import accessor.BikeAccessor;
import entity.Bike;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class RentBikeControllerTest {

    private RentBikeController rentBikeController;
    private CreditCard card;
    private BikeAccessor bikeAccessor;
    private Bike bike;
    private ReturnBikeController returnBikeController;

    @BeforeEach
    void setUp() {
        bikeAccessor = new BikeAccessor();
        returnBikeController = new ReturnBikeController();
        CategoryAccessor categoryAccessor = new CategoryAccessor();
        Category category = categoryAccessor.get(1);

        bike = new NormalBike(1, "No_001", "38MH_001", false, 10, 3,1,category,"assets/images/bike/bike01.jpg");
        rentBikeController = new RentBikeController();
        card = new CreditCard("kstn_group8_2021", "Group 8", "412", "1125");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void requestBike() {
        String barcode = "1111111110";
        BarcodeProcessor processor = new BarcodeProcessor();
        Bike bike = rentBikeController.requestBike(barcode);
        int bikeId = -1;
        try{
            bikeId = processor.processBarcode(barcode);
            System.out.println(bikeId);
        } catch(MalformedURLException e1) {
            System.out.println(e1.fillInStackTrace());
        } catch (BarcodeException e2) {
            System.out.println(e2.fillInStackTrace());
        }
        assertEquals(bikeId, bike.getBikeId());
    }

    @Test
    void requestRentBike() {
        Bike bike = (new BikeAccessor()).get(1);

        String notification = rentBikeController.requestRentBike(1, bike, card);
        assertEquals("You rent bike successful!", notification);
    }
}