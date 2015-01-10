package services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.responses.Museum;

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
	@Produces(MediaType.APPLICATION_JSON)
	public List<Museum> getMuseums() {
		List<Museum> list = DBPediaClient.retrieveHomeMuseums(5);
		// Museums m = new Museums();
		// m.setMuseums(list);
		// return m;
		return list;
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getMuseumsBrowser() {
		// List<Museum> list = DBPediaClient.retrieveMuseumsWithPrefix(
		// "Swe", 10);
		// return list;
		// DBPediaClient.retrieveNearbyMuseums(51.519459f, -0.126931f, 200);
		// DBPediaClient
		// .retrieveRDFModelForResource("http://dbpedia.org/data/Ireland.rdf");
		List<Museum> museums = DBPediaClient
				.retrieveMuseumsInCountry("Romania");
		System.out.println(museums);
		return "Ceva";
	}

}
