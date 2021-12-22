package controller

import barcode.SimpleBarcodeProcessor;
import checkout.CreditCard;
import accessor.BikeAccessor;
import entity.Bike;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentBikeControllerTest {

    private RentBikeController rentBikeController;
    private CreditCard card;

    @BeforeEach
    void setUp() {
        rentBikeController = new RentBikeController();
        card = new CreditCard("kstn_group8_2021", "Group 8", "412", "1125");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void requestBike() {
        String barcode = "1";
        SimpleBarcodeProcessor processor = new SimpleBarcodeProcessor();
        Bike bike = rentBikeController.requestBike(barcode);
        int bikeId = processor.processBarcode(barcode);
        assertEquals(bikeId, bike.getBikeId());
    }

    @Test
    void requestRentBike() {
        Bike bike = (new BikeAccessor()).get(10);
        String notification = rentBikeController.requestRentBike(1, bike, card);

        assertEquals("Your transaction is successful!", notification);
    }
}