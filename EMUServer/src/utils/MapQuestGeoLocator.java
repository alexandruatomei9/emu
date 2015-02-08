package utils;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapQuestGeoLocator {
	private static final String API_KEY = "Fmjtd%7Cluu8216ynq%2C8n%3Do5-942sgu";

	public static String getLocationFor(Float latitude, Float longitude,
			LocationType locationType) {
		switch (locationType) {
		case Country:
			return extractValueOfField(latitude, longitude, "country");
		case Locality:
			return extractValueOfField(latitude, longitude, "locality");
		default:
			return null;
		}
	}

	private static String extractValueOfField(Float latitude, Float longitude,
			String fieldName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("key", API_KEY);
		params.put("location", latitude + "," + longitude);
		try {
			String response = Request.sendGet(
					"http://www.mapquestapi.com/geocoding/v1/reverse", params,
					false);
			JSONObject object = new JSONObject(response);
			JSONArray results = object.getJSONArray("results");
			if (results != null && results.length() > 0) {
				JSONObject firstResult = (JSONObject) results.get(0);
				if (firstResult != null && firstResult.has("locations")) {
					JSONArray locations = firstResult.getJSONArray("locations");
					if (locations != null && locations.length() > 0) {
						JSONObject location = locations.getJSONObject(0);
						if (fieldName.equalsIgnoreCase("country")) {
							return location.getString("adminArea1");
						} else if (fieldName.equalsIgnoreCase("locality")) {
							return location.getString("adminArea4");
						}
					}
				}
			}
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
