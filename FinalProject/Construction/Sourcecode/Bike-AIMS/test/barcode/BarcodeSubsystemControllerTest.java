package barcode;

import barcode.exception.BarcodeException;
import barcode.subsystem.BarcodeBoundary;
import barcode.subsystem.BarcodeSubsystemController;
import checkout.CreditCard;
import checkout.PaymentTransaction;
import checkout.interbanksystem.InterbankBoundary;
import checkout.interbanksystem.InterbankSubsystemController;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

public class BarcodeSubsystemControllerTest {
    private BarcodeBoundary barcodeBoundary = new BarcodeBoundary();
    private BarcodeSubsystemController barcodeSubsystemController;

    @BeforeEach
    void setUp() {
        barcodeBoundary = new BarcodeBoundary();
        barcodeSubsystemController = new BarcodeSubsystemController();
    }

    @ParameterizedTest
    @CsvSource({
            "1111111110,true",
            "as12,false",
            "123456,false",
            "123456789a,false",
            "1234567890,false",
            "f10000901,false",
            "111000111j,false",
    })

    void processBarcode(String barcode,boolean expected) {
        int bikeId = -1;
        try{
            bikeId = barcodeSubsystemController.processBarcode(barcode);
        }catch(BarcodeException e1) {
            bikeId = -1;
        }catch(MalformedURLException e2) {
            bikeId = -2;
        }
        assertEquals(bikeId > 0,expected);
    }
}
