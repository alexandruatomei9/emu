package ro.emu.client.controllers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.emu.client.models.Detail;
import ro.emu.client.models.Museum;
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
		Museum museum = new Museum(
 				"New_Walk_Museum",
				"http://commons.wikimedia.org/wiki/Special:FilePath/New_Walk_Museum_main_entrance.jpg?width=300",
				"http://www.leicester.gov.uk/your-council-services/lc/leicester-city-museums/museums/nwm-art-gallery/",
				"The New Walk Museum and Art Gallery is a museum on New Walk in Leicester, England, not far from the city centre. The original building was designed by Joseph Hansom, designer of the hansom cab. Two dinosaur skeletons are permanently installed in the museum — a cetiosaur found in Rutland (affectionately named George), and a plesiosaur from Barrow upon Soar.Other permanent exhibits include an Egyptian area, minerals of Leicestershire, the first Charnia fossil identified nearby, and a wildspace area featuring stuffed animals from around the world.The museum opened in 1849 as one of the first public museums established within the United KingdomIn September 2011, the New Walk Museum expanded its Dinosaur Gallery, reorganizing fossils, adding a new room, and modifying the gallery itself. The opening of the new Dinosaur Gallery was launched by David Attenborough. The \"star attractions\" of the new gallery include the aforementioned Rutland cetiosaur, Charnia and plesiosaur fossils, as well as a Leedsichthys fossil and a piece of the Barwell Meteorite. The new gallery predominantly features on extinct marine reptiles.");

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
