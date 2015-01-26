package models.responses;

public class GeoMuseum {
	private float latitude;
	private float longitude;
	private String resourceURI;
	private String name;

	public GeoMuseum(float latitude, float longitude, String resourceURI,
			String name) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.resourceURI = resourceURI;
		this.name = name;
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

	public String getResourceURI() {
		return resourceURI;
	}

	public void setResourceURI(String resourceURI) {
		this.resourceURI = resourceURI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
