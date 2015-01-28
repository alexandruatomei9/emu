package ro.emu.client.models;

import ro.emu.client.dbpedia.DBPediaExtractor;
import ro.emu.client.utils.Constants;
import ro.emu.client.utils.Pair;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;

public class PersonRDF extends RDFObject {
	public PersonRDF() {

	}

	public PersonRDF(Model model) {
		super(model);
	}

	/**
	 * Get the person thumbnail's URL
	 * 
	 * @return
	 */
	public Pair<String, String> thumbnail() {
		Property thumbnailProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpThumbnailKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				thumbnailProperty, "en");
		Object value = objectValueFromStatement(stmt);
		return new Pair<String, String>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), value.toString());
	}

}
