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
	
	//XML tags & Dictionary keys
	public static final String dbpMuseumKey = "museum";
	public static final String dbpThumbnailKey = "thumbnail";
	public static final String dbpLabelKey = "label";
	public static final String dbpHasPhotoCollectionKey = "hasPhotoCollection";
	public static final String dbpNameKey = "name";
	public static final String dbpAbstractKey = "abstract";
	public static final String dbpGeoLatKey = "lat";
	public static final String dbpGeoLongKey = "long";
	public static final String dbpGeoCreatorKey = "creator";
}
