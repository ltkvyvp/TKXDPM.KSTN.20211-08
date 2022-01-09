package views.screen;

import controller.ReturnBikeController;
import controller.UserController;
import entity.Rent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

;import static views.screen.HomeScreenHandler.LOGGER;

public class RentedBikeListScreenHandler extends BaseScreenHandler {

    private int userId;

    @FXML
    private Button backBtn;

    @FXML
    private ImageView logo, backIcon;

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<Rent> rentedBikeListView;

    @FXML
    private ListView<String> timerListView;

    @FXML
    private ImageView rentedBikeListImage;

    /**
     * List of the rented time of all the rents
     */
    private List<Long> totalSecond = new ArrayList<>();
    private Timer timer =  new Timer();
    private int numberItems;

    /**
     * Handle event when user click back button, go back to home screen
     * @param event button click event
     */
    @FXML
    void goBackPreviousScreen(ActionEvent event) {
        HomeScreenHandler homeScreenHandler = (HomeScreenHandler) this.homeScreenHandler;
        homeScreenHandler.initialize();
        homeScreenHandler.show();
    }

    public RentedBikeListScreenHandler(Stage stage, String screenPath, int userId) throws IOException {
        super(stage, screenPath);
        this.userId = userId;
    }

    /**
     * Set up something in GUI such as images, effect, label...
     */
    public void initiate(){
        // Set image:
        this.setImage(true);
        // BackIcon:
        this.setSingleImage(backIcon,Configs.IMAGE_PATH + "/" +"backarrow.jpg");
        this.setSingleImage(rentedBikeListImage, Configs.IMAGE_PATH + "/rentedBikeListImage.jpg");
        // Set title
        this.setScreenTitle("Rented Bike List Screen");
        // Set title label:
        titleLabel.setText("List of Rented Bikes");
        System.out.println((RentedBikeListScreenHandler) this.loader.getController());
        this.loadData();
    }

    /**
     * Load rent's data to list view
     */
    public void loadData(){
        //Rented bike list took from user
        UserController userController = new UserController();
        List<Rent> rentLst = userController.getRentByUserId(this.userId);
        this.numberItems = rentLst.size();

        ObservableList<Rent> items = FXCollections.observableArrayList(rentLst);
        // items: toString method of Rent Class
        rentedBikeListView.setItems(items);
        ObservableList<String> timer = FXCollections.observableArrayList(Collections.nCopies(numberItems, "00:00:00"));
        timerListView.setItems(timer);
        setStopWatch(rentLst);

    }

    /**
     * Handle event when people click a rent in list view to go return bike screen
     * @param event mouse click event
     */
    @FXML
    void goReturnBikeScreen(MouseEvent event) {
        Rent rent = rentedBikeListView.getSelectionModel().getSelectedItem();
        if (rent == null)
            return;
        ReturnBikeScreenHandler returnBikeScreenHandler;
        try {
            LOGGER.info("User clicked to return bike");
            returnBikeScreenHandler = new ReturnBikeScreenHandler(this.stage, Configs.CREDIT_CARD_FORM_PATH, rent);
            returnBikeScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            returnBikeScreenHandler.setScreenTitle("Return Bike Screen");
            returnBikeScreenHandler.setBController(new ReturnBikeController());
            returnBikeScreenHandler.setPreviousScreen(this);
            returnBikeScreenHandler.initiate();
            returnBikeScreenHandler.show();
        } catch (IOException e1){
            LOGGER.info("Errors occured: " + e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * change simple format from float to String, insert '0' in the head if value less than 10
     * @param value value need to change format
     * @return String after formatted
     */
    private static String format(long value){
        if (value < 10){
            return 0 + "" + value;
        }
        return String.valueOf(value);
    }

    /**
     * Convert from a duration of seconds to format h:m:s
     * @param index index of a list of time of rent
     * @return formatted String
     */
    private String convertTime(int index){
        long totalSec = totalSecond.get(index);
        long min = TimeUnit.SECONDS.toMinutes(totalSec);
        long sec = totalSec - (min*60);
        long hr = TimeUnit.SECONDS.toHours(totalSec);
        min = min - (hr*60);
        totalSecond.set(index, totalSec + 1);
        return "" + format(hr) + ":" + format(min) + ":" + format(sec);
    }

    /**
     * Display stopwatch in GUI
     * @param rentLst list of rents
     */
    public void setStopWatch(List<Rent> rentLst){
        if (rentLst.isEmpty())
            return;
        Timestamp now = new Timestamp(new Date().getTime());
        for (Rent rent : rentLst) {
            long sec = (long) TimeUnit.MILLISECONDS.toSeconds(now.getTime() - rent.getStartTime().getTime());
            totalSecond.add(sec);
        }
        int numItems = this.numberItems;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Thread t = new Thread(() -> {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < numItems; i++){
                                timerListView.getItems().set(i, convertTime(i));
                            }
                        }
                    });
                });
                t.start();
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

}
