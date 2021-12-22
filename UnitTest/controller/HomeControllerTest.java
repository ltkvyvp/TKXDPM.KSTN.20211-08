package controller;

import entity.Dock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void getDocks() {
        List<Dock> Docks = homeController.getDocks();
        assertEquals(true, Docks.size() > 0);
    }
}