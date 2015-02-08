package ro.emu.client.rdfmodels;

import ro.emu.client.dbpedia.DBPediaExtractor;
import ro.emu.client.utils.Constants;
import ro.emu.client.utils.Pair;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
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
		if (value != null) {
			return new Pair<String, String>(safeShortForm(stmt), value.toString());
		}
		return null;
	}

	/**
	 * Get the person's birth date
	 * 
	 * @return
	 */
	public Pair<String, String> getBirthDate() {
		Property birthDateProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpBirthDateKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				birthDateProperty, lang);
		Object value = objectValueFromStatement(stmt);
		if (value != null && value.getClass() == XSDDateTime.class) {
			return new Pair<String, String>(safeShortForm(stmt), value.toString());
		}
		return null;
	}

	/**
	 * Get the person's death date
	 * 
	 * @return
	 */
	public Pair<String, String> getDeathDate() {
		Property deathDateProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpDeathDateKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				deathDateProperty, lang);
		Object value = objectValueFromStatement(stmt);
		if (value != null && value.getClass() == XSDDateTime.class) {
			return new Pair<String, String>(safeShortForm(stmt), value.toString());
		}
		return null;
	}
}
