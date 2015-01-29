package ro.emu.client.controllers;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ro.emu.client.dbpedia.DBPediaClient;
import ro.emu.client.models.MuseumRDF;

import com.hp.hpl.jena.rdf.model.Model;

@Controller
@RequestMapping("/welcome")
public class HelloController {
	@Autowired ServletContext servletContext;
	
	@RequestMapping("/rdf")
	public void getRdfForMuseum(
			@RequestParam("dbpediaURI") String dbpediaResourceURL) {
		Model model = null;
		try {
			model = DBPediaClient
					.retrieveRDFModelForResource(dbpediaResourceURL, servletContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (model != null) {
			MuseumRDF museumInfos = new MuseumRDF(model);
			System.out.println(museumInfos.getWikiPageURL());
			System.out.println(museumInfos.getNsPrefixes());
			System.out.println(museumInfos.getAbstract());
			System.out.println(museumInfos.getName());
			System.out.println(museumInfos.getLatitude());
			System.out.println(museumInfos.getDirector());
			System.out.println(museumInfos.getWebsite());
			System.out.println(museumInfos.getWorks());
			System.out.println(museumInfos.getSubjectsIncludingMuseum());
			System.out.println(museumInfos.getLocations());
			System.out.println(museumInfos.getThumbnail());
			System.out.println(museumInfos.getDeadPeople());
			System.out.println(museumInfos.getBornPeople());
		}
	}
}