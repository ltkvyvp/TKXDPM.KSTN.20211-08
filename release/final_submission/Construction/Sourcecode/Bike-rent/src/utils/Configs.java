package utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
/**
 * @author group08 Contains the configs for BikeRental Project
 */
public class Configs {

	/**
	 * unused api to ecopark system
	 */
	public static final String GET_BALANCE_URL = "https://ecopark-system-api.herokuapp.com/api/card/balance/118609_group1_2020";
	public static final String GET_VEHICLECODE_URL = "https://ecopark-system-api.herokuapp.com/api/get-vehicle-code/1rjdfasdfas";
	public static final String PROCESS_TRANSACTION_URL = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";
	public static final String RESET_URL = "https://ecopark-system-api.herokuapp.com/api/card/reset";
	public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiIxMTg2MDlfZ3JvdXAxXzIwMjAiLCJpYXQiOjE1OTkxMTk5NDl9.y81pBkM0pVn31YDPFwMGXXkQRKW5RaPIJ5WW5r9OW-Y";

	public static String CURRENCY = "VND";
	public static float PERCENT_VAT = 10;

	/**
	 * the path's configuration
	 */
	public static final String IMAGE_PATH = "assets/images";
	public static final String RESULT_SCREEN_PATH = "/views/fxml/ResultScreen.fxml";
	public static final String SPLASH_SCREEN_PATH = "/views/fxml/SplashScreen.fxml";
	public static final String DOCK_DETAIL_SCREEN_PATH = "/views/fxml/DockDetail.fxml";
	public static final String BIKE_DETAIL_SCREEN_PATH = "/views/fxml/BikeDetail.fxml";
	public static final String RENTED_BIKE_LIST_SCREEN_PATH = "/views/fxml/RentedBikeListScreen.fxml";
	public static final String HOME_PATH  = "/views/fxml/HomeScreen.fxml";
	public static final String CREDIT_CARD_FORM_PATH = "/views/fxml/CreditCardForm.fxml";
	public static final String POPUP_PATH = "/views/fxml/Popup.fxml";

	/**
	 * the font's configuration
	 */
	public static Font REGULAR_FONT = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 24);

	public static final String SUCCESS_NOTIFICATION = "You rent bike successful!";
	public static final String BIKE_IS_RENTED = "This bike is rented, please choose others!";
}
