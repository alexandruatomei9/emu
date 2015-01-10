package services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import models.responses.Response;

@Path("/museums")
public class MuseumsController {

	@POST
	@Path("/getMuseumsWithLimit")
	public Response getMuseumsWithLimit(@FormParam("limit") String limit) {
		return new Response();
	}
}
