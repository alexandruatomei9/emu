package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

public class Request {

	private final static String USER_AGENT = "Mozilla/5.0";

	public static String sendGet(String path, Map<String, String> parameters,
			boolean useScheme) throws Exception {

		URIBuilder builder = new URIBuilder();

		builder.setPath(path);

		if (parameters != null && !parameters.isEmpty()) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				builder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		URL obj = new URL(
				URLDecoder.decode(builder.build().toString(), "UTF-8"));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();

	}

}
