package entity;

public class Category {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private float bikeValue;
    private float costRatio;

    public Category(int categoryId, String categoryName, String categoryDescription, float bikeValue, float costRatio) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.bikeValue = bikeValue;
        this.costRatio = costRatio;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public float getBikeValue() {
        return bikeValue;
    }

    public float getCostRatio() {
        return costRatio;
    }
}
