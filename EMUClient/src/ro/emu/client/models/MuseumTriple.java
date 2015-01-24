package ro.emu.client.models;

import com.hp.hpl.jena.rdf.model.Statement;
/**
 * Class that keeps a statement(subject-predicate-object) about a museum.
 * @author claudiu
 *
 */
public class MuseumTriple {
	private String key;
	private Statement statement;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Statement getStatement() {
		return statement;
	}
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
}
