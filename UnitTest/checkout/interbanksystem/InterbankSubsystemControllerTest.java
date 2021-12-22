package checkout.interbanksystem;

import checkout.CreditCard;
import checkout.PaymentTransaction;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class InterbankSubsystemControllerTest {

    private InterbankBoundary interbankBoundary = new InterbankBoundary();
    private InterbankSubsystemController interbankSubsystemController;
    private CreditCard card;
    @BeforeEach
    void setUp() {
        interbankBoundary = new InterbankBoundary();
        interbankSubsystemController = new InterbankSubsystemController();
        card = new CreditCard("kstn_group8_2021", "Group 8", "412", "1125");
    }

    @Test
    void payRental() throws IOException, JSONException, ParseException {
        PaymentTransaction a = interbankSubsystemController.payRental(card, 12, "test");
        assertEquals(12, a.getAmount());
    }
}