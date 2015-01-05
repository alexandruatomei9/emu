package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import freebase.MQLClient;

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

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		// RdfSample.testRDF();
		String queryWithLimit = "[{\r\n  \"type\": \"/architecture/museum\",\r\n  \"name\": null,\r\n  \"mid\": null,\r\n  \"limit\": 5,\r\n  \"/common/topic/image\": [{\r\n    \"name\": null,\r\n    \"mid\": null\r\n  }]\r\n}]";
		String query = "[{\r\n  \"type\": \"/architecture/museum\",\r\n  \"name\": null,\r\n  \"mid\": null,\r\n  \"/common/topic/image\": [{\r\n    \"name\": null,\r\n    \"mid\": null\r\n  }]\r\n}]";
		MQLClient.retrieveResponseForQuery(queryWithLimit);

		return "<html> " + "<title>" + "Hello Jersey" + "</title>"
				+ "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
		// return testDBpedia();
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
