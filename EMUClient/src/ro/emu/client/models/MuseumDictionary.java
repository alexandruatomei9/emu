package ro.emu.client.models;

import java.util.Map;

import com.hp.hpl.jena.rdf.model.Statement;

/**
 * Class that keeps a map with the used RDF namespaces and the statements about
 * a museum
 * 
 * @author claudiu
 *
 */
public class MuseumDictionary {
	private Map<String, String> nsPrefixes;
	private Map<String, Statement> triples;

	public MuseumDictionary() {
		
	}
	
	public MuseumDictionary(Map<String, String> nsPrefixes,
			Map<String, Statement> triples) {
		this.nsPrefixes = nsPrefixes;
		this.triples = triples;
	}

	public Map<String, String> getNsPrefixes() {
		return nsPrefixes;
	}

	public void setNsPrefixes(Map<String, String> nsPrefixes) {
		this.nsPrefixes = nsPrefixes;
	}

	public Map<String, Statement> getTriples() {
		return triples;
	}

	public void setTriples(Map<String, Statement> triples) {
		this.triples = triples;
	}
}
