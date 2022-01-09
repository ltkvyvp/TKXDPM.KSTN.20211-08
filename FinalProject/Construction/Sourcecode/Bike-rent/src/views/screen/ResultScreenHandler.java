package views.screen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;


public class ResultScreenHandler extends BaseScreenHandler{

    @FXML
    private Button homeBtn;

    /**
     * the message displays in the screen
     */
    @FXML
    private Label messageLabel;

    /**
     * the home icon, user can click to return the homescreen
     */
    @FXML
    private ImageView homeIcon;

    /**
     * Handle event when user click home icon, go to home screen
     * @param event button click event
     * @throws IOException if screen handler couldn't be initiated
     */
    @FXML
    void goHomeScreen(ActionEvent event) throws IOException {
        HomeScreenHandler homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
        homeHandler.setScreenTitle("Home screen");
        homeHandler.initialize();
        homeHandler.show();
    }

    public ResultScreenHandler(Stage stage, String screenPath) throws IOException{
        super(stage, screenPath);
    }

    /**
     * Set up something for GUI, such as images, message
     * @param message
     */
    public void initiate(String message){
        this.setImage(false);
        this.setSingleImage(homeIcon, Configs.IMAGE_PATH + "/homeicon.png");
        messageLabel.setText(message);
        if (message.contains("success")){
            messageLabel.setStyle("-fx-text-fill: green");
        } else {
            messageLabel.setStyle("-fx-text-fill: red");
        }
    }
}
