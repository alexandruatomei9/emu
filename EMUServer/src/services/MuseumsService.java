package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import models.responses.GeoMuseum;
import models.responses.Museum;
import models.responses.Response;
import utils.Code;
import utils.MuseumFilter;
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMuseumsWithFilters(
			@QueryParam("country") String country,
			@QueryParam("type") String type) {
		Response response = new Response();
		List<GeoMuseum> list = new ArrayList<GeoMuseum>();
		MuseumFilter filter = findTypeOfRequest(country, type);
		try {
			if (filter == MuseumFilter.Country) {
				list = DBPediaClient.retrieveGeoMuseumsInCountry(country, 100);
			} else if (filter == MuseumFilter.Type) {
				list = DBPediaClient.retrieveGeoMuseumsWithType(type, 100);
			} else if (filter == MuseumFilter.CountryAndType) {
				list = DBPediaClient.retrieveGeoMuseumsInCountryWithType(
						country, type, 100);
			}
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

	private MuseumFilter findTypeOfRequest(String country, String type) {
		if (country != null && type != null)
			return MuseumFilter.CountryAndType;
		if (type != null)
			return MuseumFilter.Type;
		if (country != null)
			return MuseumFilter.Country;

		return MuseumFilter.Invalid;
	}
}
