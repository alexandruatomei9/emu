package ro.emu.client.dbpedia;

import java.util.List;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class DBPediaExtractor {

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
