package entity;

import java.util.HashMap;
import java.util.Map;

public class EBike extends Bike{
    private float pin;

    public EBike(int bikeId, String bikeName, String licensePlate, boolean status,
                 float initCost, float costPerQuarterHour, int dockId, Category category, String imagePath, float pin) {
        super(bikeId, bikeName, licensePlate, status, initCost, costPerQuarterHour, dockId, category, imagePath);
        this.pin = pin;
    }

    @Override
    public Map<String, String> getAdvancedInfo() {
        Map<String, String> advancedInfo = new HashMap<>();
        advancedInfo.put("Pin", String.valueOf(this.pin));
        return advancedInfo;
    }

    @Override
    public String getBikeTypeName(){return "EBike";};
}
