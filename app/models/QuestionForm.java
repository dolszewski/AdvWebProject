package models;

import play.data.validation.Constraints.Required;

public class QuestionForm {
	
	@Required
	private String question;
	
	public String getQuestions() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	
}