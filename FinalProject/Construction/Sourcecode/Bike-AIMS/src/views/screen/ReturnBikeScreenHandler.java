package views.screen;

import checkout.CreditCard;
import controller.RentBikeController;
import controller.calculator.CostComputer;
import entity.Bike;
import entity.Rent;
import controller.ReturnBikeController;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import utils.Utils;

public class ReturnBikeScreenHandler extends BaseScreenHandler{
    @FXML
    private Button backBtn;

    @FXML
    private ImageView logo, backIcon;

    /**
     * text field object store creditcard's information
     */
    @FXML
    private TextField owner, cardCode, cvvCode, dateExpired;

    /**
     * button for confirming to return bike
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

    @FXML
    private ListView<String> bikeInfoListView;

    /**
     * the current rent entity
     */
    private Rent rent;

    /**
     * the current rented bike
     */
    private Bike rentedBike;

    /**
     * Handle event when user click back button, go back to rented bike list screen
     * @param event button click event
     * @throws IOException if screen handler could not be initiated
     */
    @FXML
    void goBackPreviousScreen(ActionEvent event) throws IOException {
        RentedBikeListScreenHandler rentedBikeListScreenHandler = new RentedBikeListScreenHandler(this.getPreviousScreen().stage, Configs.RENTED_BIKE_LIST_SCREEN_PATH, rent.getUserId());
        rentedBikeListScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
        rentedBikeListScreenHandler.initiate();
        rentedBikeListScreenHandler.show();
    }


    public ReturnBikeScreenHandler(Stage stage, String screenPath, Rent rent) throws IOException {
        super(stage, screenPath);
        this.rent = rent;
        this.rentedBike = rent.getRentedBike();
    }

    /**
     * Set up something in GUI, such as image, label...
     */
    public void initiate() {
        // Set image and title
        this.setImage(true);
        this.setSingleImage(backIcon, Configs.IMAGE_PATH + "/backarrow.jpg");
        this.setSingleFitImage(bikeImage, rentedBike.getImagePath(), 420, 250);
        titleLabel.setText("Return Payment Transaction");
        confirmBtn.setText("Confirm return bike");
        // rent data
        this.loadData();
    }

    /**
     * Load bike information's data to list view
     */
    public void loadData() {
        ObservableList<String> items = FXCollections.observableArrayList();;
        Map<String, String> info = new HashMap<>();
        info.putAll(rentedBike.getBasicInfo());
        info.putAll(rentedBike.getAdvancedInfo());

        items.add("     - BIKE INFORMATION: ");
        for (Map.Entry<String, String> entry : info.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            items.add("+ " + key + " : " + value);
        }

        // Rent info:
        // Get time and cost up to now:
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());

        // Get total rented cost:
        ReturnBikeController returnBikeController = new ReturnBikeController();
        float cost = returnBikeController.calculateCost(rent, now);
        String costUpToNow = "+ Rented Cost up to now: " + Utils.convertCurrencyFormat(cost*1000);
        String timeUpToNow = "+ Rented Time up to now: " + Float.toString((float)TimeUnit.MILLISECONDS.toMinutes(now.getTime() - rent.getStartTime().getTime())/60) + " hours";
        String startTime = "+ Start time: " + rent.getStartTime().toString();

        // Get deposit:
        RentBikeController rentBikeController = new RentBikeController();
        float deposit = rentBikeController.calculateDeposit(rentedBike);
        String depo = "+ Deposit: " + Utils.convertCurrencyFormat(deposit * 1000);

        items.add("     - RENT INFORMATION: ");
        items.addAll(startTime, depo, timeUpToNow, costUpToNow);
        bikeInfoListView.setPadding(new Insets(0, 0, 0, 5));
        bikeInfoListView.setItems(items);
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
     * Handle event when user click on return bike button, go to result screen
     * @param event button click event
     * @throws IOException if screen handler could not be initiated
     */
    @FXML
    void confirm(ActionEvent event) throws IOException {
        // Get the data from user
        CreditCard creditCard = new CreditCard(cardCode.getText(), owner.getText(),
                cvvCode.getText(), dateExpired.getText());
        Date date = new Date();
        java.sql.Timestamp end = new Timestamp(date.getTime());

        // Get result of rent bike from api and go to notification screen
        ReturnBikeController returnBikeController = new ReturnBikeController();
        String result = returnBikeController.requestReturnBike(this.rent.getUserId(), this.rent, creditCard, end);

        ResultScreenHandler resultScreenHandler;
        try {
            resultScreenHandler = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH);
            resultScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            resultScreenHandler.setScreenTitle("Result Screen");
            resultScreenHandler.initiate(result);
            resultScreenHandler.show();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }
}
