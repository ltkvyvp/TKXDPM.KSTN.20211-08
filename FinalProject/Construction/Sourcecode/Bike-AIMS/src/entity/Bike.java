package entity;

import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Bike {

    protected int bikeId;
    protected String bikeName;
    protected String licensePlate;

    protected boolean status;
    protected float initCost;
    protected float costPerQuarterHour;

    protected int dockId;
    protected Category category;

    protected String imagePath;

    private static final int HASH_LENGTH = 10;

    public Bike(int bikeId, String bikeName, String licensePlate, boolean status,
                float initCost, float costPerQuarterHour, int dockId, Category category, String imagePath) {
        this.bikeId = bikeId;
        this.bikeName = bikeName;
        this.licensePlate = licensePlate;
        this.status = status;
        this.initCost = initCost;
        this.costPerQuarterHour = costPerQuarterHour;
        this.dockId = dockId;
        this.category = category;
        this.imagePath = imagePath;
    }

    public int getBikeId() {
        return bikeId;
    }

    public String getBikeName() {
        return bikeName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isStatus() {
        return status;
    }

    public float getInitCost() {
        return initCost;
    }

    public float getCostPerQuarterHour() {
        return costPerQuarterHour;
    }

    public int getDockId() {
        return dockId;
    }

    public Category getCategory() {
        return category;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDockId(int dockId) {
        this.dockId = dockId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * mapping bike's id to barcode for only purpose that is to display in bikes' table
     * @return barcode String
     */
    public String toHash(){
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(this.bikeId));
        int length = binaryString.toString().length();
        if (length< HASH_LENGTH) {
            for (int i = 0; i < HASH_LENGTH - length; i++)
                binaryString.insert(0, '0');
        }
        StringBuilder inverseBinaryString = new StringBuilder();
        for (int i = 0; i<binaryString.toString().length(); i++)
            inverseBinaryString.append(binaryString.toString().charAt(i) == '0' ? '1' : '0');
        return inverseBinaryString.toString();
    }

    public Map<String, String> getBasicInfo(){
        Map<String, String> basicInfo = new HashMap<String, String>();
        basicInfo.put("Bike id", String.valueOf(this.bikeId));
        basicInfo.put("Bike name", this.bikeName);
        basicInfo.put("License plate", this.licensePlate);
        basicInfo.put("Status", this.status ? "Đang cho mượn" : "Có sẵn");
        basicInfo.put("Category", this.category.getCategoryName());
        basicInfo.put("Description", this.category.getCategoryDescription());
        basicInfo.put("Bike value", Utils.convertCurrencyFormat(this.category.getBikeValue() * 1000));
        basicInfo.put("Starting cost", Utils.convertCurrencyFormat(this.initCost * 1000));
        basicInfo.put("Cost per 15 min", Utils.convertCurrencyFormat(this.costPerQuarterHour * 1000));
        return basicInfo;
    }

    public abstract Map<String, String> getAdvancedInfo();

    public abstract String getBikeTypeName();
}
