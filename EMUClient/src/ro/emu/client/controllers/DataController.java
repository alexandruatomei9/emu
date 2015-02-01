package ro.emu.client.controllers;

import javax.servlet.ServletContext;

import org.apache.jena.atlas.json.JsonBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.emu.client.dbpedia.DBPediaClient;
import ro.emu.client.rdfmodels.PersonRDF;
import ro.emu.client.rdfmodels.WorkRDF;
import ro.emu.client.utils.Pair;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

@Controller
@RequestMapping("/data")
public class DataController {
	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/work", method = RequestMethod.GET)
	@ResponseBody
	public String getWork(
			@RequestParam(value = "workURI", required = true) String workURI) {
		JSONObject resourceJSON = new JSONObject();
		try {
			Model workModel = DBPediaClient.retrieveRDFModelForResource(
					workURI, servletContext);
			if (workModel != null) {
				WorkRDF workRDF = new WorkRDF(workModel);
				JSONObject workJSON = new JSONObject();
				addObjectToJson(workJSON, workRDF.getAbstract(), "abstract");

				addObjectToJson(workJSON, workRDF.getName(), "name");

				addObjectToJson(workJSON, workRDF.thumbnail(), "thumbnail");

				addObjectToJson(workJSON, workRDF.getWikiPageURL(),
						"wiki_page_url");
				Pair<String, Resource> authorPair = workRDF.author();
				if (authorPair != null && authorPair.isValid()
						&& authorPair.getSecond().getURI() != null) {
					Model authorModel = DBPediaClient
							.retrieveRDFModelForResource(authorPair.getSecond()
									.getURI(), servletContext);
					PersonRDF personRDF = new PersonRDF(authorModel);
					JSONObject authorObject = new JSONObject();
					addObjectToJson(authorObject, personRDF.getAbstract(),
							"abstract");
					addObjectToJson(authorObject, personRDF.getName(), "name");
					addObjectToJson(authorObject, personRDF.thumbnail(),
							"thumbnail");
					addObjectToJson(authorObject, personRDF.getWikiPageURL(),
							"wiki_page_url");
					addObjectToJson(authorObject, personRDF.getBirthDate(),
							"birth_date");
					addObjectToJson(authorObject, personRDF.getDeathDate(),
							"death_date");
					workJSON.put("author", authorObject);
				}
				resourceJSON.put(workRDF.getResourceName(), workJSON);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceJSON.toString();
	}

	@RequestMapping(value = "/author", method = RequestMethod.GET)
	@ResponseBody
	public String getAuthor(
			@RequestParam(value = "authorURI", required = true) String authorURI) {
		JsonBuilder jsonBuilder = new JsonBuilder();

		return jsonBuilder.build().toString();
	}

	private void addObjectToJson(JSONObject object, Pair<String, String> pair,
			String jsonKey) {
		if (pair != null && pair.isValid()) {
			object.put(jsonKey, new JSONObject().put("prefix", pair.getFirst())
					.put("value", pair.getSecond()));
		}
	}
}
