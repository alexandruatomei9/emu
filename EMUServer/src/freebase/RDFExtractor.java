package freebase;

import java.util.Set;

import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

public class RDFExtractor {
	private Model rdfModel;

	public RDFExtractor() {

	}

	public RDFExtractor(Model rdfModel) {
		this.setRdfModel(rdfModel);
	}

	public Model getRdfModel() {
		return rdfModel;
	}

	public void setRdfModel(Model rdfModel) {
		this.rdfModel = rdfModel;
	}

	public Set<Value> objectsFor(Model rdfModel, Resource subject, URI predicate) {
		return null;
	}

	public Value objectFor(Model rdfModel,Resource subject, URI predicate, String countryCode) {
		return null;
	}
	
	public Set<Value> objectsFor(Resource subject, URI predicate) {
		return null;
	}

	public Value objectFor(Resource subject, URI predicate, String countryCode) {
		return null;
	}
}
