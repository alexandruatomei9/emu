package services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.responses.Answer;
import models.responses.Museum;
import models.responses.Question;
import models.responses.Response;
import utils.Code;
import dbpedia.DBPediaClient;

@Path("/trivia")
public class TriviaService {

	@GET
	@Path("/getQuiz")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuiz() {
		List<Question> questions = new ArrayList<Question>();
		Response response = new Response();
		try {
			questions.addAll(generateQuiz());
		} catch (Exception e) {
			response.setCode(Code.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		if (!questions.isEmpty()) {
			response.setCode(Code.OK);
			response.setResponse(questions);
		}

		return response;
	}

	private ArrayList<Question> generateQuiz() throws Exception {
	    ArrayList<Question> questions = new ArrayList<Question>();
	    
	    InputStream is = TriviaService.class
				.getResourceAsStream("categories.properties");
		Properties prop = new Properties();
		prop.load(is);
		String[] categories = prop.getProperty("categories").split(",");
	    ArrayList<String> cats = new ArrayList<String>();
		for(String category : categories){
			cats.add(category);
		}
	    
	    for(int i=0;i<10;i++){
	    	int index=anyItem(cats.size());
	    	String cat = cats.get(index);
	    	Question question = createQuestion(cat);
	    	questions.add(question);
	    	cats.remove(cat);
	    }
	    
	    return questions;
	} 

	private Question createQuestion(String category) throws Exception {
		InputStream is = TriviaService.class
				.getResourceAsStream("categories.properties");
		Properties prop = new Properties();
		Question question = new Question();
		
		Answer correctAnswer = new Answer();
		List<Answer> answers = new ArrayList<Answer>();
		prop.load(is);
		String[] values = prop.getProperty(category).split(",");
		ArrayList<String> options = new ArrayList<String>();
		
		for (String value : values) {
			options.add(value);
		}
		
		int index = anyItem(options.size());
		String correct = options.get(index);
		
		switch (category) {
		
		case "country":
			List<Museum> museumsInCountry = DBPediaClient
					.retrieveMuseumsInCountry(correct);
			question.setText(museumsInCountry.get(0) + " will be found in :");
			correctAnswer.setId(1);
			correctAnswer.setValue(correct);
			answers.add(correctAnswer);
			options.remove(correct);
			
			for(int i = 0; i<3; i++){
				Answer answer = new Answer();
				String incorrect = options.get(anyItem(options.size()));			
				answer.setId(i+2);
				answer.setValue(incorrect);
				options.remove(incorrect);
				answers.add(answer);
			}
			Collections.shuffle(answers);
			question.setAnswers(answers);
			break;
			
		case "museum": 
			
			String[] uris = prop.getProperty("uri").split(",");
			ArrayList<String>uriList = new ArrayList<String>();	
			for (String uri : uris) {
				uriList.add(uri);
			}
			
			String correctUri=uriList.get(index);
			
			String countryForMuseums = DBPediaClient.retrieveCountryForMuseum(correctUri);
			uriList.remove(correctUri);
			
			question.setText("Which of these museums is in "+ countryForMuseums + "?");
			correctAnswer.setId(1);
			correctAnswer.setValue(correct);
			answers.add(correctAnswer);
			options.remove(correct);
			
			for(int i = 0; i<3; i++){
				Answer answer = new Answer();
				int indexIncorrect=anyItem(options.size());
				String incorrect = options.get(indexIncorrect);
				answer.setId(i+2);
				answer.setValue(incorrect);
				options.remove(incorrect);
				uriList.remove(indexIncorrect);
				answers.add(answer);
			}
			
			Collections.shuffle(answers);
			question.setAnswers(answers);
			break;
		default:
			break;
		}

		return question;
	}

	public int anyItem(int size) {
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(size);		
		return index;
	}}
