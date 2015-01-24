package utils;

public class GeoLocationHelper {
	public static double convertToRadians(double degreeCoordinate) {
		return Math.toRadians(degreeCoordinate);
	}

	public static double convertToDegrees(double radiansCoordinate) {
		return Math.toDegrees(radiansCoordinate);
	}

	public static boolean locationIsWithinRange(float currLocLatitude,
			float currLocLongitude, float otherLocLatitude,
			float otherLocLongitude, int radius) {
		double currentLocationLatitude = Math.toRadians(currLocLatitude);
		double currentLocationLongitude = Math.toRadians(currLocLongitude);

		double otherLocationLatitude = Math.toRadians(otherLocLatitude);
		double otherLocationLongitude = Math.toRadians(otherLocLongitude);
		double value = Math.acos((Math.sin(currentLocationLatitude)
				* Math.sin(otherLocationLatitude) + Math
				.cos(currentLocationLatitude)
				* Math.cos(otherLocationLatitude)
				* Math.cos(otherLocationLongitude - currentLocationLongitude)));
		return value * 6371 <= radius;
	}
}
