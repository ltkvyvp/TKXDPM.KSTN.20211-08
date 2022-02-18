package entity;

import java.util.HashMap;
import java.util.Map;

public class TwinBike extends Bike{

    public TwinBike(int bikeId, String bikeName, String licensePlate, boolean status,
                      float initCost, float costPerQuarterHour, int dockId, Category category, String imagePath) {
        super(bikeId, bikeName, licensePlate, status, initCost, costPerQuarterHour, dockId, category, imagePath);
    }

    @Override
    public Map<String, String> getAdvancedInfo() {
        return new HashMap<String, String>();
    }

    @Override
    public String getBikeTypeName(){return "TwinBike";};
}
