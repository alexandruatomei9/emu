package ro.emu.client.models;

import java.util.List;

public class QuizQuestion {
		private int id;
		private String text;
		private List<QuizAnswer> answers;
		
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public List<QuizAnswer> getAnswers() {
			return answers;
		}
		public void setAnswers(List<QuizAnswer> string) {
			this.answers = string;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		
	}
