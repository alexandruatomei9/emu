package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class Request {

	public static final String scheme = "http";
	public static final String serverUrl = "localhost:8080/EMUServer/rest";
	private final static String USER_AGENT = "Mozilla/5.0";

	public static String sendGet(String path, Map<String, String> parameters)
			throws Exception {

		URIBuilder builder = new URIBuilder();
		builder.setScheme(scheme).setHost(serverUrl).setPath(path);

		if (parameters!=null &&!parameters.isEmpty()) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				builder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		URI uri = builder.build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(uri);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();

	}

	// HTTP POST request
	public static String sendPost(String path, Map<String, String> parameters)
			throws Exception {

		String url = scheme + "://" + serverUrl + path;

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			urlParameters.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();

	}
}
