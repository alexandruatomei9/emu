package ro.emu.client.dbpedia;

import ro.emu.client.persistence.TBDManager;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class DBPediaClient {
	public static Model retrieveRDFModelForResource(String dbpediaURL)
			throws Exception {
		Model model = TBDManager.getModel(dbpediaURL);
		if (model != null)
			return model;

		FileManager fManager = FileManager.get();
		fManager.addLocatorURL();
		model = fManager.loadModel(DBPediaClient.convertDBpediaURLToResourceURL(dbpediaURL));
		TBDManager.storeModel(model, dbpediaURL);
		return model;
	}

	private static String convertDBpediaURLToResourceURL(String dbpediaURL) {
		StringBuilder builder = new StringBuilder(dbpediaURL.replace("/page/",
				"/data/"));
		builder.append(".rdf");
		return builder.toString();
	}
}
