package ro.emu.client.dbpedia;

import javax.servlet.ServletContext;

import ro.emu.client.persistence.TBDManager;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class DBPediaClient {
	public static Model retrieveRDFModelForResource(String dbpediaURL,
			ServletContext servletContext) throws Exception {
		Model model = TBDManager.getModel(dbpediaURL, servletContext);
		if (model != null) {
			return model;
		}

		FileManager fManager = FileManager.get();
		fManager.addLocatorURL();

		try {
			model = fManager.loadModel(DBPediaClient.convertDBpediaURLToResourceURL(dbpediaURL));
			TBDManager.storeModel(model, dbpediaURL, servletContext);
			//System.out.println("--- Found "+dbpediaURL);
		} catch (Exception ex) {
			ex.printStackTrace();
			//System.out.println("Resource not found "+dbpediaURL);
			return null;
		}
		return model;
	}

	private static String convertDBpediaURLToResourceURL(String dbpediaURL) {
		StringBuilder builder = null;
		
		if(dbpediaURL.contains("/page/")){
			builder = new StringBuilder(dbpediaURL.replace("/page/",
					"/data/"));
		}else{
			builder = new StringBuilder(dbpediaURL.replace("/resource/",
					"/data/"));
		}
		
		builder.append(".rdf");
		return builder.toString();
	}
}
