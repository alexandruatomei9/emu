package ro.emu.client.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ModelAndView;

import ro.emu.client.utils.Request;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

@Controller
@RequestMapping("/map")
public class MapController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView homeMuseums(HttpServletRequest request)
			throws SocketException {
		if(request.getParameter("ip") == null){
			return new ModelAndView("redirect:" + "home");
		}
		
		
		ModelAndView modelAndView = new ModelAndView("map");

		LookupService lookupService = null;
		try {
			ServletContext context = request.getSession().getServletContext();
			ServletContextResource resource = new ServletContextResource(
					context, "/resources/GeoLiteCity.dat");
			lookupService = new LookupService(resource.getFile(),
					LookupService.GEOIP_MEMORY_CACHE);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Location location = null;
		if (lookupService != null) {
			location = lookupService.getLocation(request.getParameter("ip")
					.trim());
		}

		if (location != null) {
			modelAndView.addObject("city", location.city);
			modelAndView.addObject("myLat", location.latitude);
			modelAndView.addObject("myLong", location.longitude);
		}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("latitude", "" + location.latitude);
		parameters.put("longitude", "" + location.longitude);

		String response = null;
		try {
			response = Request.sendGet("/map/getNearbyMuseums", parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponse = new JSONObject(response);

		if (jsonResponse.getString("code").equals("OK")) {
			JSONArray geoMuseums = jsonResponse.getJSONArray("response");
			JSONArray toSend = new JSONArray();
			if (geoMuseums.length() > 20) {
				for (int i = 0; i < 19; i++) {
					if (geoMuseums.getJSONObject(i) != null) {
						toSend.put(i, geoMuseums.getJSONObject(i));
					}
				}
			} else {
				toSend = geoMuseums;
			}
			String myJson = toSend.toString();
			modelAndView.addObject("pins", myJson.replace("'", "`"));
		}
		return modelAndView;

	}
}
