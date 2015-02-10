package utils;

import java.util.HashMap;

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
		params.put("lat", ""+latitude);
		params.put("lng", ""+longitude);
		params.put("username","alexandru.atomei");
		params.put("type", "json");
		try {
			String resp = Request.sendGet(
					"http://ws.geonames.org/countryCode", params,
					false);
			JSONObject object = new JSONObject(resp);
				String countryName = object.getString("countryName");
				return countryName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
}
