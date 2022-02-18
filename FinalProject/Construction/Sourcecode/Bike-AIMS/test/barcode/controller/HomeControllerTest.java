package barcode.controller;

import controller.HomeController;
import entity.Dock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private HomeController homeController;

    @BeforeEach
    void setUp() {
        homeController = new HomeController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBriefStations() {
        List<Dock> Docks = new ArrayList<Dock>();
        try {
            Docks = homeController.getAllDock();
        }  catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
        assertEquals(true, Docks.size() > 0);
    }
}