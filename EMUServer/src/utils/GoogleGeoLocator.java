package utils;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleGeoLocator {
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
		params.put("sensor", "false");
		params.put("latlng", latitude + "," + longitude);
		try {
			String countryName = Request.sendGet(
					"http://maps.googleapis.com/maps/api/geocode/json", params,
					false);
			JSONObject object = new JSONObject(countryName);
			JSONArray resultsArray = object.getJSONArray("results");
			if (resultsArray != null) {
				int length = resultsArray.length();
				for (int i = 0; i < length; i++) {
					JSONObject result = (JSONObject) resultsArray.get(i);
					JSONArray address_components = result
							.getJSONArray("address_components");
					for (int j = 0; j < address_components.length(); j++) {
						JSONObject component = (JSONObject) address_components
								.get(j);
						JSONArray types = component.getJSONArray("types");
						if (types != null) {
							int typeLength = types.length();
							for (int k = 0; k < typeLength; k++) {
								String typeString = types.getString(k);
								if (typeString.equalsIgnoreCase(fieldName)) {
									return component.getString("long_name");
								}
							}
						}
					}

				}
			}

			System.out.println(countryName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
