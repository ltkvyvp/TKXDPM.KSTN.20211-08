package barcode.controller;

import controller.UserController;
import entity.Dock;
import entity.Rent;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRentByUserId() {
        int userId = 1;
        List<Rent> rents = new ArrayList<Rent>();
        rents = userController.getRentByUserId(userId);
        assertEquals(userId, rents.get(0).getUserId());
        rents.forEach(rent -> assertEquals(null, rent.getEndTime()));
    }
}