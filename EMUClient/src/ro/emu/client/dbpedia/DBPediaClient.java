package ro.emu.client.dbpedia;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class DBPediaClient {
	public static Model retrieveRDFModelForResource(String dbpediaResourceURL)
			throws Exception {
		FileManager fManager = FileManager.get();
		fManager.addLocatorURL();
		Model model = null;
		model = fManager.loadModel(dbpediaResourceURL);
		return model;
	}
}
