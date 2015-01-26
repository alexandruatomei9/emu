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
import ro.emu.client.models.Detail;
import ro.emu.client.models.Museum;
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
			//get 4 museums from the api
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
					.retrieveRDFModelForResource("http://dbpedia.org/data/The_Louvre.rdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MuseumRDF museumRDF = new MuseumRDF(model);
		Map<String,String> ns = museumRDF.getNsPrefixes();
		
		modelAndView.addObject("namespaces", ns);
		
		Museum museum = new Museum(
				museumRDF.name().getSecond(),
				"http://commons.wikimedia.org/wiki/Special:FilePath/New_Walk_Museum_main_entrance.jpg?width=300",
				museumRDF.website().getSecond(),
				museumRDF.abstractValue().getSecond());
		ArrayList<Detail> museumDetails = new ArrayList<Detail>();
		museumDetails.add(new Detail("City", "Leicester"));
		museumDetails.add(new Detail("Country", "United_Kingdom"));
		museumDetails.add(new Detail("Latitude", "52.628954"));
		museumDetails.add(new Detail("Longitude", "-1.127765"));
		museumDetails.add(new Detail("Wiki Page",
				"http://en.wikipedia.org/wiki/New_Walk_Museum"));

		museum.setDetails(museumDetails);

		modelAndView.addObject("museum", museum);

		return modelAndView;

	}
}
