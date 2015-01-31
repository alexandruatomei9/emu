package ro.emu.client.models;

import ro.emu.client.dbpedia.DBPediaExtractor;
import ro.emu.client.utils.Constants;
import ro.emu.client.utils.Pair;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * Class that keeps the rdf model for a museum's art object
 * 
 * @author claudiu
 *
 */
public class WorkRDF extends RDFObject {

	public WorkRDF() {
	}

	public WorkRDF(Model rdfModel) {
		this.rdfModel = rdfModel;
	}

	/**
	 * Get the author Resource for an art work
	 * 
	 * @return
	 */
	public Pair<String, Resource> author() {
		Property authorProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpAuthorKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				authorProperty, "en");
		Object authorValue = objectValueFromStatement(stmt);
		if (authorValue != null) {
			return new Pair<String, Resource>(rdfModel.shortForm(stmt
					.getPredicate().getURI()), (Resource) authorValue);
		}
		return null;
	}

	/**
	 * Get the art work's thumbnail URL
	 * 
	 * @return - Pair<String,String>
	 */
	public Pair<String, String> thumbnail() {
		Property thumbnailProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpThumbnailKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				thumbnailProperty, "en");
		Object value = objectValueFromStatement(stmt);
		if (value != null && stmt.getPredicate() != null) {
			return new Pair<String, String>(rdfModel.shortForm(stmt
					.getPredicate().getURI()), value.toString());
		}
		return null;
	}
}
