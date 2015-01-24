package ro.emu.client.dbpedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ro.emu.client.models.MuseumDictionary;
import ro.emu.client.utils.Constants;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class DBPediaExtractor {

	public static MuseumDictionary generateDictionaryFromModel(Model model) {
		MuseumDictionary dictionary = new MuseumDictionary();
		Map<String, String> prefixes = model.getNsPrefixMap();
		Map<String, Statement> triplesMap = new HashMap<String, Statement>();
		final ArrayList<Property> aboutProperties = filteringPropertiesForModel(model);
		SimpleSelector aboutSelector = new SimpleSelector(null, null,
				(RDFNode) null) {
			public boolean selects(Statement s) {
				return aboutProperties.contains(s.getPredicate());
			}
		};
		StmtIterator iter = model.listStatements(aboutSelector);
		while (iter.hasNext()) {
			Statement stmt = iter.next();
			if (stmt != null)
				if (stmt.getPredicate() != null) {
					System.out.println(stmt.getPredicate().toString());
					triplesMap.put(stmt.getPredicate().toString(), stmt);
				}
		}
		dictionary.setNsPrefixes(prefixes);
		dictionary.setTriples(triplesMap);
		return dictionary;
	}

	private static ArrayList<Property> filteringPropertiesForModel(Model model) {
		ArrayList<Property> properties = new ArrayList<Property>();
		// Name
		properties.add(model.createProperty(Constants.foaf,
				Constants.dbpNameKey));
		properties.add(model.createProperty(Constants.rdfs,
				Constants.dbpLabelKey));
		// Abstract
		properties.add(model.createProperty(Constants.dbpedia_owl,
				Constants.dbpAbstractKey));
		// Director
		properties.add(model.createProperty(Constants.dbpedia_owl,
				Constants.dbpDirectorKey));
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpDirectorKey));
		// Number of visitors
		properties.add(model.createProperty(Constants.dbpedia_owl,
				Constants.dbpNumberOfVisitorsKey));
		// Thumbnail
		properties.add(model.createProperty(Constants.dbpedia_owl,
				Constants.dbpThumbnailKey));
		// Established
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpEstablishedKey));
		// Geolocation
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpGeoLatKey));
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpGeoLongKey));
		// Location
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpLocationKey));
		// Subject of
		properties.add(model.createProperty(Constants.dcterms,
				Constants.dbpSubjectKey));
		// Homepage
		properties.add(model.createProperty(Constants.foaf,
				Constants.dbpHomepageKey));
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpWebsiteKey));
		// Curator
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpCuratorKey));

		// Award of
		properties.add(model.createProperty(Constants.dbpedia_owl,
				Constants.dbpAwardOfKey));
		// Birthplace of
		properties.add(model.createProperty(Constants.dbpedia_owl,
				Constants.dbpBirthPlaceOfKey));
		// Deathplace of
		properties.add(model.createProperty(Constants.dbpedia_owl,
				Constants.dbpDeathPlaceOfKey));
		// Primary Topic of
		properties.add(model.createProperty(Constants.foaf,
				Constants.dbpPrimaryTopicKey));
		// Works of
		properties.add(model.createProperty(Constants.dbpprop,
				Constants.dbpMuseumKey));

		return properties;
	}
}
