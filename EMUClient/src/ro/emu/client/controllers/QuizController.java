package ro.emu.client.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ro.emu.client.models.QuizAnswer;
import ro.emu.client.models.QuizQuestion;
import ro.emu.client.utils.Request;

@Controller
@RequestMapping("/quiz")
public class QuizController {
	
	 List<QuizQuestion> quizQuestion = new ArrayList<>();
	 Long score = 0L;
	 JSONArray items;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView quizRequest() {
		ModelAndView modelAndView = new ModelAndView("quiz");
		
		String resp = null;
		try {
			resp = Request.sendGet("/trivia/getQuiz", null,true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		QuizQuestion question = new QuizQuestion();
		if (resp != null) {
			JSONObject jsonResponse = new JSONObject(resp);
			if (!jsonResponse.getString("code").equals("OK")) {
				// server error
			} else {
			    items = jsonResponse.getJSONArray("response");
			    question = parseResponse(0, items.getJSONObject(0));
				for (int i = 1; i < items.length(); i++) {
					JSONObject item = items.getJSONObject(i);
					QuizQuestion anotherQuestion = parseResponse(i, item);
					quizQuestion.add(anotherQuestion);
				}
			}
		}

		modelAndView.addObject("question", question);
		return modelAndView;
	}
	
	@RequestMapping(value="/question/", method = RequestMethod.GET)
	public ModelAndView sendQuiz(@RequestParam(value="id") String id, @RequestParam(value="answerId") String answer, 
			HttpServletRequest request) {
		Integer questionId = Integer.parseInt(id);
		Integer answerId = Integer.parseInt(answer);
		verifiedAnswer(questionId,answerId);
		ModelAndView model = null;
		if(questionId.equals(6)){
		    model = new ModelAndView("showResponses");
			model.addObject("score", score);
			score = 0L;
			
		}
		else{
			model = new ModelAndView("question");
			QuizQuestion question = parseResponse(questionId+1, items.getJSONObject(questionId+1));
			model.addObject("question", question);
			}

		return model;
	}
	
	private void verifiedAnswer(Integer questionId, Integer answerId){
		QuizQuestion question = new QuizQuestion();
		question = quizQuestion.get(questionId);
		QuizAnswer responseAnswer = null;
		for(QuizAnswer answer : question.getAnswers()){
			if(answer.getId() == answerId){
				responseAnswer = answer;
			}
		}
		if(responseAnswer.isCorrectAnswer()){
			score = score + 10;
			
		}
	}
	
	private QuizQuestion parseResponse( int id, JSONObject item){
			QuizQuestion question =  new QuizQuestion();
			question.setId(id);
			question.setText(item.getString("text"));
			JSONArray answers = item.getJSONArray("answers");
			List<QuizAnswer> quizAnswer = new ArrayList<>();
				for(int j = 0; j< answers.length(); j++){
					JSONObject it = answers.getJSONObject(j);
					QuizAnswer answer = new QuizAnswer();
					answer.setId(it.getInt("id")-1);
					answer.setValue(it.getString("value"));
					answer.setCorrectAnswer(it.getBoolean("correctAnswer"));
					quizAnswer.add(answer);
				}
			question.setAnswers(quizAnswer);
		quizQuestion.add(question);
			return question;
		}
}
