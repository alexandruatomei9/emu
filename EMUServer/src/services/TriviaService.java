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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import models.responses.Answer;
import models.responses.Museum;
import models.responses.Question;
import models.responses.Response;
import utils.Code;
import dbpedia.DBPediaClient;

@Path("/trivia")
public class TriviaService {
	@Context
	javax.servlet.ServletContext servletContext;

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

		InputStream is = servletContext
				.getResourceAsStream("/WEB-INF/categories.properties");
		Properties prop = new Properties();
		prop.load(is);
		String[] categories = prop.getProperty("categories").split(",");
		ArrayList<String> cats = new ArrayList<String>();
		for (String category : categories) {
			cats.add(category);
		}

		int j = 0;
		int listSize = cats.size();
		while (j < listSize && questions.size() < 10) {
			int index = anyItem(cats.size());
			String cat = cats.get(index);
			ArrayList<Question> questionList = createQuestion(cat);
			questions.addAll(questionList);
			cats.remove(cat);
			j++;
		}

		for (Question q : questions) {
			System.out.println("------------");
			System.out.println(q.getText());
			for (Answer a : q.getAnswers()) {
				System.out.println(a.getValue() + " : " + a.isCorrectAnswer());
			}
		}

		return questions;
	}

	private ArrayList<Question> createQuestion(String category)
			throws Exception {
		InputStream is = servletContext
				.getResourceAsStream("/WEB-INF/categories.properties");
		Properties prop = new Properties();
		ArrayList<Question> questions = new ArrayList<Question>();
		Question question;
		ArrayList<String> uriList;
		String correctUri;
		Answer correctAnswer;
		List<Answer> answers;
		prop.load(is);
		String[] values = prop.getProperty(category).split(",");
		ArrayList<String> options = new ArrayList<String>();

		for (String value : values) {
			options.add(value);
		}

		int index;
		String correct;

		switch (category) {

		case "country":
			for (int j = 0; j < 3; j++) {
				correctAnswer = new Answer();
				answers = new ArrayList<Answer>();
				question = new Question();
				index = anyItem(options.size());
				correct = options.get(index);
				List<Museum> museumsInCountry = DBPediaClient
						.retrieveMuseumsInCountry(correct);
				question.setText(museumsInCountry.get(0).getName()
						+ " will be found in :");
				correctAnswer.setId(1);
				correctAnswer.setValue(correct);
				correctAnswer.setCorrectAnswer(true);
				answers.add(correctAnswer);
				options.remove(correct);

				for (int i = 0; i < 3; i++) {
					Answer answer = new Answer();
					String incorrect = options.get(anyItem(options.size()));
					answer.setId(i + 2);
					answer.setValue(incorrect);
					options.remove(incorrect);
					answers.add(answer);
				}
				Collections.shuffle(answers);
				question.setAnswers(answers);
				questions.add(question);
			}
			break;

		case "museum": {
			String[] uris = prop.getProperty("uri").split(",");
			uriList = new ArrayList<String>();

			for (String uri : uris) {
				uriList.add(uri);
			}
			for (int j = 0; j < 3; j++) {
				correctAnswer = new Answer();
				answers = new ArrayList<Answer>();
				question = new Question();
				index = anyItem(options.size());
				correct = options.get(index);

				correctUri = uriList.get(index);

				String countryForMuseums = DBPediaClient
						.retrieveCountryForMuseum(correctUri);
				uriList.remove(correctUri);

				question.setText("Which of these museums is in "
						+ countryForMuseums + "?");
				correctAnswer.setId(1);
				correctAnswer.setValue(correct);
				correctAnswer.setCorrectAnswer(true);
				answers.add(correctAnswer);
				options.remove(correct);

				for (int i = 0; i < 3; i++) {
					Answer answer = new Answer();
					int indexIncorrect = anyItem(options.size());
					String incorrect = options.get(indexIncorrect);
					answer.setId(i + 2);
					answer.setValue(incorrect);
					options.remove(incorrect);
					uriList.remove(indexIncorrect);
					answers.add(answer);
				}

				Collections.shuffle(answers);
				question.setAnswers(answers);
				questions.add(question);
			}
		}
			break;
		case "visitorsMuseum": {
			String[] visitorsUri = prop.getProperty("visitorsUri").split(",");
			uriList = new ArrayList<String>();
			for (String uri : visitorsUri) {
				uriList.add(uri);
			}

			for (int j = 0; j < 2; j++) {
				correctAnswer = new Answer();
				answers = new ArrayList<Answer>();
				question = new Question();
				index = anyItem(options.size());
				correct = options.get(index);

				correctUri = uriList.get(index);

				String visitorsForMuseum = DBPediaClient
						.retriveNumberOfVisitorsMuseum(correctUri);
				uriList.remove(correctUri);

				question.setText("Which of these numbers represents the visitors of the  "
						+ correct + "?");
				correctAnswer.setId(1);
				correctAnswer.setValue(visitorsForMuseum);
				correctAnswer.setCorrectAnswer(true);
				answers.add(correctAnswer);
				options.remove(index);

				for (int i = 0; i < 3; i++) {
					Answer answer = new Answer();

					String incorrectNumber = visitorsForMuseum;
					answer.setId(i + 2);
					int number = Integer.parseInt(incorrectNumber);
					if (i == 1)
						number += 100509180;
					else if (i == 2) {
						number -= 3060901;
						if (number < 0)
							number += 203891004;
					} else
						number += 2003091004;
					answer.setValue(Integer.toString(number));
					answers.add(answer);
				}

				Collections.shuffle(answers);
				question.setAnswers(answers);
				questions.add(question);
			}
		}
			break;
		case "work": {
			String[] uris = prop.getProperty("workUri").split(",");
			uriList = new ArrayList<String>();

			for (String uri : uris) {
				uriList.add(uri);
			}
			for (int j = 0; j < 2; j++) {
				correctAnswer = new Answer();
				answers = new ArrayList<Answer>();
				question = new Question();
				index = anyItem(options.size());
				correct = options.get(index);

				correctUri = uriList.get(index);
				String museumForWork = DBPediaClient
						.retrieveMuseumForWork(correctUri);
				uriList.remove(correctUri);

				question.setText("Which of these works is in " + museumForWork
						+ "?");
				correctAnswer.setId(1);
				correctAnswer.setValue(correct);
				correctAnswer.setCorrectAnswer(true);
				answers.add(correctAnswer);
				options.remove(correct);

				for (int i = 0; i < 3; i++) {
					Answer answer = new Answer();
					int indexIncorrect = anyItem(options.size());
					String incorrect = options.get(indexIncorrect);
					answer.setId(i + 2);
					answer.setValue(incorrect);
					options.remove(indexIncorrect);
					uriList.remove(indexIncorrect);
					answers.add(answer);
				}

				Collections.shuffle(answers);
				question.setAnswers(answers);
				questions.add(question);
			}
		}
			break;
		default:
			break;
		}

		return questions;
	}

	public int anyItem(int size) {
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(size);
		return index;
	}
}
