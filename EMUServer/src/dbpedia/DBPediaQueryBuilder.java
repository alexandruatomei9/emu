package dbpedia;

import java.util.Random;

import utils.Constants;
import utils.MuseumType;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;

public class DBPediaQueryBuilder {

	/**
	 * This method creates a sparql query for selecting a list of museums
	 * established between 1900-1920 ordered ascending by date.
	 * 
	 * @param limit
	 *            - maximum number of museums
	 * @return a query
	 */
	public static Query homepageMuseumsQuery(Integer limit) {
		Random rnd = new Random();
		Integer offset = rnd.nextInt(6);
		Integer minDate = rnd.nextInt(151) + 1800;
		Integer maxDate = rnd.nextInt(1990 - (minDate + 3) + 1) + (minDate + 3);
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
		qs.append("SELECT DISTINCT ?museum ?label ?thumbnail ");
		qs.append("WHERE {");
		qs.append("		?museum ?p ?label;");
		qs.append("a ?type;");
		qs.append("dbpprop:established ?date;");
		qs.append("dbpedia-owl:thumbnail ?thumbnail.");
		qs.append("FILTER ( datatype(?date) = xsd:integer ).");
		qs.append("FILTER (?date > " + minDate + " && ?date < " + maxDate
				+ ").");
		qs.append("FILTER (?p=<http://www.w3.org/2000/01/rdf-schema#label>).");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>, <http://dbpedia.org/class/yago/Museum103800563>)).");
		qs.append("FILTER ( lang(?label) = 'en')");
		qs.append("}");
		qs.append("ORDER BY ASC(?date)");
		qs.append("OFFSET " + offset);
		qs.append(" LIMIT " + limit);
		return qs.asQuery();
	}

