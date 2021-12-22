package controller;

import checkout.CreditCard;
import accessor.RentAccessor;
import entity.Rent;
import entity.Bike;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ReturnBikeControllerTest {

    private ReturnBikeController returnBikeController;

    @BeforeEach
    void setUp() {
        returnBikeController = new ReturnBikeController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void requestReturnBike() {
        int userId = 1;
        CreditCard creditCard = new CreditCard("kstn_group8_2021", "Group 8", "412", "1125");
        Rent rent = (new RentAccessor()).get(30);
        rent.getBike().setStatus(false);

        assertEquals("You returned bike successfully!",
                this.returnBikeController.requestReturnBike(
                        userId,
                        rent,
                        creditCard,
                        rent.getEnd_time()
                ));
    }
}