package controller.calculator;

import entity.Bike;
import entity.Category;
import entity.Rent;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class SimpleCostCalculator implements CostComputer {

    @Override
    public float checkout(Rent rent, Timestamp endTime) {
        float initCost = rent.getRentedBike().getInitCost();
        float costPerQuarterHour = rent.getRentedBike().getCostPerQuarterHour();
        float ratio = rent.getRentedBike().getCategory().getCostRatio();
        float cost = initCost;

        long minutes = getMinutes(rent.getStartTime(), endTime);
        if (minutes > 30){
            float extraTime = minutes - 30;
            float extraCost = (Math.round(extraTime/15.0f) + 1) * costPerQuarterHour;
            cost += extraCost;
        }
        return cost * ratio;
    }

    /**
     * Get the duration of rent
     * @param start: the start time of renting
     * @param end: the end time of renting (returning time)
     * @return minutes
     */
    protected long getMinutes(Timestamp start, Timestamp end) {
        long duration = (end.getTime() - start.getTime());
        return TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    @Override
    public float getDeposit(Bike bike)  {
        Category category = bike.getCategory();
        return (float) (category.getBikeValue() * 0.4);
    }
}
