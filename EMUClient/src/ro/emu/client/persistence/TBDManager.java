package ro.emu.client.persistence;

import javax.servlet.ServletContext;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;

public class TBDManager {

	// private static String root = "/WEB-INF/TBD";
	// private static ModelMaker maker =
	// ModelFactory.createFileModelMaker(root);

	public static void storeModel(Model model, String modelName, ServletContext servletContext) throws ClassNotFoundException {
		// Now we copy the in-memory model into our DB backend.
		// When the model is created you can give it the name that you like.

		String root = servletContext.getRealPath("/WEB-INF/TBD");
		ModelMaker maker = ModelFactory.createFileModelMaker(root);

		Model dbModel = maker.createModel(modelName);
		dbModel.add(model);
		dbModel.close();
	}

	public static Model getModel(String modelName, ServletContext servletContext) {
		String root = servletContext.getRealPath("/WEB-INF/TBD");
		ModelMaker maker = ModelFactory.createFileModelMaker(root);
		return maker.getModel(modelName);
	}

	public static boolean existsModelWithURI(String modelName, ServletContext servletContext) {
		String root = servletContext.getRealPath("/WEB-INF/TBD");
		ModelMaker maker = ModelFactory.createFileModelMaker(root);
		return maker.hasModel(modelName);
	}

}
