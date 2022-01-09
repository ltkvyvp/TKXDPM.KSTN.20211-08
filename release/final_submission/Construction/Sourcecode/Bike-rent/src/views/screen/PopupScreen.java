package views.screen;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.Configs;


public class PopupScreen extends BaseScreenHandler{

    @FXML
    ImageView tickicon;

    @FXML
    Label message;

    public PopupScreen(Stage stage) throws IOException{
        super(stage, Configs.POPUP_PATH);
    }

    private static PopupScreen popup(String message, String imagepath, Boolean undecorated, String textFill) throws IOException{
        PopupScreen popup = new PopupScreen(new Stage());
        if (undecorated) popup.stage.initStyle(StageStyle.UNDECORATED);
        popup.message.setText(message);
        popup.message.setStyle("-fx-font-weight: bold; -fx-text-fill: "+textFill);
        popup.setImage(imagepath);
        return popup;
    }

    public static void success(String message) throws IOException{
        popup(message, Configs.IMAGE_PATH + "/tickgreen.png", false, "green").show(false);
    }

    public static void error(String message) throws IOException{
        popup(message, Configs.IMAGE_PATH + "/tickerror.png", false, "red").show(false);
    }

    public static PopupScreen loading(String message) throws IOException{
        return popup(message, Configs.IMAGE_PATH + "/" + "loading.gif", true, "aquamarine");
    }

    public void setImage(String path) {
        super.setSingleImage(tickicon, path);
    }

    public void show(Boolean autoclose) {
        super.show();
        if (autoclose) close(0.8);
    }

    public void show(double time) {
        super.show();
        close(time);
    }

    public void close(double time){
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }
}
