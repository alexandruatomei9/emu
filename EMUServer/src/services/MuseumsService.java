package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import models.responses.Museum;
import models.responses.Response;
import utils.Code;
import dbpedia.DBPediaClient;

@Path("/museums")
public class MuseumsService {

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
		
		if (!list.isEmpty()) {
			response.setCode(Code.OK);
			response.setResponse(list);
		}
		/*List<Museum> respMus = (ArrayList<Museum>) response.getResponse();
		for(int i =0; i<respMus.size(); i++){
			System.out.println(respMus.get(i).getUri());
		}*/
		
		
		return response;
	}
	
	@GET
	@Path("/searchMuseums")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchMuseums(@QueryParam("prefix") String prefix) {
		List<Museum> list = new ArrayList<Museum>();
		Response response = new Response();
		try {
			list = DBPediaClient.retrieveMuseumsWithPrefix(prefix, 20);
		} catch (Exception e) {
			response.setCode(Code.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		if (!list.isEmpty()) {
			response.setCode(Code.OK);
			response.setResponse(list);
		}

		return response;
	}
}
