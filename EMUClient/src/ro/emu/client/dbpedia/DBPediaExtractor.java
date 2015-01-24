package ro.emu.client.dbpedia;

import java.util.ArrayList;
import java.util.List;

import ro.emu.client.utils.Constants;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class DBPediaExtractor {

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

	private static StmtIterator propertyIterator(Model model,
			final Property property, final String lang) {
		SimpleSelector selector = new SimpleSelector(null, null, (RDFNode) null) {
			public boolean selects(Statement s) {
				RDFNode node = s.getObject();
				if (node.isLiteral()) {
					Literal lit = (Literal) node;
					if (lit.getLanguage().length() > 0
							&& !lit.getLanguage().equalsIgnoreCase(lang)) {
						return false;
					}
				}
				return s.getPredicate().equals(property);
			}
		};
		StmtIterator iter = model.listStatements(selector);
		return iter;
	}

	public static Statement statementWithProperties(Model model,
			final Property property, final String lang) {
		StmtIterator iter = propertyIterator(model, property, lang);
		if (iter.hasNext()) {
			return iter.next();
		}
		return null;
	}

	public static Statement statementWithProperties(Model model,
			Property property) {
		return statementWithProperties(model, property, "en");
	}

	public static List<Statement> statementsWithProperties(Model model,
			final Property property, final String lang) {
		StmtIterator iter = propertyIterator(model, property, lang);
		if (iter != null) {
			return iter.toList();
		}
		return null;
	}

	public static List<Statement> statementsWithProperties(Model model,
			Property property) {
		return statementsWithProperties(model, property, "en");
	}

}
