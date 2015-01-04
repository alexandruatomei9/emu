package ro.emu.client.models;

public class MuseumThumbnail {

	private String museumName;
	private String imageUrl;
	
	public MuseumThumbnail(){
		
	}
	
	public MuseumThumbnail(String museumName, String imageUrl){
		this.museumName = museumName;
		this.imageUrl = imageUrl;
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
}
