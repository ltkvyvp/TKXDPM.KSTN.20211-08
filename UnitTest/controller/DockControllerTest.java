package controller;

import entity.Dock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DockControllerTest {

    private DockController DockController;

    @BeforeEach
    void setUp() {
        DockController = new DockController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDock() {
        int id = 1;
        Dock Dock = this.DockController.getDock(id);
        assertEquals(id, Dock.getDockId());
    }
}