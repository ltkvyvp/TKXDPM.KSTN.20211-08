package views.screen;

import checkout.CreditCard;
import controller.RentBikeController;
import entity.Bike;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;

public class RentBikeScreenHandler extends BaseScreenHandler{
    @FXML
    private Button backBtn;

    @FXML
    private ImageView logo, backIcon;

    /**
     * text field object for credit card information
     */
    @FXML
    private TextField owner, cardCode, cvvCode, dateExpired;

    /**
     * button for confirming to rent bike
     */
    @FXML
    private Button confirmBtn;

    /**
     * button for getting our group's card information
     */
    @FXML
    private Button validInformationBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView bikeImage;

    /**
     * ListView object contains the information of bike
     */
    @FXML
    private ListView<String> bikeInfoListView;

    private int userId;
    /**
     * the current bike in this screen
     */
    private Bike bike;

    /**
     * Handle event when user click back button, go back to home screen
     * @param event button click event
     */
    @FXML
    void goBackPreviousScreen(ActionEvent event) {
        HomeScreenHandler homeScreenHandler = (HomeScreenHandler) this.getPreviousScreen();
        homeScreenHandler.initialize();
        homeScreenHandler.setScreenTitle("Home Screen");
        homeScreenHandler.show();
    }

    public RentBikeScreenHandler(Stage stage, String screenPath, int userId, Bike bike) throws IOException {
        super(stage, screenPath);
        this.userId = userId;
        this.bike = bike;
    }

    /**
     * Set up something in GUI such as image, button...
     */
    public void initiate() {
        // Set image and title
        this.setImage(true);
        this.setSingleImage(backIcon, Configs.IMAGE_PATH + "/backarrow.jpg");
        this.setSingleFitImage(bikeImage, bike.getImagePath(), 420, 250);
        titleLabel.setText("Rent Payment Transaction");
        confirmBtn.setText("Confirm rent bike");
        // rent data
        this.loadData();
    }

    /**
     * Load rented bikes' data to list view
     */
    public void loadData() {
        String bikeId = "Bike id: " + bike.getBikeId();
        String bikeName = "Bike name:  " + bike.getBikeName();
        String licensePlate = "License plate: " + bike.getLicensePlate();
        String pin = (bike.getCategory().getCategoryId() == 3) ? "Pin: " + Float.toString(bike.getPin()) : null;
        String status =  bike.isStatus() ? "Trạng thái: Đang cho mượn" : "Trạng thái: Có sẵn";
        String bikeCategory = "Category: " + bike.getCategory().getCategoryName();
        String description = "Description: " + bike.getCategory().getCategoryDescription();
        String startPrice = "Cost of the first 30 minutes: " + convertCurrencyFormat(bike.getInitCost()*1000) + " VNĐ";
        String costPerQuarterHour = "Cost per 15 minutes: " + convertCurrencyFormat(bike.getCostPerQuarterHour()*1000) + " VNĐ";

        ObservableList<String> items = FXCollections.observableArrayList(bikeId, bikeName, licensePlate, status, bikeCategory, description, startPrice, costPerQuarterHour);;
        if (pin != null){
            items.add(4, pin);
        }
        bikeInfoListView.setPadding(new Insets(0, 0, 0, 5));
        bikeInfoListView.setItems(items);
        bikeInfoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Handle event when user click on valid information button, fill in the default value of card
     * @param actionEvent button click event
     */
    public void resetDefaultValue(ActionEvent actionEvent) {
        // Get the data from user(fix)
        cardCode.setText("kstn_group8_2021");
        owner.setText("Group 8");
        cvvCode.setText("412");
        dateExpired.setText("1125");
    }

    /**
     * Handle event when user click on confirm rent bike event, go to the result screen
     * @param event button click event
     * @throws IOException if screen handler object isn't initiated
     */
    @FXML
    void confirm(ActionEvent event) throws IOException {
        // Get the data from user
        CreditCard creditCard = new CreditCard(cardCode.getText(), owner.getText(),
                cvvCode.getText(), dateExpired.getText());

        // Get result of rent bike from api and go to notification screen
        RentBikeController rentBikeController = new RentBikeController();
        String result = rentBikeController.requestRentBike(this.userId, this.bike, creditCard);
        if (result.equals(Configs.BIKE_IS_RENTED)){
            PopupScreen.error(Configs.BIKE_IS_RENTED);
            return;
        }
        try {
            ResultScreenHandler resultScreenHandler = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH);
            resultScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            resultScreenHandler.setScreenTitle("Result Screen");
            resultScreenHandler.initiate(result);
            resultScreenHandler.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
