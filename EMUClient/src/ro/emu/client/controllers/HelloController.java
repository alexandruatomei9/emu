package ro.emu.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ro.emu.client.dbpedia.DBPediaClient;
import ro.emu.client.models.MuseumRDF;

import com.hp.hpl.jena.rdf.model.Model;

@Controller
@RequestMapping("/welcome")
public class HelloController {
	@RequestMapping("/rdf")
	public void getRdfForMuseum(
			@RequestParam("dbpediaURI") String dbpediaResourceURL) {
		Model model = null;
		try {
			model = DBPediaClient
					.retrieveRDFModelForResource(dbpediaResourceURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (model != null) {
			MuseumRDF museumInfos = new MuseumRDF(model);
			System.out.println(museumInfos.wikiPageURL());
			System.out.println(museumInfos.getNsPrefixes());
			System.out.println(museumInfos.abstractValue());
			System.out.println(museumInfos.name());
			System.out.println(museumInfos.latitude());
			System.out.println(museumInfos.longitude());
			System.out.println(museumInfos.director());
			System.out.println(museumInfos.website());
			System.out.println(museumInfos.works());
			System.out.println(museumInfos.subjectsIncludingMuseum());
			System.out.println(museumInfos.locations());
			System.out.println(museumInfos.thumbnail());
			System.out.println(museumInfos.deadPeople());
			System.out.println(museumInfos.bornPeople());
		}
	}
}