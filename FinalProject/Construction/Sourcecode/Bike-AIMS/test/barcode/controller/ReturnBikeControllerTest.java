package barcode.controller;

import accessor.BikeAccessor;
import accessor.CategoryAccessor;
import checkout.CreditCard;
import accessor.RentAccessor;
import controller.ReturnBikeController;
import entity.Bike;
import entity.Category;
import entity.NormalBike;
import entity.Rent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ReturnBikeControllerTest {

    private ReturnBikeController returnBikeController;
    private BikeAccessor bikeAccessor;
    private Bike bike;

    @BeforeEach
    void setUp() {
        bikeAccessor = new BikeAccessor();
        CategoryAccessor categoryAccessor = new CategoryAccessor();
        Category category = categoryAccessor.get(1);
        bike = new NormalBike(1, "No_001", "38MH_001", false, 10, 3,1,category,"assets/images/bike/bike01.jpg");
        returnBikeController = new ReturnBikeController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void requestReturnBike() {
        int userId = 1;
        CreditCard creditCard = new CreditCard("kstn_group8_2021", "Group 8", "412", "1125");
        Rent rent = (new RentAccessor()).get(1);
        rent.setEndTime(null);

        assertEquals("You returned bike successfully!",
                this.returnBikeController.requestReturnBike(
                        userId,
                        rent,
                        creditCard,
                        rent.getStartTime()
                ));
    }

    void calculateCost() {
        Rent rent = new Rent(1, 1, bike, 10, new Timestamp(2022, 1, 1, 1, 0, 0, 0),
                new Timestamp(2022, 1, 1, 2, 0, 0, 0));
        float cost = this.returnBikeController.calculateCost(rent, rent.getEndTime());
        assertEquals(19, cost);
    }
}
