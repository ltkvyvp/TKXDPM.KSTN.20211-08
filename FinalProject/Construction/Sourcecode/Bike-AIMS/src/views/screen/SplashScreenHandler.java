package views.screen;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SplashScreenHandler implements Initializable {

    @FXML
    ImageView logo;

    @FXML
    AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("assets/images/logo.jpg");
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);
        root.setStyle("-fx-background-color: linear-gradient(#8080ff, #b3b3ff)");
    }
}