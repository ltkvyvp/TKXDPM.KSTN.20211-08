package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import checkout.CreditCard;
import checkout.PaymentTransaction;

/**
 * Class provides methods to send requests to the server and receive data back
 * @author phucnp
 * @version 1.0
 */
public class API {
	/**
	 * Attributes help format dates according to the format
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Attributes to help log information to the console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Establish connection to the server
	 * @param url: path to server
	 * @param token: token for user authentication
	 * @return connection
	 * @throws IOException
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException {
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}

	private static String readResponse(HttpURLConnection conn) throws IOException {
		BufferedReader in;
		String inputLine;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		LOGGER.info("Respone Info: " + response.toString());
		return response.substring(0, response.length()-1).toString();
	}


	/**
	 * Method to help call GET . type api
	 * @param url: path to server to request
	 * @param token: token for user authentication
	 * @return response: response from the user of type String
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		// phần 1: setup
		HttpURLConnection conn = setupConnection(url, "GET", token);

		// phần 2: đọc dữ liệu trả về từ server
		String response = readResponse(conn);
		return response;
	}

	/**
	 * Methods to help call POST APIs (e.g. payment...)
	 * @param url: path to server to request
	 * @param data: data sent to the server for processing (JSON format)
	 * @return response: response from the server (in String format)
	 * @throws IOException
	 */
	public static String post(String url, String data, String token) throws IOException {
		allowMethods("PATCH");
		// phần 1: setup
		HttpURLConnection conn = setupConnection(url, "GET", token);

		// phần 2: gửi dữ liệu
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();

		// phần 3: đọc dữ liệu trả về từ server
		String response = readResponse(conn);
		return response;
	}

	/**
	 * The method allows calling different types of API protocols such as PATCH, PUT...
	 * @deprecated only works with Java version less or qual than 11
	 * @param methods: protocol name
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
}
