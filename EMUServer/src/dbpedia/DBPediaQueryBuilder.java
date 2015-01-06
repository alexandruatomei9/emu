package dbpedia;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;

public class DBPediaQueryBuilder {

	public static Query searchMuseumsQuery(String prefix, Integer number) {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.append("SELECT DISTINCT ?museum ?label ?thumbnail");
		qs.append("  WHERE{ \n");
		qs.append("?museum ?p ?label.");
		qs.append("?museum a ?type.");
		qs.append("?museum dbpedia-owl:thumbnail ?thumbnail.");
		qs.append("FILTER (?p=<http://www.w3.org/2000/01/rdf-schema#label>).");
		qs.append("FILTER regex(?label, \"^" + prefix + "\", \"i\").\r\n");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>)).");
		qs.append("FILTER ( lang(?label) = 'en') } LIMIT " + number);

		return qs.asQuery();
	}

	public static String nearbyMuseumsQuery(Float radius) {
		return null;
	}
}
