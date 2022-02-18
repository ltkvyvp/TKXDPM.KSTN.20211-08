package accessor;

import accessor.BikeAccessor;
import entity.Bike;
import entity.Category;
import accessor.CategoryAccessor;
import entity.NormalBike;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateBikeTest {
    private BikeAccessor bikeAccessor;
    private Bike bike;

    @BeforeEach
    void setUp(){
        bikeAccessor = new BikeAccessor();
        CategoryAccessor categoryAccessor = new CategoryAccessor();
        Category category = categoryAccessor.get(1);
        bike = new NormalBike(1, "No_001", "38MH_001",  false, 10, 3,1,category,"assets/images/bike/bike01.jpg");
    }

    @Test
    void updateBike(){
        bikeAccessor.update(bike);
        Bike bikeUpdate = bikeAccessor.get(bike.getBikeId());
        assertEquals(bikeUpdate.getBikeId(), bike.getBikeId());
        assertEquals(bikeUpdate.getBikeName(), bike.getBikeName());
        assertEquals(bikeUpdate.getDockId(), bike.getDockId());
        assertEquals(bikeUpdate.getImagePath(), bike.getImagePath());
        assertEquals(bikeUpdate.getLicensePlate(), bike.getLicensePlate());
        assertEquals(bikeUpdate.getInitCost(), bike.getInitCost());
        assertEquals(bikeUpdate.getCostPerQuarterHour(), bike.getCostPerQuarterHour());
        assertEquals(bikeUpdate.isStatus(), bike.isStatus());

    }
}
