package views.screen;

import java.io.File;
import java.io.IOException;
import java.util.*;

import controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Configs;

public class BaseScreenHandler {

	/**
	 * the images in foot part of application
	 */
	@FXML
	private ImageView footImage11, footImage12, footImage13, footImage21, footImage22;

	/**
	 * the logo of application
	 */
	@FXML
	private ImageView logo;

	private Scene scene;
	private BaseScreenHandler prev;
	protected BaseController bController;
	protected final Stage stage;
	protected HomeScreenHandler homeScreenHandler;
	protected Hashtable<String, String> messages;
	protected AnchorPane content;
	protected FXMLLoader loader;

	private BaseScreenHandler(String screenPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(screenPath));
		this.loader.setController(this);
		this.content = (AnchorPane) loader.load();
		this.stage = new Stage();
	}

	public void setPreviousScreen(BaseScreenHandler prev) {
		this.prev = prev;
	}

	public BaseScreenHandler getPreviousScreen() {
		return this.prev;
	}

	public BaseScreenHandler(Stage stage, String screenPath) throws IOException {
		this.loader = new FXMLLoader(getClass().getResource(screenPath));
		this.loader.setController(this);
		this.content = loader.load();
		this.stage = stage;
	}

	public void show() {
		if (this.scene == null) {
			this.scene = new Scene(this.content);
		}
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	public void setScreenTitle(String string) {
		this.stage.setTitle(string);
	}

	public void setBController(BaseController bController){
		this.bController = bController;
	}

	public BaseController getBController(){
		return this.bController;
	}

	public void forward(Hashtable messages) {
		this.messages = messages;
	}

	public void setHomeScreenHandler(HomeScreenHandler HomeScreenHandler) {
		this.homeScreenHandler = HomeScreenHandler;
	}

	/**
	 * Set image in the foot part and set logo icon to GUI
	 * the logo can be clicked for redirecting to homescreen if param homeClick is set true
	 * @param homeClick
	 */
	public void setImage(boolean homeClick){
		// Config:
		List<ImageView> imgViews = List.of(footImage11, footImage12, footImage13, footImage21, footImage22, logo);
		Map<ImageView, String> map = new HashMap<ImageView, String>();
		int i = 0;
		for (i = 0; i<imgViews.size()-1; i++)
			map.put(imgViews.get(i), "foot"+i+".jpg");
		map.put(imgViews.get(i), "logo.jpg");
		
		if (homeClick){
			logo.setOnMouseClicked(e -> {
				this.homeScreenHandler.initialize();
				this.homeScreenHandler.setScreenTitle("Home Screen");
				this.homeScreenHandler.show();
			});
		}

		// fix image path caused by fxml
		for (Map.Entry<ImageView, String> entry : map.entrySet()) {
			this.setSingleImage(entry.getKey(), Configs.IMAGE_PATH + "/" + entry.getValue());
		}
	}

	/**
	 * Set one image to GUI
	 * @param imgView an object in FXML
	 * @param imgPath the path to image
	 */
	public void setSingleImage(ImageView imgView, String imgPath){
		File f = new File(imgPath);
		Image img = new Image(f.toURI().toString());
		imgView.setImage(img);
		imgView.setBlendMode(BlendMode.MULTIPLY);
	}

	/**
	 * Set one image to GUI with certain with and height
	 * @param imgView an object in FXML
	 * @param imgPath the path to image
	 * @param width the width of image user wants to display
	 * @param height the height of image user wants to display
	 */
	public void setSingleFitImage(ImageView imgView, String imgPath, int width, int height){
		File f = new File(imgPath);
		Image img = new Image(f.toURI().toString(), width, height, false, false);
		imgView.setImage(img);
		imgView.setBlendMode(BlendMode.MULTIPLY);
	}
}
