package ro.emu.client.rdfmodels;

import java.util.Map;

import ro.emu.client.dbpedia.DBPediaExtractor;
import ro.emu.client.utils.Constants;
import ro.emu.client.utils.Pair;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFObject {
	protected static final String lang = "en";
	protected Model rdfModel;

	protected RDFObject() {

	}

	protected RDFObject(Model rdfModel) {
		this.rdfModel = rdfModel;
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

	protected Object objectValueFromStatement(Statement stmt) {
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

	protected Object subjectValueFromStatement(Statement stmt) {
		if (stmt != null) {
			RDFNode node = stmt.getSubject();
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

	protected String safeShortForm(Statement stmt) {
		if (stmt == null || stmt.getPredicate() == null
				|| stmt.getPredicate().getURI() == null) {
			return null;
		}
		return rdfModel.shortForm(stmt.getPredicate().getURI());
	}

	public String getResourceName() {
		Property abstractProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpAbstractKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				abstractProperty, lang);
		if (stmt != null) {
			return stmt.getSubject().getURI();
		}
		Property foafName = rdfModel.createProperty(Constants.foaf,
				Constants.dbpNameKey);
		stmt = DBPediaExtractor.statementWithProperties(rdfModel, foafName,
				lang);
		if (stmt != null) {
			return stmt.getSubject().getURI();
		}
		return null;
	}

	/**
	 * Get the abstract description about a entity
	 * 
	 * @return Pair<String,String>
	 */
	public Pair<String, String> getAbstract() {
		Property abstractProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpAbstractKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				abstractProperty, lang);
		Object value = objectValueFromStatement(stmt);
		if (value != null) {
			String prefixNS = safeShortForm(stmt);
			return new Pair<String, String>(prefixNS, (String) value);
		}
		return null;
	}

	/**
	 * Get the entity's name
	 * 
	 * @return Pair<String, String>
	 */
	public Pair<String, String> getName() {
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
			nameValue = (String) objectValueFromStatement(stmt);
			if (nameValue != null) {
				return new Pair<String, String>(safeShortForm(stmt),
						(String) objectValueFromStatement(stmt));
			}
			return null;
		}
		return new Pair<String, String>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), nameValue);
	}

	/**
	 * Get the entity wikipedia URL
	 * 
	 * @return
	 */
	public Pair<String, String> getWikiPageURL() {
		try {
			Property primaryTopicOfProperty = rdfModel.createProperty(
					Constants.foaf, Constants.dbpPrimaryTopicKey);
			Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
					primaryTopicOfProperty, "en");
			Object primaryValue = objectValueFromStatement(stmt);
			if (primaryValue != null) {
				return new Pair<String, String>(safeShortForm(stmt),
						primaryValue.toString());
			}
			return new Pair<String, String>(safeShortForm(stmt), null);
		} catch (Exception ex) {
			return null;
		}
	}
}
