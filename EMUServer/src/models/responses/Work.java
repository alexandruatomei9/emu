package models.responses;

public class Work {
	protected String name;
	protected String uri;

	public Work(String name, String uri) {
		this.name = name;
		this.uri = uri;
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
}
