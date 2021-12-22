package controller;

import entity.RentedBike;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    void setUp() {
        simpleCostCalculator = new SimpleCostCalculator();
    }

    @ParameterizedTest
    @CsvSource({
            "2021-12-22 15:00:00,30,80000",
            "2021-12-22 15:00:00,630,180000",
            "2021-12-22 15:00:00,720,200000",
            "2021-12-22 15:00:00,1000,200000",
            "2021-12-22 15:00:00,1455,202000",
            "2021-12-22 115:00:00,1500,208000"
    })
    void checkout(String start, long minute, float expected) throws ParseException {
        Date temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
        long duration = TimeUnit.MINUTES.toMillis(minute);
        Timestamp startTime = new Timestamp(temp.getTime());
        Timestamp endTime = new Timestamp(temp.getTime() + duration);
        Rent rent = new Rent(1, "1", 1, 1, true, null, 1, 0,
                startTime,
                endTime, 5);
        float cost = simpleCostCalculator.checkout(rent, endTime);
        assertEquals(expected, cost);
    }
}