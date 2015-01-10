package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.responses.Museum;
import models.responses.Response;
import utils.Code;
import dbpedia.DBPediaClient;

@Path("/museums")
public class MuseumsController {

	@GET
	@Path("/getMuseums")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMuseumsWithLimit() {
		List<Museum> list = new ArrayList<Museum>();
		Response response = new Response();
		try {
			list = DBPediaClient.retrieveMuseumsInCountry("Romania");
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
}