	/**
	 * This method creates a query for selecting the museum in which their name
	 * starts with a given string
	 * 
	 * @param prefix
	 * @param number
	 *            ` maximum number of museums
	 * @return a query
	 */
	public static Query searchMuseumsQuery(String prefix, Integer number) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.append("SELECT DISTINCT ?museum ?label ?thumbnail");
		qs.append("  WHERE{ \n");
		qs.append("?museum ?p ?label.");
		qs.append("?museum a ?type.");
		qs.append("?museum dbpedia-owl:thumbnail ?thumbnail.");
		qs.append("FILTER (?p=<http://www.w3.org/2000/01/rdf-schema#label>).");
		qs.append("FILTER regex(?label, \"^" + prefix + "\", \"i\").\r\n");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>,  <http://dbpedia.org/class/yago/Museum103800563>)).");
		qs.append("FILTER ( lang(?label) = 'en') } LIMIT " + number);
		return qs.asQuery();
	}

	/**
	 * This method creates a sparql query for selecting all the museums with
	 * their geolocation coordinates
	 * 
	 * @return
	 */
	public static Query museumsWithCoordinatesQuery() {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		qs.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		qs.append("SELECT ?Museum ?minLabel ?maxlongitude ?maxlatitude WHERE { ");
		qs.append("{");
		qs.append("SELECT ?Museum");
		qs.append("(MIN(?label) as ?minLabel)");
		qs.append("(MAX(?longitude) as ?maxlongitude)");
		qs.append("(MAX(?latitude) as ?maxlatitude)");
		qs.append("WHERE {");
		qs.append("?Museum a ?type ;");
		qs.append("geo:lat ?latitude ;");
		qs.append("rdfs:label ?label ;");
		qs.append("geo:long ?longitude .");
		qs.append("FILTER (langMatches(lang(?label),\"EN\"))");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>,  <http://dbpedia.org/class/yago/Museum103800563>)).");
		qs.append("}");
		qs.append("GROUP BY ?Museum");
		qs.append("}}");

		return qs.asQuery();
	}

	/**
	 * This method creates a sparql query for selecting all the museums in
	 * country(without geolocation)
	 * 
	 * @param country
	 * @return
	 */
	public static Query museumsInCountryQuery(String country) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.append("PREFIX  dbpedia-owl: <http://dbpedia.org/ontology/>\r\nPREFIX  "
				+ "dbpedia: <http://dbpedia.org/resource/>\r\n\r\nSELECT DISTINCT "
				+ " ?museum ?label ?thumbnail\r\nWHERE\r\n  "
				+ "{ ?museum <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type .\r\n "
				+ "   ?museum ?p ?label .\r\n  "
				+ "  ?museum dbpedia-owl:thumbnail ?thumbnail\r\n   "
				+ " FILTER ( ?p = <http://www.w3.org/2000/01/rdf-schema#label> )\r\n  "
				+ "  FILTER ( ?type IN (dbpedia-owl:Museum, <http://schema.org/Museum>,  <http://dbpedia.org/class/yago/Museum103800563>) )\r\n  "
				+ "  ?museum dbpedia-owl:location dbpedia:"
				+ country
				+ "\r\n   "
				+ " FILTER ( lang(?label) = \"en\" )\r\n  "
				+ "}\r\n" + "LIMIT   100");
		return qs.asQuery();
	}

	/**
	 * This method creates a sparql query for retrieving the country of museum
	 * 
	 * @param museumURI
	 * @return
	 */
	public static Query retrieveCountryForMuseum(String museumURI) {
		if(museumURI.contains("<")){
			museumURI = museumURI.replace("<", "");
		}
		if(museumURI.contains(">")){
			museumURI = museumURI.replace(">", "");
		}
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setBaseUri(museumURI);
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		qs.append("SELECT ?val WHERE {<" + museumURI
				+ "> dbpedia-owl:location ?val}LIMIT" + 1);
		return qs.asQuery();

	}

	/**
	 * This method creates a sparql query for finding the name of a country
	 * resource
	 * 
	 * @param uri
	 * @return
	 */
	public static Query retriveCountry(String uri) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setBaseUri(uri);
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.append("SELECT ?label WHERE {<" + uri
				+ "> rdfs:label ?label FILTER ( lang(?label) ='en')} LIMIT" + 1);
		return qs.asQuery();
	}

	public static Query retriveNumberOfVisitorsMuseum(String museumURI) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.append("SELECT ?val WHERE {" + museumURI
				+ " dbpprop:visitors ?val}LIMIT" + 1);
		return qs.asQuery();
	}

	public static Query retrieveMuseumForWork(String workUri) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setBaseUri(workUri);
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		qs.append("SELECT ?val WHERE {<" + workUri
				+ "> dbpedia-owl:museum ?val}LIMIT" + 1);
		return qs.asQuery();

	}

	/**
	 * This method creates a sparql query for retrieving the name of a museum
	 * resource
	 * 
	 * @param uri
	 * @return
	 */
	public static Query retriveMuseum(String uri) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setBaseUri(uri);
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.append("SELECT ?label WHERE {<" + uri
				+ "> rdfs:label ?label FILTER ( lang(?label) ='en')} LIMIT" + 1);
		return qs.asQuery();
	}

	/**
	 * This method creates a query for selecting the works(pictures, sculptures,
	 * etc) from museum. The <b>dbPediaMuseumName</b> should be the name which
	 * identifies the resource on the dbpedia's webpages. EX:
	 * http://dbpedia.org/page/<b>Indian_River_Citrus_Museum</b>
	 * 
	 * @param dbPediaMuseumName
	 * @param limit
	 *            - maximum number of museums
	 * @return
	 */
	public static Query worksFromAMuseumQuery(String dbPediaMuseumName,
			Integer limit) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		qs.setNsPrefix("dbpedia", "http://dbpedia.org/resource/");
		qs.append("SELECT DISTINCT ?work ?label ");
		qs.append("WHERE {");
		qs.append("?work  rdf:type dbpedia-owl:Work;");
		qs.append("dbpedia-owl:museum dbpedia:" + dbPediaMuseumName + ";");
		qs.append("rdfs:label ?label.");
		qs.append(" FILTER(lang(?label) = 'en').");
		qs.append("}");
		qs.append("LIMIT " + limit);
		return qs.asQuery();
	}

	/**
	 * This method creates a query for selecting the museums with a specified
	 * type
	 * 
	 * @param type
	 * @param limit
	 * @return
	 */
	public static Query museumsWithAType(MuseumType museumType, Integer limit) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.append("SELECT DISTINCT ?museum ?label ?thumbnail ?category ");
		qs.append("WHERE {");
		qs.append("?museum ?p ?label;");
		qs.append(" a ?type;");
		qs.append(" dbpedia-owl:thumbnail ?thumbnail;");
		qs.append(" dbpprop:type ?category;");
		qs.append(" dbpedia-owl:abstract ?abstract.");
		qs.append(filtersForMuseumType(museumType));
		qs.append(" FILTER (?p=<http://www.w3.org/2000/01/rdf-schema#label>).");
		qs.append(" FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, ");
		qs.append(" <http://schema.org/Museum>,  <http://dbpedia.org/class/yago/Museum103800563>)).");
		qs.append(" FILTER ( lang(?label) = 'en')");
		qs.append("}");
		qs.append(" LIMIT " + limit);
		return qs.asQuery();
	}

	public static Query geoMuseumsWithType(MuseumType museumType, Integer limit) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		qs.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		qs.setNsPrefix("dbpedia", "http://dbpedia.org/resource");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.append("SELECT ?Museum ?minLabel ?maxlongitude ?maxlatitude WHERE { ");
		qs.append("{");
		qs.append("SELECT ?Museum");
		qs.append("(MIN(?label) as ?minLabel)");
		qs.append("(MIN(?name) as ?maxname)");
		qs.append("(MAX(?longitude) as ?maxlongitude)");
		qs.append("(MAX(?latitude) as ?maxlatitude)");
		qs.append("WHERE {");
		qs.append("?Museum a ?type ;");
		qs.append("geo:lat ?latitude ;");
		qs.append("rdfs:label ?label ;");
		qs.append("geo:long ?longitude ;");
		qs.append(" dbpprop:type ?category;");
		qs.append(" dbpedia-owl:abstract ?abstract.");
		qs.append(filtersForMuseumType(museumType));
		qs.append("FILTER (langMatches(lang(?label),\"EN\"))");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>, <http://dbpedia.org/class/yago/Museum103800563>)).");
		qs.append("}");
		qs.append("GROUP BY ?Museum");
		qs.append("}}");
		return qs.asQuery();
	}

	public static Query geoMuseumsInCountry(String country, Integer limit) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		qs.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		qs.setNsPrefix("dbpedia", "http://dbpedia.org/resource");
		qs.append("SELECT ?Museum ?minLabel ?maxlongitude ?maxlatitude WHERE { ");
		qs.append("{");
		qs.append("SELECT ?Museum");
		qs.append("(MIN(?label) as ?minLabel)");
		qs.append("(MIN(?name) as ?maxname)");
		qs.append("(MAX(?longitude) as ?maxlongitude)");
		qs.append("(MAX(?latitude) as ?maxlatitude)");
		qs.append("WHERE {");
		qs.append("?Museum a ?type ;");
		qs.append("geo:lat ?latitude ;");
		qs.append("rdfs:label ?label ;");
		qs.append("geo:long ?longitude ;");
		qs.append("dbpedia-owl:location ?location.");
		qs.append(filterForCountry(country));
		qs.append("FILTER (langMatches(lang(?label),\"EN\"))");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>,<http://dbpedia.org/class/yago/Museum103800563>)).");
		qs.append("}");
		qs.append("GROUP BY ?Museum");
		qs.append("}}");
		return qs.asQuery();
	}

	public static Query geoMuseumsInCountryWithType(String country,
			MuseumType type, Integer limit) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		qs.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		qs.setNsPrefix("dbpedia", "http://dbpedia.org/resource");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.append("SELECT ?Museum ?minLabel ?maxlongitude ?maxlatitude WHERE { ");
		qs.append("{");
		qs.append("SELECT ?Museum");
		qs.append("(MIN(?label) as ?minLabel)");
		qs.append("(MAX(?longitude) as ?maxlongitude)");
		qs.append("(MAX(?latitude) as ?maxlatitude)");
		qs.append("WHERE {");
		qs.append("?Museum a ?type ;");
		qs.append("geo:lat ?latitude ;");
		qs.append("rdfs:label ?label ;");
		qs.append("geo:long ?longitude ;");
		qs.append(" dbpprop:type ?category;");
		qs.append(" dbpedia-owl:abstract ?abstract;");
		qs.append("dbpedia-owl:location ?location.");
		qs.append(filterForCountry(country));
		qs.append(filtersForMuseumType(type));
		qs.append("FILTER (langMatches(lang(?label),\"EN\"))");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>,<http://dbpedia.org/class/yago/Museum103800563>)).");
		qs.append("}");
		qs.append("GROUP BY ?Museum");
		qs.append("}}");
		return qs.asQuery();
	}

	private static String filterForCountry(String country) {

		if (country.equals("united_states")) {
			return "FILTER (?location=dbpedia:" + country + " || "
					+ "?location=dbpedia:" + "US"
					+ " || ?location=dbpedia:USA).";
		}
		
		if (country.equals("united_kingdom")) {
			return "FILTER (?location=dbpedia:" + country + " || "
					+ "?location=dbpedia:" + "England"
					+ " || ?location=dbpedia:Scotland"
					+ " || ?location=dbpedia:Wales).";
		}
		return "FILTER (?location=dbpedia:" + country + " || "
				+ "regex(?location, \"" + country + "\", \"i\")).";
	}

	private static String filtersForMuseumType(MuseumType type) {
		String[] list = null;
		switch (type) {
		case ArtMuseum:
			list = Constants.artList;
			break;
		case AutoMuseum:
			list = Constants.autoList;
			break;
		case ComputerMuseum:
			list = Constants.computer;
			break;
		case FarmMuseum:
			list = Constants.farmList;
			break;
		case GeologyMuseum:
			list = Constants.geologyList;
			break;
		case HistoryMuseum:
			list = Constants.historyList;
			break;
		case PhotographyMuseum:
			list = Constants.photoList;
			break;
		case RailwayMuseum:
			list = Constants.railwayList;
			break;
		case ScienceMuseum:
			list = Constants.scienceList;
			break;
		case UniversityMuseum:
			list = Constants.universityList;
			break;
		case WarMuseum:
			list = Constants.warList;
			break;
		default:
			break;
		}
		if (list == null) {
			return "";
		}
		String filters = "FILTER (";
		for (String item : list) {
			filters += " regex(str(?category),\"" + item
					+ "\",\"i\") || regex(str(?type),\"" + item
					+ "\",\"i\") || regex(str(?abstract),\"" + item
					+ "\",\"i\") ||";
		}
		filters = filters.substring(0, filters.length() - 2);
		filters += ").";
		return filters;
	}
}
