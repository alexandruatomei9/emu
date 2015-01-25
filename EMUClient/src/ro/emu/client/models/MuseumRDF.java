package ro.emu.client.models;

import java.util.ArrayList;
import java.util.List;
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
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

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
	 * @return Pair<String,String>
	 */
	public Pair<String, String> abstractValue() {
		Property abstractProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpAbstractKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				abstractProperty, lang);
		System.out.println(rdfModel.shortForm(stmt.getPredicate().getURI()));
		Object value = objectValueFromStatement(stmt);
		if (value != null) {
			String prefixNS = rdfModel.shortForm(stmt.getPredicate().getURI());
			return new Pair<String, String>(prefixNS, (String) value);
		}
		return null;
	}

	/**
	 * Get the museum's name
	 * 
	 * @return Pair<String, String>
	 */
	public Pair<String, String> name() {
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
			return new Pair<String, String>(rdfModel.shortForm(stmt
					.getPredicate().getURI()),
					(String) objectValueFromStatement(stmt));
		}
		return new Pair<String, String>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), nameValue);
	}

	/**
	 * Get the latitude coordinate
	 * 
	 * @return Pair<String, Float>
	 */
	public Pair<String, Float> latitude() {
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
			return new Pair<String, Float>(rdfModel.shortForm(stmt
					.getPredicate().getURI()),
					(Float) objectValueFromStatement(stmt));
		}
		return new Pair<String, Float>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), (Float) value);
	}

	/**
	 * Get the longitude coordinate
	 * 
	 * @return Pair<String, Float>
	 */
	public Pair<String, Float> longitude() {
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
			return new Pair<String, Float>(rdfModel.shortForm(stmt
					.getPredicate().getURI()),
					(Float) objectValueFromStatement(stmt));
		}
		return new Pair<String, Float>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), (Float) value);
	}

	/**
	 * Get the museum's director
	 * 
	 * @return Pair<String, Resource>
	 */
	public Pair<String, Resource> director() {
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
			return new Pair<String, Resource>(rdfModel.shortForm(stmt
					.getPredicate().getURI()),
					(Resource) objectValueFromStatement(stmt));

		}
		return new Pair<String, Resource>(rdfModel.shortForm(stmt
				.getPredicate().getURI()), (Resource) value);
	}

	/**
	 * Get the established year
	 * 
	 * @return - Integer
	 */
	public Pair<String, Integer> establishedYear() {
		Property establishedProp = rdfModel.createProperty(Constants.dbpprop,
				Constants.dbpEstablishedKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				establishedProp, "en");
		return new Pair<String, Integer>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), (Integer) objectValueFromStatement(stmt));
	}

	/**
	 * Get the museum website
	 * 
	 * @return Pair<String, String>
	 */
	public Pair<String, String> website() {
		Property websiteProperty = rdfModel.createProperty(Constants.dbpprop,
				Constants.dbpWebsiteKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				websiteProperty, "en");
		Object value = objectValueFromStatement(stmt);
		if (value == null) {
			websiteProperty = rdfModel.createProperty(Constants.foaf,
					Constants.dbpHomepageKey);
			stmt = DBPediaExtractor.statementWithProperties(rdfModel,
					websiteProperty);
			return new Pair<String, String>(rdfModel.shortForm(stmt
					.getPredicate().getURI()), objectValueFromStatement(stmt)
					.toString());
		}
		return new Pair<String, String>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), value.toString());
	}

	/**
	 * Get the museum's thumbnail URL
	 * 
	 * @return - Pair<String,String>
	 */
	public Pair<String, String> thumbnail() {
		Property thumbnailProperty = rdfModel.createProperty(Constants.dbpedia_owl,
				Constants.dbpThumbnailKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				thumbnailProperty, "en");
		Object value = objectValueFromStatement(stmt);
		return new Pair<String, String>(rdfModel.shortForm(stmt.getPredicate()
				.getURI()), value.toString());
	}

	/**
	 * Get the art objects from museum
	 * 
	 * @return Pair<String, Resource>
	 */
	public List<Pair<String, Resource>> works() {
		ArrayList<Pair<String, Resource>> works = new ArrayList<Pair<String, Resource>>();
		Property worksProperty = rdfModel.createProperty(Constants.dbpedia_owl,
				Constants.dbpMuseumKey);
		List<Statement> listStmt = DBPediaExtractor.statementsWithProperties(
				rdfModel, worksProperty, "en");
		for (Statement stmt : listStmt) {
			Object workValue = subjectValueFromStatement(stmt);
			if (workValue.getClass() == ResourceImpl.class) {
				works.add(new Pair<String, Resource>(rdfModel.shortForm(stmt
						.getPredicate().getURI()), (Resource) workValue));
			}
		}
		return works;
	}

	/**
	 * Get the subjects including this museum
	 * 
	 * @return List<Pair<String, Resource>>
	 */
	public List<Pair<String, Resource>> subjectsIncludingMuseum() {
		ArrayList<Pair<String, Resource>> subjects = new ArrayList<Pair<String, Resource>>();
		Property subjectProperty = rdfModel.createProperty(Constants.dcterms,
				Constants.dbpSubjectKey);
		List<Statement> list = DBPediaExtractor.statementsWithProperties(
				rdfModel, subjectProperty, "en");
		if (list != null)
			for (Statement stmt : list) {
				subjects.add(new Pair<String, Resource>(rdfModel.shortForm(stmt
						.getPredicate().getURI()),
						(Resource) objectValueFromStatement(stmt)));
			}
		return subjects;
	}

	/**
	 * Get the museum's locations(city,country,street, etc.)
	 * 
	 * @return
	 */
	public List<Pair<String, Resource>> locations() {
		ArrayList<Pair<String, Resource>> locations = new ArrayList<Pair<String, Resource>>();
		Property locationProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpLocationKey);
		List<Statement> list = DBPediaExtractor.statementsWithProperties(
				rdfModel, locationProperty, "en");
		if (list != null) {
			for (Statement stmt : list) {
				if (stmt.getObject().isResource()) {
					locations.add(new Pair<String, Resource>(rdfModel
							.shortForm(stmt.getPredicate().getURI()),
							(Resource) stmt.getObject()));
				}
			}
		}
		return locations;
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

	private Object subjectValueFromStatement(Statement stmt) {
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
}
