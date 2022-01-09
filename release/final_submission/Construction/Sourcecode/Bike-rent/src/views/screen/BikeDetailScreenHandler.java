package views.screen;

import entity.Bike;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.Random;

public class BikeDetailScreenHandler extends BaseScreenHandler {

    /**
     * the current bike in this screen
     */
    private Bike bike;

    @FXML
    private Button backBtn;

    /**
     * information of the current bike
     */
    @FXML
    private Label textTitle, bikeId, bikeName, bikeNameLabel, category, costPerQuarterHour, description, startingPrice, pin, status, pinLabel;

    /**
     * the title of this screen, and is set name of bike
     */
    @FXML
    private Label titleLabel;

    /**
     * image of current bike
     */
    @FXML
    private ImageView bikeImage;

    @FXML
    private ImageView logo, backIcon;

    /**
     * object of JavaFX contains bike's information
     */
    @FXML
    private VBox vBox, vBoxInfo;

    /**
     * Handle event when user clicks back button to return Dock Detail Screen
     * @param event button click event
     */
    @FXML
    void goBackPreviousScreen(ActionEvent event) {
        this.setScreenTitle("Dock Detail Screen");
        this.getPreviousScreen().show();
    }

    /**
     * the colors, is used for setting some effects
     */
    private static final String[] COLORS =  {"yellow", "red", "purple", "black", "aquamarine", "orange", "green"};

    public BikeDetailScreenHandler(Stage stage, String screenPath, Bike bike) throws IOException {
        super(stage, screenPath);
        this.bike = bike;
    }

    /**
     * Set up something for GUI, such as images, labels, effect and loading data
     */
    public void initiate(){
        // Set image:
        this.setImage(true);
        // BackIcon:
        this.setSingleImage(backIcon,Configs.IMAGE_PATH + "/" +"backarrow.jpg");

        System.out.println((BikeDetailScreenHandler) this.loader.getController());
        // Set effect:
        String color = this.selectColor();
        DropShadow dropShadow = new DropShadow(10, Color.web(color));
        textTitle.setStyle("-fx-text-fill: "+color);
        bikeNameLabel.setStyle("-fx-text-fill: "+color);
        vBox.getChildren().forEach(node -> {
            if (node instanceof Label){
                node.setEffect(dropShadow);
            }
        });
        this.loadData();
    }

    /**
     * Load bike's information to screen
     */
    public void loadData(){
        this.setSingleFitImage(bikeImage, this.bike.getImagePath(), 340, 230);
        titleLabel.setText(this.bike.getBikeName());
        bikeNameLabel.setText(this.bike.getBikeName());
        String status;
        if (this.bike.isStatus())
            status = "Đang thuê";
        else
            status = "Có sẵn";
        String curPIN = "";
        if (this.bike.getCategory().getCategoryId() != 3){
            pin.setVisible(false);
            pinLabel.setVisible(false);
        } else {
            curPIN = Float.toString(this.bike.getPin());
        }
        String[] info = {""+this.bike.getBikeId(),
                         this.bike.getBikeName(),
                         status,
                         this.bike.getCategory().getCategoryName(),
                         ""+this.convertCurrencyFormat(this.bike.getInitCost()*1000)+" VNĐ",
                         ""+this.convertCurrencyFormat(this.bike.getCostPerQuarterHour())+" VNĐ",
                         this.bike.getCategory().getCategoryDescription(),
                         curPIN
                        };
        ObservableList<Node> infoLabel = vBoxInfo.getChildren();
        for (int i = 0; i < infoLabel.size(); i++){
            if (infoLabel.get(i) instanceof Label){
                ((Label) infoLabel.get(i)).setText(info[i]);
            }
        }
    }

    public String selectColor(){
        int rnd = new Random().nextInt(COLORS.length);
        return COLORS[rnd];
    }
}
