package barcode.controller;

import controller.DockController;
import entity.Dock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DockControllerTest {

    private DockController dockController;

    @BeforeEach
    void setUp() {
        dockController = new DockController();
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",
            "0,false"
    })

    void getDock(int input, boolean expected) {
        int id = input;
        Dock dock = this.dockController.getDock(id);
        int dockId = -1;
        if (dock != null) {
            dockId = dock.getDockId();
        }
        assertEquals(id == dockId , expected);
    }
}