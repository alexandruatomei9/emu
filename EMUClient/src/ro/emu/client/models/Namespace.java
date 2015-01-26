package ro.emu.client.models;

public class Namespace {
	private String value;

	public Namespace(){
		
	}
	
	public Namespace(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
