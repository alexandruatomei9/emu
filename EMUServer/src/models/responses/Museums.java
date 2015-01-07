package models.responses;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

public class Museums {
	private List<Museum> museums;

	public List<Museum> getMuseums() {
		return museums;
	}

	@XmlElementWrapper(name = "museums")
	public void setMuseums(List<Museum> museums) {
		this.museums = museums;
	}

}
