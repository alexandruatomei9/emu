package dbpedia;

import java.util.ArrayList;
import java.util.List;

import utils.GeoLocationHelper;
import models.responses.CategoryMuseum;
import models.responses.GeoMuseum;
import models.responses.Museum;
import models.responses.Work;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class DBPediaClient {
	private static String service = "http://dbpedia.org/sparql";

	public static List<Museum> retrieveHomeMuseums(Integer limit)
			throws Exception {
		List<Museum> list = new ArrayList<Museum>();
		Query query = DBPediaQueryBuilder.homepageMuseumsQuery(limit);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);

		ResultSet result = qe.execSelect();
		while (result.hasNext()) {
			QuerySolution solution = result.next();
			if (solution != null) {
				String resourceUri = solution.getResource("?museum").toString();
				Literal lit = solution.getLiteral("?label");
				String thumbnail = solution.getResource("?thumbnail")
						.toString();
				list.add(new Museum(lit.getValue().toString(), resourceUri,
						thumbnail));
			}
		}

		return list;
	}

	public static List<Museum> retrieveMuseumsWithPrefix(String prefix,
			Integer limit) throws Exception {
		Query query = DBPediaQueryBuilder.searchMuseumsQuery(prefix, limit);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		List<Museum> list = new ArrayList<Museum>();

		ResultSet result = qe.execSelect();
		while (result.hasNext()) {
			QuerySolution solution = result.next();
			if (solution != null) {
				String resourceUri = solution.getResource("?museum").toString();
				Literal lit = solution.getLiteral("?label");
				String thumbnail = solution.getResource("?thumbnail")
						.toString();
				list.add(new Museum(lit.getValue().toString(), resourceUri,
						thumbnail));
			}
		}

		return list;
	}

	public static List<GeoMuseum> retrieveNearbyMuseums(float currLatitude,
			float currLongitude, Integer radius) throws Exception {
		Query query = DBPediaQueryBuilder.museumsWithCoordinatesQuery();
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		List<GeoMuseum> list = new ArrayList<GeoMuseum>();

		ResultSet result = qe.execSelect();
		while (result.hasNext()) {
			QuerySolution solution = result.next();
			Literal litLat = solution.getLiteral("?maxlatitude");
			Literal litLong = solution.getLiteral("?maxlongitude");
			Literal litName = solution.getLiteral("?minLabel");
			if (GeoLocationHelper.locationIsWithinRange(currLatitude,
					currLongitude, litLat.getFloat(), litLong.getFloat(),
					radius)) {
				list.add(new GeoMuseum(litLat.getFloat(), litLong.getFloat(),
						solution.getResource("?Museum").toString(), litName
								.getString()));
			}
		}
		return list;
	}

	public static Model retrieveRDFModelForResource(String dbpediaResourceURL)
			throws Exception {
		FileManager fManager = FileManager.get();
		fManager.addLocatorURL();

		Model model = null;
		model = fManager.loadModel(dbpediaResourceURL);

		return model;

	}

	public static List<Museum> retrieveMuseumsInCountry(String country)
			throws Exception {
		Query query = DBPediaQueryBuilder.museumsInCountryQuery(country);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		List<Museum> list = new ArrayList<Museum>();

		ResultSet result = qe.execSelect();
		while (result.hasNext()) {
			QuerySolution solution = result.next();
			if (solution != null) {
				String resourceUri = solution.getResource("?museum").toString();
				Literal lit = solution.getLiteral("?label");
				String thumbnail = solution.getResource("?thumbnail")
						.toString();
				list.add(new Museum(lit.getValue().toString(), resourceUri,
						thumbnail));
			}
		}

		return list;
	}

	public static String retrieveCountryForMuseum(String museumURI)
			throws Exception {
		Query query = DBPediaQueryBuilder.retrieveCountryForMuseum(museumURI);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		String resourceUri = null;
		String country = null;

		ResultSet result = qe.execSelect();
		if (result.hasNext()) {
			QuerySolution solution = result.next();
			if (solution != null) {
				resourceUri = solution.getResource("?val").toString();
			}
		}
		
		Query query2 = DBPediaQueryBuilder.retriveCountry(resourceUri);
		QueryExecution qe2 = QueryExecutionFactory.sparqlService(service,
				query2);
		ResultSet result2 = qe2.execSelect();
		if(result2.hasNext()) {
			QuerySolution solution2 = result2.next();
			if (solution2 != null) {
				Literal lit = solution2.getLiteral("?label");
				country = lit.getValue().toString();
			}
		}
		return country;
	}

	public static String retriveNumberOfVisitorsMuseum(String museumURI) {
		String numberOfVisitors = null;
		Query query = DBPediaQueryBuilder
				.retriveNumberOfVisitorsMuseum(museumURI);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		ResultSet result = qe.execSelect();
		while (result.hasNext()) {
			QuerySolution solution = result.next();
			Literal lit = solution.getLiteral("?val");
			numberOfVisitors = lit.getValue().toString();
		}

		return numberOfVisitors;

	}

	public static List<Work> retrieveWorksForMuseum(String museumName,
			Integer limit) throws Exception {
		Query query = DBPediaQueryBuilder.worksFromAMuseumQuery(museumName,
				limit);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		List<Work> list = new ArrayList<Work>();
		ResultSet result = qe.execSelect();
		while (result.hasNext()) {
			QuerySolution solution = result.next();
			if (solution != null) {
				String resourceUri = solution.getResource("?work").toString();
				Literal labelLiteral = solution.getLiteral("?label");

				list.add(new Work(labelLiteral.getValue().toString(),
						resourceUri));

			}
		}
		return list;
	}

	public static List<CategoryMuseum> retrieveMuseumsWithCategory(
			String category, Integer limit) throws Exception {
		Query query = DBPediaQueryBuilder.museumsWithAType(category, limit);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		List<CategoryMuseum> list = new ArrayList<CategoryMuseum>();
		ResultSet result = qe.execSelect();
		while (result.hasNext()) {
			QuerySolution solution = result.next();
			if (solution != null) {
				String resourceUri = solution.getResource("?museum").toString();
				Literal labelLiteral = solution.getLiteral("?label");
				String thumbnail = solution.getResource("?thumbnail")
						.toString();
				System.out.println(solution);
				String categoryString = null;
				try {
					Resource categoryResource = solution
							.getResource("?category");
					categoryString = categoryResource.toString();
				} catch (Exception e) {
					Literal categoryLiteral = solution.getLiteral("?category");
					categoryString = categoryLiteral.getValue().toString();
				}
				list.add(new CategoryMuseum(labelLiteral.getValue().toString(),
						resourceUri, thumbnail, categoryString));

			}
		}
		return list;
	}

}
