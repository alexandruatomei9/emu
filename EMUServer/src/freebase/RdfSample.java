package freebase;

import java.util.Set;

import org.apache.marmotta.ldclient.endpoint.freebase.FreebaseEndpoint;
import org.apache.marmotta.ldclient.model.ClientConfiguration;
import org.apache.marmotta.ldclient.model.ClientResponse;
import org.apache.marmotta.ldclient.services.ldclient.LDClient;
import org.openrdf.model.Literal;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDFS;

public class RdfSample {

	public static void testRDF() {
		try {
			// String serviceURL = "https://www.googleapis.com/freebase/v1/rdf";
			// HttpClient httpclient = new DefaultHttpClient();
			// String topicId = "/m/020c55";
			// String url = serviceURL + topicId + "?key="
			// + "AIzaSyBS5BYpKRPYtUqBnu8KzDAAFMI5yPOYgWQ";
			// HttpGet request = new HttpGet(url);
			// HttpResponse httpResponse = httpclient.execute(request);

			ClientConfiguration config = new ClientConfiguration();
			config.addEndpoint(new FreebaseEndpoint());
			LDClient ldclient = new LDClient(config);
			ClientResponse response = ldclient
					.retrieveResource("http://rdf.freebase.com/ns/m.04gdr");
			Model model = response.getData();
			for (Resource res : model.subjects()) {
				Set<Value> objects = model.filter(res,RDFS.LABEL,null).objects();
				for (Value lit: objects) {
					Literal literal = (Literal)lit;
					System.out.println(literal.getLanguage());
				}
			}
			// Resource topic = model.getResource("http://rdf.freebase.com/ns/"
			// + topicId.substring(1).replace('/', '.'));
			// Property labelProperty = model
			// .getProperty("http://www.w3.org/2000/01/rdf-schema#label");
			// System.out.println(topic.getProperty(labelProperty).getString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}