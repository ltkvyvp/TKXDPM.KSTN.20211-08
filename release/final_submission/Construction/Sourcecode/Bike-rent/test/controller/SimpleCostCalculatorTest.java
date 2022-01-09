package controller;

import accessor.BikeAccessor;
import accessor.CategoryAccessor;
import controller.calculator.SimpleCostCalculator;
import entity.Bike;
import entity.Category;
import entity.Rent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCostCalculatorTest {
    private SimpleCostCalculator simpleCostCalculator;
    private ReturnBikeController returnBikeController;
    private BikeAccessor bikeAccessor;
    private Bike bike;

    @BeforeEach
    void setUp() {

        bikeAccessor = new BikeAccessor();
        CategoryAccessor categoryAccessor = new CategoryAccessor();
        Category category = categoryAccessor.get(1);
        bike = new Bike(1, "No_001", "38MH_001", 0, false, 10, 3,1,category,"assets/images/bike/bike01.jpg");
        returnBikeController = new ReturnBikeController();
        simpleCostCalculator = new SimpleCostCalculator();
    }

    @ParameterizedTest
    @CsvSource({
            "2012-07-10 14:58:00,30,10",
            "2012-07-10 14:58:00,630,133",
            "2012-07-10 14:58:00,720,151",
            "2012-07-10 14:58:00,1000,208",
            "2012-07-10 14:58:00,1455,298",
            "2012-07-10 14:58:00,1500,307"
    })
    void checkout(String start, long minute, float expected) throws ParseException {
        Date temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
        long duration = TimeUnit.MINUTES.toMillis(minute);
        Timestamp startTime = new Timestamp(temp.getTime());
        Timestamp endTime = new Timestamp(temp.getTime() + duration);
        Rent rent = new Rent(1, 1, bike, 10, startTime, endTime);
        float cost = simpleCostCalculator.checkout(rent, endTime);
        assertEquals(expected, cost);
    }
}