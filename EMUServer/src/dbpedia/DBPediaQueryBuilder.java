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

	public static Query museumsWithCoordinatesQuery() {
		ParameterizedSparqlString qs = new ParameterizedSparqlString();
		qs.setNsPrefix("dbpedia-owl", "http://dbpedia.org/ontology/");
		qs.setNsPrefix("dbpprop", "http://dbpedia.org/property/");
		qs.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		qs.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		qs.append("SELECT ?Museum ?maxlongitude ?maxlatitude WHERE { ");
		qs.append("{");
		qs.append("SELECT ?Museum");
		qs.append("(MIN(?name) as ?maxname)");
		qs.append("(MAX(?longitude) as ?maxlongitude)");
		qs.append("(MAX(?latitude) as ?maxlatitude)");
		qs.append("WHERE {");
		qs.append("?Museum a ?type ;");
		qs.append("dbpprop:name ?name ;");
		qs.append("geo:lat ?latitude ;");
		qs.append("geo:long ?longitude .");
		qs.append("FILTER (langMatches(lang(?name),\"EN\"))");
		qs.append("FILTER (?type IN (<http://dbpedia.org/ontology/Museum>, <http://schema.org/Museum>)).");
		qs.append("}");
		qs.append("GROUP BY ?Museum");
		qs.append("}}");

		return qs.asQuery();
	}
}
