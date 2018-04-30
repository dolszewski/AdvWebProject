package models;

import java.util.Random;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.MinLength;

public class QuestionForm {

	@Required(message = "Question is required!")
	@MaxLength(32)
	@MinLength(5)
	@Pattern(value = "^.*\\?$", message = "Needs to end in ?")
	private String question;
	private String answer;
	
	/**
	 * 
	 * @return The question data
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * 
	 * @return Answered data
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * 
	 * @param question The question that is to be set up
	 */
	public void setQuestion(String question) {
		
		this.question = question;
	}
	
	/**
	 * 
	 * @return "Yes" or "No" that is randomly generated
	 */
	public String setAnswer() {
		Random myRand = new Random();
		int ans = myRand.nextInt(3);
		if(ans == 0) {
			this.answer = "Yes";
		} else {
			this.answer = "No";
		}
		return answer;
	}
	
	
}