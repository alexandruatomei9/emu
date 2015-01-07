package models.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "museum")
@XmlType(propOrder = { "name", "uri", "thumbUri" })
public class Museum {
	private String name;
	@XmlElement(name = "dbpResourceUri")
	private String uri;
	@XmlElement(name = "dbpThumbnailUri")
	private String thumbUri;

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
