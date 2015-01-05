package ro.emu.client.models;

import java.util.ArrayList;

public class Museum {
	private String name;
	private String url;
	private String image;
	private String description;
	private ArrayList<Detail> details;
	
	public Museum(){
		
	}
	
	public Museum(String name, String image, String url, String description){
		this.name = name;
		this.image = image;
		this.url = url;
		this.setDescription(description);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Detail> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<Detail> details) {
		this.details = details;
	}
}
