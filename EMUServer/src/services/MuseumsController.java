package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hp.hpl.jena.rdf.model.Model;

import models.responses.Museum;
import models.responses.Response;
import utils.Code;
import dbpedia.DBPediaClient;

@Path("/museums")
public class MuseumsController {

	@GET
	@Path("/getMuseums")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomePageMuseums() {
		List<Museum> list = new ArrayList<Museum>();
		Response response = new Response();
		try {
			list = DBPediaClient.retrieveHomeMuseums(4);
		} catch (Exception e) {
			response.setCode(Code.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		if(!list.isEmpty()){
			response.setCode(Code.OK);
			response.setResponse(list);
		}
		
		return response;
	}
	
	@GET
	@Path("/getRdfForMuseum")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRdfForMuseum(@QueryParam("resourceUri") String dbpediaResourceURL) {
		Model model = null;
		Response response = new Response();
		try {
			model = DBPediaClient.retrieveRDFModelForResource(dbpediaResourceURL);
		} catch (Exception e) {
			response.setCode(Code.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		System.out.println(model);
		System.out.println(model.getGraph());
		if(model!=null){
			response.setCode(Code.OK);
			response.setResponse(model);
		}
		
		return response;
	}
}
