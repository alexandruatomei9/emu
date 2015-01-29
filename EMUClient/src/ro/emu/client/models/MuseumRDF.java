package ro.emu.client.models;

import java.util.ArrayList;
import java.util.List;
import ro.emu.client.dbpedia.DBPediaExtractor;
import ro.emu.client.utils.Constants;
import ro.emu.client.utils.Pair;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
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
public class MuseumRDF extends RDFObject {

	public MuseumRDF() {
	}

	public MuseumRDF(Model model) {
		super(model);
	}

	/**
	 * Get the latitude coordinate
	 * 
	 * @return Pair<String, Float>
	 */
	public Pair<String, Float> getLatitude() {
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
	public Pair<String, Float> getLongitude() {
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
	public Pair<String, Resource> getDirector() {
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
	public Pair<String, Integer> getEstablishedYear() {
		Property establishedProp = rdfModel.createProperty(Constants.dbpprop,
				Constants.dbpEstablishedKey);
		Statement stmt = DBPediaExtractor.statementWithProperties(rdfModel,
				establishedProp, "en");
		if (stmt != null) {
			return new Pair<String, Integer>(rdfModel.shortForm(stmt
					.getPredicate().getURI()),
					(Integer) objectValueFromStatement(stmt));
		}
		return null;
	}

	/**
	 * Get the museum website
	 * 
	 * @return Pair<String, String>
	 */
	public Pair<String, String> getWebsite() {
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
	public Pair<String, String> getThumbnail() {
		Property thumbnailProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpThumbnailKey);
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
	public List<Pair<String, Resource>> getWorks() {
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
	public List<Pair<String, Resource>> getSubjectsIncludingMuseum() {
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
	public List<Pair<String, Resource>> getLocations() {
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

	/**
	 * Get the list of persons which died in the museum
	 * 
	 * @return
	 */
	public List<Pair<String, Resource>> getDeadPeople() {
		ArrayList<Pair<String, Resource>> deadPeople = new ArrayList<Pair<String, Resource>>();
		Property deathPlaceProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpDeathPlaceOfKey);
		List<Statement> list = DBPediaExtractor.statementsWithProperties(
				rdfModel, deathPlaceProperty, "en");
		if (list != null) {
			for (Statement stmt : list) {
				Object deathPersonValue = stmt.getSubject();
				if (deathPersonValue.getClass() == ResourceImpl.class) {
					deadPeople.add(new Pair<String, Resource>(rdfModel
							.shortForm(stmt.getPredicate().getURI()),
							(Resource) deathPersonValue));
				}
			}
		}
		return deadPeople;
	}

	/**
	 * Get the list of persons which were born in the museum
	 * 
	 * @return
	 */
	public List<Pair<String, Resource>> getBornPeople() {
		ArrayList<Pair<String, Resource>> bornPeople = new ArrayList<Pair<String, Resource>>();

		Property birthPlaceProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpBirthPlaceOfKey);
		List<Statement> list = DBPediaExtractor.statementsWithProperties(
				rdfModel, birthPlaceProperty, "en");
		if (list != null) {
			for (Statement stmt : list) {
				Object bornPersonValue = stmt.getSubject();
				if (bornPersonValue.getClass() == ResourceImpl.class) {
					bornPeople.add(new Pair<String, Resource>(rdfModel
							.shortForm(stmt.getPredicate().getURI()),
							(Resource) bornPersonValue));
				}
			}
		}
		return bornPeople;
	}

	/**
	 * Get the number of visitors
	 * 
	 * @return
	 */
	public Pair<String, Integer> getNumberOfVisitors() {
		ArrayList<Pair<String, Integer>> numbers = new ArrayList<Pair<String, Integer>>();
		Property nrOfVisitorsProperty = rdfModel.createProperty(
				Constants.dbpedia_owl, Constants.dbpNumberOfVisitorsKey);
		List<Statement> stmts = DBPediaExtractor.statementsWithProperties(
				rdfModel, nrOfVisitorsProperty, lang);
		for (Statement stmt : stmts) {
			Object nrOfVisitorsObject = objectValueFromStatement(stmt);
			if (nrOfVisitorsObject.getClass() == Integer.class) {
				numbers.add(new Pair<String, Integer>(rdfModel.shortForm(stmt
						.getPredicate().getURI()), (Integer) nrOfVisitorsObject));
			}
		}
		nrOfVisitorsProperty = rdfModel.createProperty(Constants.dbpprop,
				Constants.dbpVisitorsKey);
		stmts = DBPediaExtractor.statementsWithProperties(rdfModel,
				nrOfVisitorsProperty, lang);
		for (Statement stmt : stmts) {
			Object nrOfVisitorsObject = objectValueFromStatement(stmt);
			if (nrOfVisitorsObject.getClass() == Integer.class) {
				numbers.add(new Pair<String, Integer>(rdfModel.shortForm(stmt
						.getPredicate().getURI()), (Integer) nrOfVisitorsObject));
			}
		}
		if (numbers.size() == 0)
			return null;
		Pair<String, Integer> maxNumberPair = numbers.get(0);
		for (Pair<String, Integer> pair : numbers) {
			if (maxNumberPair.getSecond() < pair.getSecond())
				maxNumberPair = pair;
		}
		return maxNumberPair;
	}
}
