package ro.emu.client.controllers;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;

import org.apache.jena.atlas.json.JsonBuilder;
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

	private static JsonBuilderFactory factory = Json.createBuilderFactory(null);

	@RequestMapping(value = "/work", method = RequestMethod.GET)
	@ResponseBody
	public String getWork(
			@RequestParam(value = "workURI", required = true) String workURI) {
		JsonObjectBuilder builder = factory.createObjectBuilder();

		try {
			Model workModel = DBPediaClient.retrieveRDFModelForResource(
					workURI, servletContext);
			if (workModel != null) {
				WorkRDF workRDF = new WorkRDF(workModel);
				addObjectToJson(builder, workRDF.getAbstract(), "abstract");

				addObjectToJson(builder, workRDF.getName(), "name");

				addObjectToJson(builder, workRDF.thumbnail(), "thumbnail");

				addObjectToJson(builder, workRDF.getWikiPageURL(),
						"wiki_page_url");
				Pair<String, Resource> authorPair = workRDF.author();
				if (authorPair != null && authorPair.isValid()
						&& authorPair.getSecond().getURI() != null) {
					Model authorModel = DBPediaClient
							.retrieveRDFModelForResource(authorPair.getSecond()
									.getURI(), servletContext);
					PersonRDF personRDF = new PersonRDF(authorModel);
					JsonObjectBuilder authorBuilder = factory
							.createObjectBuilder();
					addObjectToJson(authorBuilder, personRDF.getAbstract(),
							"abstract");
					addObjectToJson(authorBuilder, personRDF.getName(), "name");
					addObjectToJson(authorBuilder, personRDF.thumbnail(),
							"thumbnail");
					addObjectToJson(authorBuilder, personRDF.getWikiPageURL(),
							"wiki_page_url");
					builder.add("author", authorBuilder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.build().toString();
	}

	@RequestMapping(value = "/author", method = RequestMethod.GET)
	@ResponseBody
	public String getAuthor(
			@RequestParam(value = "authorURI", required = true) String authorURI) {
		JsonBuilder jsonBuilder = new JsonBuilder();

		return jsonBuilder.build().toString();
	}

	private void addObjectToJson(JsonObjectBuilder builder,
			Pair<String, String> pair, String jsonKey) {
		if (pair != null && pair.isValid()) {
			builder.add(
					jsonKey,
					factory.createObjectBuilder()
							.add("prefix", pair.getFirst())
							.add("value", pair.getSecond()));
		}
	}
}
