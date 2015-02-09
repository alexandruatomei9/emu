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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import ro.emu.client.dbpedia.DBPediaClient;
import ro.emu.client.models.MuseumThumbnail;
import ro.emu.client.rdfmodels.MuseumRDF;
import ro.emu.client.rdfmodels.PersonRDF;
import ro.emu.client.rdfmodels.WorkRDF;
import ro.emu.client.utils.Pair;
import ro.emu.client.utils.Request;

@Controller
@RequestMapping("/")
public class HomeController {

	private static final String URI = "http://dbpedia.org/page/The_Louvre";

	@Autowired
	ServletContext servletContext;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView homeMuseums(String uri) {
		if (uri == null || uri.equals("")) {
			uri = URI;
		}
		ModelAndView modelAndView = new ModelAndView("index");

		String resp = null;
		try {
			// get 4 museums from the api
			resp = Request.sendGet("/museums/getMuseums", null, true);
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
						thumb.setMuseumName(StringEscapeUtils.escapeHtml4(item
								.getString("name")));
						thumb.setImageUrl(StringEscapeUtils.escapeHtml4(item
								.getString("thumbUri")));
						thumb.setGetDetailsUrl(StringEscapeUtils
								.escapeHtml4(item.getString("uri")));
						museumsThumbs.add(thumb);
					}
				}
			}
		}

		modelAndView.addObject("museumThumbs", museumsThumbs);

		// for the 5th museum display the image and some details
		Map<String, String> ns = new HashMap<String, String>();
		Model model = null;
		try {
			model = DBPediaClient.retrieveRDFModelForResource(uri,
					servletContext);
			MuseumRDF museumRDF = new MuseumRDF(model);
			ns.putAll(museumRDF.getNsPrefixes());
			modelAndView.addObject("namespaces", ns);
			modelAndView.addObject("museumRDF", museumRDF);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;

	}

	@RequestMapping(value = "works", method = RequestMethod.GET)
	public ModelAndView homeMuseumWork(
			@RequestParam(value = "workURI", required = true) String uri) {
		if (uri == null || uri.equals("")) {
			uri = URI;
		}
		ModelAndView modelAndView = new ModelAndView("works");
		try {
			Model workModel = DBPediaClient.retrieveRDFModelForResource(uri,
					servletContext);
			if (workModel != null) {
				WorkRDF workRDF = new WorkRDF(workModel);
				modelAndView.addObject("resourceName",
						workRDF.getResourceName());
				modelAndView.addObject("description", workRDF.getAbstract());
				modelAndView.addObject("name", workRDF.getName());
				modelAndView.addObject("thumbnail", workRDF.thumbnail());
				modelAndView.addObject("wiki_page_url",
						workRDF.getWikiPageURL());

				Pair<String, Resource> authorPair = workRDF.author();
				if (authorPair != null && authorPair.isValid()
						&& authorPair.getSecond().getURI() != null) {
					Model authorModel = DBPediaClient
							.retrieveRDFModelForResource(authorPair.getSecond()
									.getURI(), servletContext);
					if (authorModel != null) {
						PersonRDF personRDF = new PersonRDF(authorModel);
						modelAndView.addObject("authorResourceName",
								personRDF.getResourceName());
						modelAndView.addObject("authorAbstract",
								personRDF.getAbstract());
						modelAndView.addObject("authorName",
								personRDF.getName());
						modelAndView.addObject("authorThumbnail",
								personRDF.thumbnail());
						modelAndView.addObject("authorWiki",
								personRDF.getWikiPageURL());
						modelAndView.addObject("authorBirthDate",
								personRDF.getBirthDate());
						modelAndView.addObject("authorDeathDate",
								personRDF.getDeathDate());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(value = "director", method = RequestMethod.GET)
	public ModelAndView museumDirector(
			@RequestParam(value = "directorURI") String directorURI) {
		ModelAndView modelAndView = new ModelAndView("director");
		try {
			Model personModel = DBPediaClient.retrieveRDFModelForResource(
					directorURI, servletContext);
			if (personModel != null) {
				PersonRDF personRDF = new PersonRDF(personModel);
				modelAndView.addObject("resourceName",
						personRDF.getResourceName());
				modelAndView.addObject("description", personRDF.getAbstract());
				modelAndView.addObject("name", personRDF.getName());
				modelAndView.addObject("thumbnail", personRDF.thumbnail());
				modelAndView.addObject("wiki",
						personRDF.getWikiPageURL());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(value = "museumDetails", method = RequestMethod.GET)
	public ModelAndView searchMuseum(
			@RequestParam(value = "museum", required = true) String museum) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("prefix", museum);

		String response;
		JSONObject myJson = null;
		try {
			response = Request.sendGet("/museums/searchMuseums", parameters,
					true);
			myJson = new JSONObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String uri = null;
		if (myJson.has("code") && !myJson.isNull("code")
				&& myJson.getString("code").equals("OK")) {
			JSONArray myArray = myJson.getJSONArray("response");
			if (myArray != null) {
				for (int i = 0; i < myArray.length(); i++) {
					JSONObject myObject = myArray.getJSONObject(i);
					if (myObject != null && myObject.has("name")
							&& myObject.getString("name") != null) {
						uri = myObject.getString("uri");
					}
				}
			}
		}

		ModelAndView modelAndView = new ModelAndView("museumDetails");

		if (uri != null) {
			Model model = null;
			try {
				model = DBPediaClient.retrieveRDFModelForResource(uri,
						servletContext);
				MuseumRDF museumRDF = new MuseumRDF(model);
				modelAndView.addObject("museumRDF", museumRDF);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Something bad happened");
		}

		return modelAndView;
	}
}
