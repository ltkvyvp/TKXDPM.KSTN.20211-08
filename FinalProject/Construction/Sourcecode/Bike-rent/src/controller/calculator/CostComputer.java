package controller.calculator;

import entity.Bike;
import entity.Rent;

import java.sql.Timestamp;

/**
 * Interface provides methods related cost computation.
 */
public interface CostComputer {

    /**
     * calculate the total money for renting
     * @param rent bike that was rented by user
     * @param endTime the time user checking out
     * @return amount of money
     */
    float checkout(Rent rent, Timestamp endTime);

    /**
     * Get the debit of bike
     * @param bike
     * @return debit:
     */
    float getDebit(Bike bike);

}
