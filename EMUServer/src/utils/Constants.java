package utils;

public class Constants {
	// Endpoints
	public static final String dbpediaEndpoint = "http://dbpedia.org/sparql";
	public static final String freebaseRDFResourceEndpoint = "http://rdf.freebase.com/ns/";
	public static final String freebaseMQLEndpoint = "https://www.googleapis.com/freebase/v1/mqlread";

	// Sparql prefixes
	public static final String foafPrefix = "PREFIX foaf:<http://xmlns.com/foaf/0.1/>";
	public static final String geoPrefix = "PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#>";
	public static final String dbppropPrefix = "PREFIX dbpprop:<http://dbpedia.org/property/>";
	public static final String dbpediaOwlPrefix = "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/>";

	// RDF Namespace
	public static final String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	public static final String owl = "http://www.w3.org/2002/07/owl#";
	public static final String dcterms = "http://purl.org/dc/terms/";
	public static final String grs = "http://www.georss.org/georss/";
	public static final String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static final String dbpedia_owl = "http://dbpedia.org/ontology/";
	public static final String dbpprop = "http://dbpedia.org/property/";
	public static final String foaf = "http://xmlns.com/foaf/0.1/";
	public static final String prov = "http://www.w3.org/ns/prov#";

	// XML tags & Dictionary keys
	public static final String dbpAbstractKey = "abstract";
	public static final String dbpDirectorKey = "director";
	public static final String dbpLocationKey = "location";
	public static final String dbpThumbnailKey = "thumbnail";
	public static final String dbpHomepageKey = "homepage";
	public static final String dbpTypeKey = "type";
	public static final String dbpSubjectKey = "subject";
	public static final String dbpAwardOfKey = "award";
	public static final String dbpVisitorsKey = "visitors";
	public static final String dbpBirthPlaceOfKey = "birthPlace";
	public static final String dbpDeathPlaceOfKey = "deathPlace";
	public static final String dbpMuseumKey = "museum";
	public static final String dbpLabelKey = "label";
	public static final String dbpHasPhotoCollectionKey = "hasPhotoCollection";
	public static final String dbpNameKey = "name";
	public static final String dbpGeoLatKey = "lat";
	public static final String dbpGeoLongKey = "long";
	public static final String dbpGeoCreatorKey = "creator";
	public static final String dbpWebsiteKey = "website";
	public static final String dbpEstablishedKey = "established";

	// Museum type mappings
	public static final String[] artList = { "art", "artistic", "arts" };
	public static final String[] scienceList = { "science", "scientific" };
	public static final String[] historyList = { "history", "historic",
			"historical" };
	public static final String[] railwayList = { "railway" };
	public static final String[] farmList = { "farm", "farms", "farming" };
	public static final String[] universityList = { "university" };
	public static final String[] geologyList = { "geology", "geological" };
	public static final String[] photoList = { "photograph", "photography",
			"photos" };
	public static final String[] autoList = { "auto", "autos", "cars",
			"vehicle", "automobile" };
	public static final String[] computer = { "pc", "personal computer",
			"desktop", "computer" };
	public static final String[] warList = { "war", "military", "tank", "tanks" };
}
