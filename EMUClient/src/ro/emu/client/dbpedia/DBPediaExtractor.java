package ro.emu.client.dbpedia;

import java.util.Map;

import ro.emu.client.models.MuseumDictionary;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class DBPediaExtractor {
	public static MuseumDictionary generateDictionaryFromModel(Model model) {
		MuseumDictionary dictionary = new MuseumDictionary();
		Map<String, String> prefixes = model.getNsPrefixMap();
		StmtIterator iterator = model.listStatements();
		while (iterator.hasNext()) {
			Statement stmt = iterator.next();
			System.out.println(stmt);
		}
		return dictionary;
	}

}
