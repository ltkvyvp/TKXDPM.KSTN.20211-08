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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.Configs;
;
import javafx.scene.paint.Color;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
        this.setSingleFitImage(bikeImage, this.bike.getImagePath(), 340, 230);

        // BackIcon:
        this.setSingleImage(backIcon,Configs.IMAGE_PATH + "/" +"backarrow.jpg");

        System.out.println((BikeDetailScreenHandler) this.loader.getController());
        // Set label:
        titleLabel.setText(this.bike.getBikeName());
        bikeNameLabel.setText(this.bike.getBikeName());

        // Load data:
        this.loadData();

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

    }

    /**
     * Load bike's information to screen
     */
    public void loadData(){
        Map<String, String> basicInfo = bike.getBasicInfo();
        Map<String, String> advancedInfo = bike.getAdvancedInfo();
        Map<String, String> info = new HashMap<>();

        info.putAll(basicInfo);
        info.putAll(advancedInfo);

        ObservableList<Node> infoLabel = vBoxInfo.getChildren();
        ObservableList<Node> label = vBox.getChildren();

        for (Map.Entry<String, String> entry : info.entrySet()) {
            String key = entry.getKey().toUpperCase(Locale.ROOT);
            String value = entry.getValue();

            Label labelKey = new Label(key);
            labelKey.setFont(Font.font("VnAvantH", FontWeight.BOLD, FontPosture.REGULAR, 18));

            Label labelValue = new Label(value);
            labelValue.setStyle("-fx-border-width: 1; " +
                    "-fx-border-color: grey; " +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: white;" +
                    "-fx-padding: 5");

            labelKey.setPrefSize(194, 43);
            labelValue.setPrefSize(467, 42);

            vBox.getChildren().add(labelKey);
            vBoxInfo.getChildren().add(labelValue);

            System.out.println(key + ":" + value);

        }

    }

    public String selectColor(){
        int rnd = new Random().nextInt(COLORS.length);
        return COLORS[rnd];
    }
}
