package ro.emu.client.dbpedia;

import javax.servlet.ServletContext;

import ro.emu.client.persistence.TBDManager;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class DBPediaClient {
	public static Model retrieveRDFModelForResource(String dbpediaURL, ServletContext servletContext)
			throws Exception {
		Model model = TBDManager.getModel(dbpediaURL, servletContext);
		if (model != null)
			return model;

		FileManager fManager = FileManager.get();
		fManager.addLocatorURL();
		model = fManager.loadModel(DBPediaClient.convertDBpediaURLToResourceURL(dbpediaURL));
		TBDManager.storeModel(model, dbpediaURL, servletContext);
		return model;
	}

	private static String convertDBpediaURLToResourceURL(String dbpediaURL) {
		StringBuilder builder = new StringBuilder(dbpediaURL.replace("/page/",
				"/data/"));
		builder.append(".rdf");
		return builder.toString();
	}
}
