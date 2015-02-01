package ro.emu.client.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import ro.emu.client.dbpedia.DBPediaClient;
import ro.emu.client.models.MuseumThumbnail;
import ro.emu.client.rdfmodels.MuseumRDF;
import ro.emu.client.rdfmodels.WorkRDF;
import ro.emu.client.utils.Pair;
import ro.emu.client.utils.Request;

@Controller
@RequestMapping("/home")
public class HomeController {

	private static final String URI = "http://dbpedia.org/page/The_Louvre";
	
	
	@Autowired
	ServletContext servletContext;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView homeMuseums(String uri) {
		if(uri==null || uri.equals("")){
			uri = URI;
		}
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
			if (jsonResponse.has("code") && !jsonResponse.isNull("code")) {
				if (!jsonResponse.getString("code").equals("OK")) {
					// server error
				} else {
					JSONArray items = jsonResponse.getJSONArray("response");
					for (int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);
						MuseumThumbnail thumb = new MuseumThumbnail();
						thumb.setMuseumName(StringEscapeUtils.escapeHtml4(item.getString("name")));
						thumb.setImageUrl(StringEscapeUtils.escapeHtml4(item.getString("thumbUri")));
						thumb.setGetDetailsUrl(StringEscapeUtils.escapeHtml4(item.getString("uri")));
						museumsThumbs.add(thumb);
					}
				}
			}
		}

		modelAndView.addObject("museumThumbs", museumsThumbs);

		// for the 5th museum display the image and some details
		Map<String, String> ns = new HashMap<String,String>();
		Model model = null;
		try {

			model = DBPediaClient.retrieveRDFModelForResource(
					uri, servletContext);
			MuseumRDF museumRDF = new MuseumRDF(model);
			ns.putAll(museumRDF.getNsPrefixes());
			modelAndView.addObject("namespaces", ns);
			modelAndView.addObject("museumRDF", museumRDF);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;

	}
	
	@RequestMapping(value="/works", method = RequestMethod.GET)
	public ModelAndView homeMuseumWork(String uri) {
		if(uri==null || uri.equals("")){
			uri = URI;
		}
		ModelAndView modelAndView = new ModelAndView("works");
		ArrayList<WorkRDF> worksRDF = new ArrayList<WorkRDF>();
		Model model = null;
		try {
			model = DBPediaClient.retrieveRDFModelForResource(
					uri, servletContext);
			MuseumRDF museumRDF = new MuseumRDF(model);
			for(Pair<String,Resource> workPair : museumRDF.getWorks()){
				Model workModel = DBPediaClient.retrieveRDFModelForResource(workPair.getSecond().getURI(), servletContext);
				WorkRDF workRDF = new WorkRDF(workModel);
				//Map<String, String> ns = workRDF.getNsPrefixes();
				worksRDF.add(workRDF);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.addObject("works", worksRDF);
		
		return modelAndView;
	}
}
