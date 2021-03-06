package models.responses;

public class Museum {
	protected String name;
	protected String uri;
	protected String thumbUri;

	public Museum(String name, String uri, String thumbUri) {
		this.name = name;
		this.uri = uri;
		this.thumbUri = thumbUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}
}
