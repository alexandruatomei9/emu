package ro.emu.client.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.emu.client.utils.Request;

@Controller
@RequestMapping("/welcome")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		String resp = "blabla";
		try {
			Map<String, String> params = new HashMap<String,String>();
			params.put("resourceUri", "http://dbpedia.org/resource/Whitechapel_Gallery");
			resp = Request.sendGet("/museums/getRdfForMuseum", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("message", resp);
		return "hello";

	}
}