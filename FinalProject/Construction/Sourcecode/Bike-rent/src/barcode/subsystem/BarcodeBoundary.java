package barcode.subsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class BarcodeBoundary {
	/**
	 * Send request to server barcode
	 * @param url: the api's url
	 * @return response
	 */
	public static String request(URL url) {
		String response = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("charset", "utf-8");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			String inputLine;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));


			StringBuilder output = new StringBuilder();
			while ((inputLine = br.readLine()) != null)
				output.append(inputLine);
			br.close();
			// Read json response:
			response = output.toString();
			System.out.println("Output from Server .... " + response);
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}


