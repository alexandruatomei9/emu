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
	
	 List<QuizQuestion> quizQuestions;
	 Long score;
	 JSONArray items;
	 
	 @RequestMapping(method = RequestMethod.GET)
		public ModelAndView loading() {
			ModelAndView modelAndView = new ModelAndView("quizLoader");

			return modelAndView;
		}

	@RequestMapping(value="/generate", method = RequestMethod.GET)
	public ModelAndView quizRequest() {
		ModelAndView modelAndView = new ModelAndView("question");
		quizQuestions = new ArrayList<>();
		score = 0L;
		String resp = null;
		try {
			resp = Request.sendGet("/trivia/getQuiz", null,true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp != null) {
			JSONObject jsonResponse = new JSONObject(resp);
			if (!jsonResponse.getString("code").equals("OK")) {
				// server error
			} else {
			    items = jsonResponse.getJSONArray("response");
			    for(int j=0; j<items.length();j++){
			    	quizQuestions.add(parseResponse(j, items.getJSONObject(j)));
			    }
			}
		}

		modelAndView.addObject("question", quizQuestions.get(0));
		modelAndView.addObject("score",score);
		return modelAndView;
	}
	
	@RequestMapping(value="/question/", method = RequestMethod.GET)
	public ModelAndView sendQuiz(@RequestParam(value="id") String id, @RequestParam(value="answerId") String answer, 
			HttpServletRequest request) {
		Integer questionId = Integer.parseInt(id);
		if(answer!= null && !answer.equals("")){
			Integer answerId = Integer.parseInt(answer);
			verifiedAnswer(questionId,answerId);
		}
		ModelAndView model = null;
		if(questionId.equals(quizQuestions.size()-1)){
		    model = new ModelAndView("showResponses");
			model.addObject("score", score);
			score = 0L;
		}
		else{
			model = new ModelAndView("question");
			QuizQuestion question = quizQuestions.get(questionId+1);
			model.addObject("question", question);
			model.addObject("score", score);
			}
		return model;
	}
	
	private void verifiedAnswer(Integer questionId, Integer answerId){
		QuizQuestion question = quizQuestions.get(questionId);
		for(QuizAnswer answer : question.getAnswers()){
			if(answer.getId() == answerId){
				if(answer.isCorrectAnswer()){
					score = score + 10;
				}
			}
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
			return question;
		}
}
