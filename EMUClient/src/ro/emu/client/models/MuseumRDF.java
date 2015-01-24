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

	/**
	 * Get the abstract description about a museum
	 * 
	 * @return String
	 */
	public String abstractValue() {
		Property abstractProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpAbstractKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				abstractProperty, lang);
		return (String) objectValueFromStatement(stmt);
	}

	/**
	 * Get the museum's name
	 * 
	 * @return String
	 */
	public String name() {
		Property foafName = rdfModel.createProperty(Constants.foaf,
				Constants.dbpNameKey);
		Property rdfsName = rdfModel.createProperty(Constants.rdfs,
				Constants.dbpLabelKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				rdfsName, lang);
		String nameValue = (String) objectValueFromStatement(stmt);
		if (nameValue == null) {
			stmt = DBPediaExtractor.statementWithProperties(rdfModel, foafName,
					lang);
			return (String) objectValueFromStatement(stmt);
		}
		return nameValue;
	}

	/**
	 * Get the latitude coordinate
	 * 
	 * @return Float
	 */
	public Float latitude() {
		Property geoProperty = rdfModel.createProperty(Constants.geo,
				Constants.dbpGeoLatKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				geoProperty, lang);
		Object value = objectValueFromStatement(stmt);
		if (value == null) {
			geoProperty = rdfModel.createProperty(Constants.dbpprop,
					Constants.dbpPropLatitudeKey);
			stmt = DBPediaExtractor.statementWithProperties(rdfModel,
					geoProperty, lang);
			return (Float) objectValueFromStatement(stmt);
		}
		return (Float) value;
	}

	/**
	 * Get the longitude coordinate
	 * 
	 * @return
	 */
	public Float longitude() {
		Property geoProperty = rdfModel.createProperty(Constants.geo,
				Constants.dbpGeoLongKey);

		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				geoProperty, lang);
		Object value = objectValueFromStatement(stmt);
		if (value == null) {
			geoProperty = rdfModel.createProperty(Constants.dbpprop,
					Constants.dbpPropLongitudeLey);
			stmt = DBPediaExtractor.statementWithProperties(rdfModel,
					geoProperty, lang);
			return (Float) objectValueFromStatement(stmt);
		}
		return (Float) value;
	}

	public Resource director() {
		Property directorProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpDirectorKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				directorProperty, lang);
		Object value = objectValueFromStatement(stmt);
		if (value == null) {
			directorProperty = rdfModel.createProperty(Constants.dbpprop,
					Constants.dbpDirectorKey);
			stmt = DBPediaExtractor.statementWithProperties(rdfModel,
					directorProperty, lang);
			return (Resource) objectValueFromStatement(stmt);
		}
		return (Resource) value;
	}

	private Object objectValueFromStatement(Statement stmt) {
		if (stmt != null) {
			RDFNode node = stmt.getObject();
			if (node.isResource()) {
				Resource res = (Resource) node;
				return res;
			} else if (node.isLiteral()) {
				Literal lit = (Literal) node;
				if (lit.getLanguage().length() == 0)
					return lit.getValue();
				if (lit.getLanguage().equalsIgnoreCase("en")) {
					return lit.getValue();
				}
			}
		}
		return null;
	}
}
