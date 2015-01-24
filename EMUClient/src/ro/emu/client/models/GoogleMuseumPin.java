package ro.emu.client.models;

public class GoogleMuseumPin {
	private String museumName;
	private float latitude;
	private float longitude;
	
	public GoogleMuseumPin(){
		
	}
	
	public GoogleMuseumPin(String name, float latitude, float longitude){
		this.museumName = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getMuseumName() {
		return museumName;
	}
	public void setMuseumName(String museumName) {
		this.museumName = museumName;
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
}
