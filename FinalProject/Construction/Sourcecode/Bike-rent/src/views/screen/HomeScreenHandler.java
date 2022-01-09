package views.screen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import controller.*;

import entity.Bike;
import entity.Dock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import utils.Configs;
import utils.Utils;

public class HomeScreenHandler extends BaseScreenHandler{

    public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());
    public static final int userId = 1;

    /**
     * text field for barcode
     */
    @FXML
    private TextField barcodeTextField;

    /**
     * table contains the information of dock
     */
    @FXML
    private TableView<Dock> dockInfoTable;

    /**
     * table column
     */
    @FXML
    private TableColumn<Dock, String> dockName, address;

    /**
     * table column
     */
    @FXML
    private TableColumn<Dock, Integer> dockId, emptyDockingPoints, availableBikes;

    /**
     * table column
     */
    @FXML
    private TableColumn<Dock, Float> distance, dockArea, walkingTime;

    /**
     * number of rented bikes not returning
     */
    @FXML
    private Label numberRentedBikes;

    @FXML
    private ImageView logo, rentedBikeIcon;

    /**
     * button for process barcode to rent bike
     */
    @FXML
    private Button rentBikeBtn;

    /**
     * button for redirecting to rented bike list screen
     */
    @FXML
    private Button rentedBikeCartBtn;

    /**
     * button for search dock in docks' table, required that user must choose the option in the MenuButton
     */
    @FXML
    private SplitMenuButton searchDockBtn;

    /**
     * the text field of searched information
     */
    @FXML
    private TextField searchDockTextField;

    /**
     * searching option, in {0, 1}, 0 is "dockName" and 1 is "address"
     */
    private static int searchOption = -1;

    public HomeScreenHandler(Stage stage, String screenPath) throws IOException{
        super(stage, screenPath);
        setBController(new HomeController());
        setHomeScreenHandler(this);
    }

    /**
     * Set up something for GUI, such as images, buttons, load table,...
     */
    public void initialize() {
        try {
            this.setImage(true);

            setBController(new HomeController());
            HomeController homeController = (HomeController) this.bController;
            List<Dock> docks = homeController.getAllDock();
            // Thêm vào list của FXCollections
            this.loadDataToDockTable(docks);

            // Set Image:
            this.setSingleImage(rentedBikeIcon, Configs.IMAGE_PATH + "/rentedBikeCart.png");

            // Set number of current rented bikes:
            UserController userController = new UserController();
            numberRentedBikes.setText("" + userController.getRentByUserId(userId).size());

            // Search Dock
            ObservableList<MenuItem> menuItems = searchDockBtn.getItems();
            for (int i = 0; i < menuItems.size(); i++) {
                MenuItem menuItem = menuItems.get(i);
                int finalI = i;
                if (i == menuItems.size() - 1) {
                    menuItem.setOnAction((e) -> {
                        this.loadDataToDockTable(docks);
                    });
                } else {
                    menuItem.setOnAction((e) -> {
                        searchOption = finalI;
                    });
                }
            }

            searchDockBtn.setOnAction((e)->{
                System.out.println(searchOption);
                if (searchOption != -1) {
                    String info = searchDockTextField.getText();
                    List<Dock> dockLst = homeController.searchDock(searchOption, info);
                    this.loadDataToDockTable(dockLst);
                }
            });

            logo.setOnMouseClicked(e -> {
                this.homeScreenHandler.show();
            });

        } catch (SQLException e) {
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle event when user click on dock, go to the dock detail screen of chosen dock
     * @param e mouse click
     * @throws IOException if user missed click in table
     */
    public void goDockDetailScreen(MouseEvent e) throws IOException {
        Dock dock = dockInfoTable.getSelectionModel().getSelectedItem();
        int id = dock.getDockId();
        DockDetailScreenHandler dockDetailScreen;
        try {
            LOGGER.info("User clicked to view a dock");
            dockDetailScreen = new DockDetailScreenHandler(this.stage, Configs.DOCK_DETAIL_SCREEN_PATH, id);
            dockDetailScreen.setHomeScreenHandler(this);
            dockDetailScreen.setBController(new DockController());
            dockDetailScreen.setScreenTitle("Dock Detail Screen");
            dockDetailScreen.setPreviousScreen(this);
            dockDetailScreen.initiate();
            dockDetailScreen.show();
        } catch (IOException e1){
            LOGGER.info("Errors occured: " + e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * Load docks' data to table
     * @param docks list of dock need to load to table
     */
    public void loadDataToDockTable(List<Dock> docks) {
        ObservableList<Dock> dockList = FXCollections.observableArrayList();
        dockList.addAll(docks);
        dockId.setCellValueFactory(new PropertyValueFactory<Dock, Integer>("dockId"));
        dockName.setCellValueFactory(new PropertyValueFactory<Dock, String>("dockName"));
        address.setCellValueFactory(new PropertyValueFactory<Dock, String>("address"));
        dockArea.setCellValueFactory(new PropertyValueFactory<Dock, Float>("dockArea"));
        availableBikes.setCellValueFactory(new PropertyValueFactory<Dock, Integer>("availableBikes"));
        emptyDockingPoints.setCellValueFactory(new PropertyValueFactory<Dock, Integer>("emptyDockingPoints"));
        distance.setCellValueFactory(new PropertyValueFactory<Dock, Float>("distance"));
        walkingTime.setCellValueFactory(new PropertyValueFactory<Dock, Float>("walkingTime"));
        dockInfoTable.setItems(dockList);
    }

    /**
     * Handle event when user click to view rented bike list screen
     * @param event button click event
     */
    @FXML
    void goRentedBikeListScreen(ActionEvent event) {
        RentedBikeListScreenHandler rentedBikeListScreenHandler;
        try {
            LOGGER.info("User clicked to view a dock");
            rentedBikeListScreenHandler = new RentedBikeListScreenHandler(this.stage, Configs.RENTED_BIKE_LIST_SCREEN_PATH, userId);
            rentedBikeListScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            rentedBikeListScreenHandler.setPreviousScreen(this);
            rentedBikeListScreenHandler.setScreenTitle("Rented Bike List");
            rentedBikeListScreenHandler.initiate();
            rentedBikeListScreenHandler.show();
        } catch (IOException e1){
            LOGGER.info("Errors occured: " + e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * Handle event when user click to rent bike, system will process received barcode and
     * show a pop-up if barcode processing fails or redirect to rent bike screen if successes
     * @param event button click event
     */
    @FXML
    void goRentBikeScreenHandler(ActionEvent event) throws IOException {
        // Get and barcode:
        String barcode = barcodeTextField.getText();
        BarcodeController barcodeController = new BarcodeController();
        boolean checkBarcode = barcodeController.validateBarcode(barcode);
        if (!checkBarcode){
            PopupScreen.error("Invalid Barcode!");
            return;
        }
        RentBikeScreenHandler rentBikeScreenHandler;
        RentBikeController rentBikeController = new RentBikeController();
        Bike bike = rentBikeController.requestBike(barcode);
        if (bike == null){
            PopupScreen.error("Invalid Barcode!");
            return;
        }
        try {
            LOGGER.info("User clicked barcode");
            rentBikeScreenHandler = new RentBikeScreenHandler(this.stage, Configs.CREDIT_CARD_FORM_PATH, userId, bike);
            rentBikeScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            rentBikeScreenHandler.setScreenTitle("Rent Bike Screen");
            rentBikeScreenHandler.setPreviousScreen(this);
            rentBikeScreenHandler.setBController(new RentBikeController());
            rentBikeScreenHandler.initiate();
            rentBikeScreenHandler.show();
        } catch (IOException e1) {
            LOGGER.info("Errors occured: " + e1.getMessage());
            e1.printStackTrace();
        }
    }
}