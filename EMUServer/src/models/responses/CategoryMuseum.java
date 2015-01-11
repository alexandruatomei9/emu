package models.responses;

public class CategoryMuseum extends Museum {
	protected String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public CategoryMuseum(String name, String uri, String thumbUri,
			String category) {
		super(name, uri, thumbUri);
		this.category = category;
	}
}
