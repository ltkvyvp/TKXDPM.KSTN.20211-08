package controller.calculator;

import entity.Bike;
import entity.Rent;

import java.sql.Timestamp;

public class NewBikeMethodCostCalculator implements CostComputer{
    @Override
    public float checkout(Rent rent, Timestamp endTime) {
        // TODO: Add new calculation method here
        return 1000;
    }

    @Override
    public float getDeposit(Bike bike){
        // TODO: Add new calculation method here
        return 1000;
    }
}
