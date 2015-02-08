package utils;

public enum MuseumType {
	ArtMuseum("Art"), ScienceMuseum("Science"), HistoryMuseum("History"), RailwayMuseum(
			"Railway"), FarmMuseum("Farm"), UniversityMuseum("University"), GeologyMuseum(
			"Geology"), PhotographyMuseum("Photography"), AutoMuseum("Auto"), ComputerMuseum(
			"Computer"), WarMuseum("War");
	private String text;

	private MuseumType(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static MuseumType fromString(String text) {
		if (text != null) {
			for (MuseumType m : MuseumType.values()) {
				if (text.equalsIgnoreCase(m.text)) {
					return m;
				}
			}
		}
		return null;
	}
}
