package ro.emu.client.models;

public class MuseumThumbnail {

	private String museumName;
	private String imageUrl;
	private String getDetailsUrl;
	
	public MuseumThumbnail(){
		
	}
	
	public MuseumThumbnail(String museumName, String imageUrl, String getDetailsUrl){
		this.museumName = museumName;
		this.imageUrl = imageUrl;
		this.getDetailsUrl = getDetailsUrl;
	}
	
	public String getMuseumName() {
		return museumName;
	}
	public void setMuseumName(String museumName) {
		this.museumName = museumName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getGetDetailsUrl() {
		return getDetailsUrl;
	}

	public void setGetDetailsUrl(String getDetailsUrl) {
		this.getDetailsUrl = getDetailsUrl;
	}
}
