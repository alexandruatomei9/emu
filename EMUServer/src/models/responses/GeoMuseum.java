package models.responses;

public class GeoMuseum {
	private float latitude;
	private float longitude;
	private String resourceUri;
	
	public GeoMuseum(float latitude, float longitude, String resourceUri) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.resourceUri = resourceUri;
	}
	
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getResourceUri() {
		return resourceUri;
	}
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}
}
