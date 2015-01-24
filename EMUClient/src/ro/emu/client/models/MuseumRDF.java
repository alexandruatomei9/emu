package ro.emu.client.models;

import java.util.Map;

import ro.emu.client.dbpedia.DBPediaExtractor;
import ro.emu.client.utils.Constants;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * Class that keeps a map with the used RDF namespaces and the statements about
 * a museum
 * 
 * @author claudiu
 *
 */
public class MuseumRDF {
	private static final String lang = "en";
	private Model rdfModel;

	public MuseumRDF() {

	}

	public MuseumRDF(Model model) {
		this.rdfModel = model;
	}

	public Model getRdfModel() {
		return rdfModel;
	}

	public void setRdfModel(Model rdfModel) {
		this.rdfModel = rdfModel;
	}

	public Map<String, String> getNsPrefixes() {
		return rdfModel.getNsPrefixMap();
	}

	public String abstractValue() {
		Property abstractProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpAbstractKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				abstractProperty, lang);
		return objectValueFromStatement(stmt);
	}

	public String name() {
		Property foafName = rdfModel.createProperty(Constants.foaf,
				Constants.dbpNameKey);
		Property rdfsName = rdfModel.createProperty(Constants.rdfs,
				Constants.dbpLabelKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				rdfsName, lang);
		String nameValue = objectValueFromStatement(stmt);
		if (nameValue == null) {
			stmt = DBPediaExtractor.statementWithProperties(rdfModel, foafName,
					lang);
			return objectValueFromStatement(stmt);
		}
		return nameValue;
	}

	private String objectValueFromStatement(Statement stmt) {
		if (stmt != null) {
			RDFNode node = stmt.getObject();
			if (node.isResource()) {
				Resource res = (Resource) node;
				return res.toString();
			} else if (node.isLiteral()) {
				Literal lit = (Literal) node;
				if (lit.getLanguage().equalsIgnoreCase("en")) {
					return lit.getString();
				}
			}
		}
		return null;
	}
}
