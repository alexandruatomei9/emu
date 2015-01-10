package freebase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import utils.Constants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class MQLClient {

	/**
	 * Retrieve an array with results from Freebase based on MQL query
	 * 
	 * @see <a href ="https://developers.google.com/freebase/v1/mql-overview">
	 *      MQL Overview</a>
	 * @param query
	 *            - string with MQL syntax
	 * @return JSON Array
	 */
	public static JSONArray retrieveResponseForQuery(String query) {
		InputStream inputStream = MQLClient.class.getClassLoader()
				.getResourceAsStream("freebase.properties");

		Properties properties = new Properties();

		System.out.println("InputStream is: " + inputStream);
		JSONArray results = null;

		// load the inputStream using the Properties
		try {
			properties.load(inputStream);

			HttpTransport httpTransport = new NetHttpTransport();
			HttpRequestFactory requestFactory = httpTransport
					.createRequestFactory();
			JSONParser parser = new JSONParser();
			GenericUrl url = new GenericUrl(Constants.freebaseMQLEndpoint);
			url.put("query", query);
			url.put("key", properties.get("apikey"));
			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse httpResponse = request.execute();
			JSONObject response = (JSONObject) parser.parse(httpResponse
					.parseAsString());
			results = (JSONArray) response.get("result");
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
}
