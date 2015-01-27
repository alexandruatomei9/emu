package ro.emu.client.controllers;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hp.hpl.jena.rdf.model.Model;

import ro.emu.client.dbpedia.DBPediaClient;
import ro.emu.client.models.MuseumRDF;
import ro.emu.client.models.MuseumThumbnail;
import ro.emu.client.utils.Request;

@Controller
@RequestMapping("/home")
public class HomeController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView homeMuseums() {
		ModelAndView modelAndView = new ModelAndView("index");

		String resp = null;
		try {
			// get 4 museums from the api
			resp = Request.sendGet("/museums/getMuseums", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<MuseumThumbnail> museumsThumbs = new ArrayList<MuseumThumbnail>();
		if (resp != null) {
			JSONObject jsonResponse = new JSONObject(resp);
			if (!jsonResponse.getString("code").equals("OK")) {
				// server error
			} else {
				JSONArray items = jsonResponse.getJSONArray("response");
				for (int i = 0; i < items.length(); i++) {
					JSONObject item = items.getJSONObject(i);
					MuseumThumbnail thumb = new MuseumThumbnail();
					thumb.setMuseumName(item.getString("name"));
					thumb.setImageUrl(item.getString("thumbUri"));
					thumb.setGetDetailsUrl(item.getString("uri"));
					museumsThumbs.add(thumb);
				}
			}
		}

		modelAndView.addObject("museumThumbs", museumsThumbs);

		// for the 5th museum display the image and some details

		Model model = null;
		try {
			model = DBPediaClient
					.retrieveRDFModelForResource("http://dbpedia.org/data/British_Museum.rdf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		MuseumRDF museumRDF = new MuseumRDF(model);
		Map<String, String> ns = museumRDF.getNsPrefixes();

		modelAndView.addObject("namespaces", ns);

		modelAndView.addObject("museumRDF", museumRDF);

		return modelAndView;

	}
}
