package views.screen;

import controller.BikeController;
import controller.DockController;
import entity.Bike;
import entity.Dock;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;
import java.util.List;

import static views.screen.HomeScreenHandler.LOGGER;

public class DockDetailScreenHandler extends BaseScreenHandler{

    /**
     * Class uses for wrapping information together in purpose displaying information in bikes' GUI table
     */
    public class Wrapper{
        private Bike bike;
        private int index;

        public Wrapper(Bike bike, int index){
            this.bike = bike;
            this.index = index+1;
        }

        public StringProperty statusProperty(){
            if (this.bike.isStatus()) {
                return new SimpleStringProperty("Đang thuê");
            }
            return new SimpleStringProperty("Có sẵn");
        }
        public Integer getIndex(){
            return this.index;
        }
        public String getBarcode(){
            return this.bike.toHash();
        }
        public String getCategory(){
            return this.bike.getCategory().getCategoryName();
        }
        public int getBikeId(){
            return this.bike.getBikeId();
        }
        public String getBikeName(){
            return this.bike.getBikeName();
        }
        public Bike getBike(){
            return this.bike;
        }
    }

    @FXML
    private Button backBtn;

    /**
     * Table displays list of bikes
     */
    @FXML
    private TableView<Wrapper> bikeListTable;

    /**
     * table column
     */
    @FXML
    private TableColumn<Wrapper, Integer> index, bikeId;

    /**
     * table column
     */
    @FXML
    private TableColumn<Wrapper, String> barcode, bikeName, category, status;
    @FXML
    private SplitMenuButton searchBikeBtn;

    /**
     * the text field for searched information
     */
    @FXML
    private TextField searchBikeTextField;

    @FXML
    private ImageView logo, backIcon;

    /**
     * the image of current dock
     */
    @FXML
    private ImageView dockImage;

    /**
     * title of this screen, is set dock's name
     */
    @FXML
    private Label titleLabel;

    /**
     * current dock in this screen
     */
    private int dockID;
    private static int searchOption=-1;

    public DockDetailScreenHandler(Stage stage, String screenPath, int dockID) throws IOException {
        super(stage, screenPath);
        this.dockID = dockID;

        // Set image:
        super.setImage(true);
        // BackIcon:
        super.setSingleImage(backIcon,Configs.IMAGE_PATH + "/" +"backarrow.jpg");
        // Set title
        super.setScreenTitle("Dock Detail");
    }

    /**
     * Set up something in GUI, such as images, title, dataloader, option configuration...
     */
    public void initiate(){
        System.out.println((DockDetailScreenHandler) this.loader.getController());
        DockController dockController = new DockController();
        Dock dock = dockController.getDock(this.dockID);
        // Set title, image and label name:
        titleLabel.setText("Bãi xe " + dock.getDockName());
        this.setSingleFitImage(dockImage,Configs.IMAGE_PATH + "/dock/" +dock.getImagePath(), 400, 220);

        // Load data to table:
        List<Bike> bikes = dockController.getBikeByDockId(this.dockID);
        this.loadDataToBikeTable(bikes);

        // Search option:
        ObservableList<MenuItem> menuItems = searchBikeBtn.getItems();
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem menuItem = menuItems.get(i);
            int finalI = i;
            if (i == menuItems.size() - 1) {
                menuItem.setOnAction((e) -> {
                    this.loadDataToBikeTable(bikes);
                });
            } else {
                menuItem.setOnAction((e) -> {
                    searchOption = finalI;
                });
            }
        }
    }

    /**
     * Load bikes' data to table
     * @param bikes list of bikes
     */
    public void loadDataToBikeTable(List<Bike> bikes){
        ObservableList<Wrapper> wrapper = FXCollections.observableArrayList();
        for (int i = 0; i < bikes.size(); i++){
            wrapper.add(new Wrapper(bikes.get(i), i));
        }
        // Thêm thông tin dock vào bảng:

        index.setCellValueFactory(new PropertyValueFactory<Wrapper, Integer>("index"));
        bikeId.setCellValueFactory(new PropertyValueFactory<Wrapper, Integer>("bikeId"));
        barcode.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("barcode"));
        status.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("status"));
        bikeName.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("bikeName"));
        category.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("category"));
        bikeListTable.setItems(wrapper);
    }

    /**
     * Handle event when user click back button, go back to home screen
     * @param event button click event
     */
    @FXML
    public void goBackPreviousScreen(ActionEvent event) {
        System.out.println("User clicked back icon!");
        this.getPreviousScreen().show();
    }

    /**
     * Handle event when user click on a bike in list bike of dock, go to the detail bike information screen
     @param event mouse click event
     */
    @FXML
    public void goBikeDetailScreen(MouseEvent event) throws IOException{
        Bike bike = bikeListTable.getSelectionModel().getSelectedItem().getBike();
        BikeDetailScreenHandler bikeDetailScreen;
        try {
            LOGGER.info("User clicked to view a dock");
            bikeDetailScreen = new BikeDetailScreenHandler(this.stage, Configs.BIKE_DETAIL_SCREEN_PATH, bike);
            bikeDetailScreen.setHomeScreenHandler(this.homeScreenHandler);
            bikeDetailScreen.setScreenTitle("Bike Detail Screen");
            bikeDetailScreen.setBController(new BikeController());
            bikeDetailScreen.setPreviousScreen(this);
            bikeDetailScreen.initiate();
            bikeDetailScreen.show();
        } catch (IOException e1){
            LOGGER.info("Errors occured: " + e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * Handle event when user click search button to search for a bike or list of bikes with provided information
     * @param event button click event
     */
    @FXML
    void searchBike(ActionEvent event) {
        String info = searchBikeTextField.getText();
        int option = searchOption;
        if (option == 0)
            info = String.valueOf(this.inverseHash(info));
        if (option != -1 && option != 4){
            String opt = searchBikeBtn.getItems().get(option).getText();
            opt = opt.substring(0, 1).toLowerCase() + opt.substring(1);
            DockController dockController = new DockController();
            List<Bike> bikes = dockController.searchBike(opt, info, this.dockID);
            this.loadDataToBikeTable(bikes);
        }
    }

    /**
     * inverse barcode to bike's id, it only uses for purpose displaying in GUI
     * @param inverse: barcode String
     * @return bike id
     */
    public int inverseHash(String inverse){
        if (inverse.equals(""))
            return -1;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < inverse.length(); i++)
            s.append((inverse.charAt(i) == '0') ? '1' : '0');
        return Integer.parseInt(s.toString(), 2);
    }
}
