package services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import utils.Constants;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import models.responses.CategoryMuseum;
import models.responses.Work;
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
	public List<Work> getMuseums() {
		List<Work> list = null;
		try {
			// list = DBPediaClient.retrieveWorksForMuseum("The_Louvre", 100);
			Model model = DBPediaClient
					.retrieveRDFModelForResource("http://dbpedia.org/data/The_Louvre.rdf");
			TestModel(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Museums m = new Museums();
		// m.setMuseums(list);
		// return m;
		return list;
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getMuseumsBrowser() throws Exception {
		List<CategoryMuseum> categoryMuseum = DBPediaClient
				.retrieveMuseumsWithCategory("Art", 100);
		if (categoryMuseum != null) {
			System.out.println(categoryMuseum.size());
		}
		return "Ceva";
	}

	public void TestModel(Model model) {
		Property geoProperty = model.createProperty(Constants.dbpprop,
				"latitude");
		StmtIterator iter = model.listStatements(new SimpleSelector(null,
				geoProperty, (RDFNode) null));
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			System.out.print(stmt.getSubject().toString() + " ");
			System.out.print(stmt.getPredicate().toString() + " ");
			System.out.println(stmt.getObject().toString());

		}
	}

}
