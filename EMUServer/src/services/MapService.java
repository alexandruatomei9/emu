package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import models.responses.GeoMuseum;
import models.responses.Response;
import utils.Code;
import dbpedia.DBPediaClient;

@Path("/map")
public class MapService {

	@GET
	@Path("/getNearbyMuseums")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNearbyMuseums(@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude,
			@QueryParam("radius") Integer radius) {
		// TODO:SLider with values
		List<GeoMuseum> list = new ArrayList<GeoMuseum>();
		Response response = new Response();
		try {
			list = DBPediaClient.retrieveNearbyMuseums(
					Float.parseFloat(latitude), Float.parseFloat(longitude),
					radius);
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
