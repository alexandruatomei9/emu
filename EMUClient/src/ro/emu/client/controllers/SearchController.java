package ro.emu.client.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.emu.client.utils.Request;

@Controller
@RequestMapping("/search")
public class SearchController {
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String searchMuseums(HttpServletRequest request) {
		String prefix = request.getParameter("q");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("prefix", prefix);

		String response;
		String res = "";
		JSONObject myJson = null;
		try {
			response = Request.sendGet("/museums/searchMuseums", parameters);
			myJson = new JSONObject(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> result = new ArrayList<String>();
		
		if (myJson.has("code") && !myJson.isNull("code")
				&& myJson.getString("code").equals("OK")) {
			JSONArray myArray = myJson.getJSONArray("response");
			if (myArray != null) {
				for (int i = 0; i < myArray.length(); i++) {
					JSONObject myObject = myArray.getJSONObject(i);
					if (myObject != null && myObject.has("name")
							&& myObject.getString("name") != null) {
						result.add(myObject.getString("name"));
					}
				}
			}
		} else {
			res = "No museums found";
		}
		
		if(!result.isEmpty()){
			for(int i=0; i<result.size();i++){
				if(i==result.size()-1){
					res+=result.get(i);
				}else{
					res+=result.get(i)+",";
				}
			}
		}
		
		return res;
	}
}
