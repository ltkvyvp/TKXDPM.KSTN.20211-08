package barcode.subsystem;

import barcode.exception.BarcodeException;
import barcode.exception.InvalidBarcodeException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BarcodeSubsystemController {
	/**
	 * Configuration of parameter in the request's body sent to barcode server
	 */
	private static final String BARCODE_SERVER = "http://localhost:5000";

	/**
	 * The api's URL
	 */
	private static final String BARCODE_URI = "/request_bike_id";

	/**
	 * Get the bike's id in the response of server
	 * @param barcode: the barcode needs to process
	 * @return bike's id
	 * @throws BarcodeException if have errorCode response from server
	 * @throws MalformedURLException if can't recognize url
	 */
	public int processBarcode(String barcode) throws BarcodeException, MalformedURLException {
		URL url = new URL(BARCODE_SERVER + BARCODE_URI + "?code=" + barcode + "");
		String response = BarcodeBoundary.request(url);
		JSONObject obj = new JSONObject(response.toString());
		switch ((String) obj.getString("errorCode")) {
			case "00":
				break;
			case "01":
				throw new InvalidBarcodeException();
		}
		return obj.getInt("bikeId");
	}
}
