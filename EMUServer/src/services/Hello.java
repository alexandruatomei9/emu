package services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.responses.Museum;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import dbpedia.DBPediaClient;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey";
	}

	// This method is called if XML/JSON is requested
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Museum getMuseums() {
		List<Museum> list = DBPediaClient.retrieveMuseumsWithPrefix(
				"Swe", 10);
//		Museums m = new Museums();
//		m.setMuseums(list);
//		return m;
		return list.get(0);
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getMuseumsBrowser() {
//		List<Museum> list = DBPediaClient.retrieveMuseumsWithPrefix(
//				"Swe", 10);
//		return list;
		DBPediaClient.retrieveNearbyMuseums(51.519459f, -0.126931f, 200);
		return "Ceva";
	}

	public String testDBpedia() {
		String service = "http://dbpedia.org/sparql";
		String query = "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/>"
				+ "PREFIX dbpprop:<http://dbpedia.org/property/>"
				+ "PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#>"
				+ "PREFIX foaf:<http://xmlns.com/foaf/0.1/>"
				+ "SELECT * WHERE { \r\n "
				+ " # Select museums and a single latitude, longitude, and name for them.\r\n "
				+ " {\r\n    SELECT ?Museum \r\n       "
				+ "    (MIN(?name) as ?maxname)\r\n"
				+ "           (MAX(?longitude) as ?maxlongitude)\r\n "
				+ "          (MAX(?latitude) as ?maxlatitude)\r\n"
				+ "    WHERE {\r\n"
				+ "      ?Museum a dbpedia-owl:Museum ;\r\n"
				+ "              dbpprop:name ?name ;\r\n"
				+ "              geo:lat ?latitude ;\r\n"
				+ "              geo:long ?longitude .\r\n"
				+ "      FILTER (langMatches(lang(?name),\"EN\"))\r\n"
				+ "    }\r\n "
				+ "   GROUP BY ?Museum\r\n"
				+ "  }\r\n"
				+ "  # Get the rest of the properties of the museum.\r\n"
				+ "  ?Museum dbpprop:name ?name ;\r\n"
				+ "          dbpedia-owl:abstract ?abstract ; \r\n"
				+ "          dbpedia-owl:thumbnail ?thumbnail ; \r\n"
				+ "          dbpprop:hasPhotoCollection ?photoCollection ;\r\n"
				+ "          dbpprop:website ?website ; \r\n"
				+ "          foaf:homepage ?homepage ; \r\n"
				+ "          foaf:isPrimaryTopicOf ?wikilink .\r\n"
				+ "  FILTER(langMatches(lang(?abstract),\"EN\")) \r\n}\r\nLIMIT 20";
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try {
			ResultSet result = qe.execSelect();
			while (result.hasNext()) {
				QuerySolution solution = result.next();
				System.out.println(solution.getResource("?Museum").toString());
			}
			return "succes";
		} catch (Exception e) {
			// TODO: handle exception
			return service + " has problems.";
		}

	}

}
