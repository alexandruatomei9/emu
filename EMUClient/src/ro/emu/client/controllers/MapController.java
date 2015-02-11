package ro.emu.client.controllers;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.emu.client.utils.Request;


@Controller
@RequestMapping("/map")
public class MapController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView loadingScreen(HttpServletRequest request)
			throws SocketException {
		ModelAndView modelAndView = new ModelAndView("mapLoader");
		return modelAndView;
	}
	
	@RequestMapping(value ="/generate",method = RequestMethod.GET)
	public ModelAndView theMap(HttpServletRequest request)
			throws SocketException {
		if (request.getParameter("latitude") == null
				|| request.getParameter("longitude") == null) {
			return new ModelAndView("redirect:" + "/");
		}

		ModelAndView modelAndView = new ModelAndView("map");
		
		modelAndView.addObject("myLat", request.getParameter("latitude"));
		modelAndView.addObject("myLong", request.getParameter("longitude"));

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("latitude", "" + request.getParameter("latitude"));
		parameters.put("longitude", "" + request.getParameter("longitude"));
		parameters.put("radius", "" + 100);
		String response = null;
		try {
			response = Request.sendGet("/map/getNearbyMuseums", parameters,
					true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponse = new JSONObject(response);

		if (jsonResponse.getString("code").equals("OK")) {
			JSONArray geoMuseums = jsonResponse.getJSONArray("response");
			JSONArray toSend = new JSONArray();
			toSend = geoMuseums;
			String myJson = toSend.toString();
			modelAndView.addObject("pins", myJson.replace("'", "`"));
		}
		return modelAndView;
	}
}
