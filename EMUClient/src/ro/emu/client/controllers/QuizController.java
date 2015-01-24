package ro.emu.client.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.emu.client.models.QuizAnswer;
import ro.emu.client.models.QuizQuestion;
import ro.emu.client.utils.Request;

@Controller
@RequestMapping("/quiz")
public class QuizController {
	
	List<QuizQuestion> quizQuestion = new ArrayList<>();
	JSONArray items;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView quizRequest() {
		ModelAndView modelAndView = new ModelAndView("quiz");
		
		String resp = null;
		try {
			//get 10 question from the api
			resp = Request.sendGet("/trivia/getQuiz", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		QuizQuestion question = new QuizQuestion();
		if (resp != null) {
			JSONObject jsonResponse = new JSONObject(resp);
			/*if (!jsonResponse.getString("code").equals("OK")) {
				// server error
			} else*/ {
			   // items = jsonResponse.getJSONArray("response");
			    question = parseResponse(0, "prima intrebare");
				/*for (int i = 1; i < items.length(); i++) {
					JSONObject item = items.getJSONObject(i);
					QuizQuestion anotherQuestion = parseResponse(item, i);
					quizQuestion.add(anotherQuestion);
				}*/
			}
		}

		modelAndView.addObject("question", question);
		return modelAndView;
	}
	
	@RequestMapping(value="/question", method = RequestMethod.GET)
	public ModelAndView sendQuiz(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		ModelAndView model = new ModelAndView("quiz");
		QuizQuestion question = parseResponse(id+1, "a doua intrebare");
		model.addObject("question", question);

		return model;
	}
	
	
	private QuizQuestion parseResponse( int id, String text){
			/*QuizQuestion question =  new QuizQuestion();
			question.setId(id);
			question.setText(item.getString("text"));
			JSONArray answers = item.getJSONArray("answers");
			List<QuizAnswer> quizAnswer = new ArrayList<>();
				for(int j = 0; j< answers.length(); j++){
					JSONObject it = answers.getJSONObject(j);
					QuizAnswer answer = new QuizAnswer();
					answer.setId(Integer.parseInt(it.getString("id")));
					answer.setValue(it.getString("value"));
				}
			question.setAnswers(quizAnswer);*/
		QuizQuestion question =  new QuizQuestion();
		question.setId(id);
		question.setText(text);
		
		List<QuizAnswer> quizAnswer = new ArrayList<>();
		QuizAnswer answer = new QuizAnswer();
		answer.setId(id);
		answer.setValue("primul raspuns");
		
		quizAnswer.add(answer);
		question.setAnswers(quizAnswer);
			return question;
		}
}
