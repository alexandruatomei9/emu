package ro.emu.client.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import ro.emu.client.utils.Constants;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.StoreDesc;
import com.hp.hpl.jena.sdb.sql.JDBC;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.store.DatabaseType;
import com.hp.hpl.jena.sdb.store.LayoutType;

public class TBDManager {

	public static void storeModel(Model model, String modelName,
			ServletContext servletContext) throws ClassNotFoundException {
		// Now we copy the in-memory model into our DB backend.
		// When the model is created you can give it the name that you like.

		StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash,
				DatabaseType.MySQL);
		JDBC.loadDriverMySQL();
		String jdbcURL = "jdbc:mysql://localhost:3306/SDB";
		SDBConnection conn = new SDBConnection(jdbcURL, "root", "root");
		Store store = SDBFactory.connectStore(conn, storeDesc);
		Dataset ds = SDBFactory.connectDataset(store);
		ds.addNamedModel(modelName, model);
		store.close();
		conn.close();

		// String root = servletContext.getRealPath("/WEB-INF/TBD");
		// ModelMaker maker = ModelFactory.createFileModelMaker(root);
		//
		// Model dbModel = maker.createModel(modelName);
		// dbModel.add(model);
		// dbModel.close();
	}

	public static Model getModel(String modelName, ServletContext servletContext) {
		// String root = servletContext.getRealPath("/WEB-INF/TBD");
		// ModelMaker maker = ModelFactory.createFileModelMaker(root);
		// return maker.getModel(modelName);

		StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash,
				DatabaseType.MySQL);
		JDBC.loadDriverMySQL();
		String jdbcURL = "jdbc:mysql://localhost:3306/SDB";
		SDBConnection conn = new SDBConnection(jdbcURL, "root", "");
		Store store = SDBFactory.connectStore(conn, storeDesc);
		// here is our mode
		Model m = null;
		Dataset ds = SDBFactory.connectDataset(store);
		// check the dataset if it contains our model
		if (ds.containsNamedModel(modelName)) {
			m = ds.getNamedModel(modelName);
			rebuildModelNamespaces(m);
		}
		return m;
	}

	private static void rebuildModelNamespaces(Model model) {
		Map<String, String> prefixes = new HashMap<String, String>();
		prefixes.put("rdfs", Constants.rdfs);
		prefixes.put("rdf", Constants.rdf);
		prefixes.put("prov", Constants.prov);
		prefixes.put("owl", Constants.owl);
		prefixes.put("grs", Constants.grs);
		prefixes.put("geo", Constants.geo);
		prefixes.put("foaf", Constants.foaf);
		prefixes.put("dcterms", Constants.dcterms);
		prefixes.put("dbpedia-owl", Constants.dbpedia_owl);
		model.setNsPrefixes(prefixes);
	}

	public static boolean existsModelWithURI(String modelName,
			ServletContext servletContext) {
		String root = servletContext.getRealPath("/WEB-INF/TBD");
		ModelMaker maker = ModelFactory.createFileModelMaker(root);
		return maker.hasModel(modelName);
	}

}
