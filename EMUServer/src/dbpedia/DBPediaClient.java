package dbpedia;

import java.util.ArrayList;
import java.util.List;

import utils.GeoLocationHelper;
import models.responses.GeoMuseum;
import models.responses.Museum;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class DBPediaClient {
	private static String service = "http://dbpedia.org/sparql";

	public static List<Museum> retrieveHomeMuseums(Integer limit) {
		List<Museum> list = new ArrayList<Museum>();
		Query query = DBPediaQueryBuilder.homepageMuseumsQuery(limit);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try {
			ResultSet result = qe.execSelect();
			while (result.hasNext()) {
				QuerySolution solution = result.next();
				if (solution != null) {
					String resourceUri = solution.getResource("?museum")
							.toString();
					Literal lit = solution.getLiteral("?label");
					String thumbnail = solution.getResource("?thumbnail")
							.toString();
					list.add(new Museum(lit.getValue().toString(), resourceUri,
							thumbnail));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(service + " has problems.");
		}
		return list;
	}

	public static List<Museum> retrieveMuseumsWithPrefix(String prefix,
			Integer limit) throws Exception{
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
			float currLongitude, Integer radius) {
		Query query = DBPediaQueryBuilder.museumsWithCoordinatesQuery();
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		List<GeoMuseum> list = new ArrayList<GeoMuseum>();
		try {
			ResultSet result = qe.execSelect();
			while (result.hasNext()) {
				QuerySolution solution = result.next();
				Literal litLat = solution.getLiteral("?maxlatitude");
				Literal litLong = solution.getLiteral("?maxlongitude");
				if (GeoLocationHelper.locationIsWithinRange(currLatitude,
						currLongitude, litLat.getFloat(), litLong.getFloat(),
						radius)) {
					list.add(new GeoMuseum(litLat.getFloat(), litLong
							.getFloat(), solution.getResource("?Museum")
							.toString()));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(service + " has problems.");
		}
		return list;
	}

	public static Model retrieveRDFModelForResource(String dbpediaResourceURL) {
		FileManager fManager = FileManager.get();
		fManager.addLocatorURL();

		Model model = null;
		try {
			model = fManager.loadModel(dbpediaResourceURL);
			System.out.println(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	public static List<Museum> retrieveMuseumsInCountry(String country) {
		Query query = DBPediaQueryBuilder.museumsInCountryQuery(country);
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		List<Museum> list = new ArrayList<Museum>();
		try {
			ResultSet result = qe.execSelect();
			while (result.hasNext()) {
				QuerySolution solution = result.next();
				if (solution != null) {
					String resourceUri = solution.getResource("?museum")
							.toString();
					Literal lit = solution.getLiteral("?label");
					String thumbnail = solution.getResource("?thumbnail")
							.toString();
					list.add(new Museum(lit.getValue().toString(), resourceUri,
							thumbnail));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(service + " has problems.");
		}
		return list;
	}
}
