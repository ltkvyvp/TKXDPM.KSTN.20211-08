package accessor;

import accessor.BikeAccessor;
import entity.Bike;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllBikesTest {

    private BikeAccessor bikeAccessor;

    @BeforeEach
    void setUp(){bikeAccessor = new BikeAccessor();}

    @Test
    void getAll(){
        List<Bike> bikes = bikeAccessor.getAll();
        bikes.forEach(bike -> assertNotNull(bike.getBikeId()));
    }
}
