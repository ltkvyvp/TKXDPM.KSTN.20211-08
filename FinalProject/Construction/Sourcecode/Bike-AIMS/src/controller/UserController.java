package controller;

import accessor.RentAccessor;
import entity.Rent;

import java.util.List;

public class UserController {
    /**
     * RentAccessor object for accessing to rent in database
     */
    private RentAccessor rentAccessor;

    public UserController(){
        this.rentAccessor = new RentAccessor();
    }

    /**
     * Query a list of rents of a user satisfy that the rent's bike is not returned
     * @param userId the user's id
     * @return list of rent
     */
    public List<Rent> getRentByUserId(int userId){
        return this.rentAccessor.getRentByUserId(userId);
    }
}
