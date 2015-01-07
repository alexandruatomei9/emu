package dbpedia;

import java.util.ArrayList;
import java.util.List;

import models.responses.Museum;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

public class DBPediaClient {
	private static String service = "http://dbpedia.org/sparql";

	public static List<Museum> retrieveMuseumsWithPrefix(String prefix,
			Integer limit) {
		Query query = DBPediaQueryBuilder.searchMuseumsQuery(prefix, 100);
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
					String thumbnail = solution.getResource("?thumbnail").toString();
					list.add(new Museum(lit.getValue().toString(),resourceUri,thumbnail));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(service + " has problems.");
		}
		return list;
	}
}
