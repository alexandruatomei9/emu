package ro.emu.client;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.emu.client.models.MuseumThumbnail;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView printWelcome() {
		
		//get the 4 museums from the API
		ModelAndView modelAndView = new ModelAndView("index");
		
		ArrayList<MuseumThumbnail> museumsThumbs = new ArrayList<MuseumThumbnail>();
		museumsThumbs.add(new MuseumThumbnail("The_Bakken", "http://commons.wikimedia.org/wiki/Special:FilePath/The_Bakken_Museum_-_Medicinal_Garden_View.jpg?width=300"));
		museumsThumbs.add(new MuseumThumbnail("AutoWorld", "http://commons.wikimedia.org/wiki/Special:FilePath/Autoworld_Cinquantenaire.JPG?width=300"));
		museumsThumbs.add(new MuseumThumbnail("Le_Magasin", "http://commons.wikimedia.org/wiki/Special:FilePath/CNAC_-_Le_Magasin_-_Grenoble_2.JPG?width=300"));
		museumsThumbs.add(new MuseumThumbnail("New_Walk_Museum", "http://commons.wikimedia.org/wiki/Special:FilePath/New_Walk_Museum_main_entrance.jpg?width=300"));
		
		modelAndView.addObject("museumThumbs", museumsThumbs);
		
		
		return modelAndView;

	}
}
