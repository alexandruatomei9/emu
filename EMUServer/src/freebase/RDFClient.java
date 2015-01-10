package freebase;

import org.apache.marmotta.ldclient.exception.DataRetrievalException;
import org.apache.marmotta.ldclient.model.ClientConfiguration;
import org.apache.marmotta.ldclient.model.ClientResponse;
import org.apache.marmotta.ldclient.services.ldclient.LDClient;
import org.openrdf.model.Model;

import utils.Constants;

public class RDFClient {
	private static ClientConfiguration config = new ClientConfiguration();

	public static Model retrieveRDFModelWithId(String id) {
		Model model = null;
		LDClient ldclient = new LDClient(config);
		ClientResponse response;
		try {
			response = ldclient
					.retrieveResource(Constants.freebaseRDFResourceEndpoint
							+ id);
			model = response.getData();
		} catch (DataRetrievalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
}
