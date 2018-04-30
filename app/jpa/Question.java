package jpa;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "question_table")
public class Question {
	@Id
	@GeneratedValue
	private Integer id;
	@play.data.validation.Constraints.Required
	@Column(name = "question_col", nullable = false, length = 32, unique = true)
	private String question;
	@Column(name = "answer_col", length = 3)
	private String answer;
	@Column(name = "pass_hash")
	private String passHash;

	/**
	 * 
	 * @return the generated ID
	 */
	public Integer getId(){ 
		return id;
		
	}
	/**
	 * 
	 * @return The question that is set
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * 
	 * @param newId : Integer that is set
	 * 
	 */
	public void setId(Integer newId) {
		id = newId;
	}
	/**
	 * 
	 * @param newQuestion the new content for the question
	 */
	public void setQuestion(String newQuestion) {
		
		question =newQuestion.substring(0,1).toUpperCase() + newQuestion.substring(1).toLowerCase();
	}
	/**
	 * 
	 * @return the answer contens
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 
	 * @param newAnswer String for setting answer content
	 */
	public void setAnswer(String newAnswer) {
		answer = newAnswer;
	}
}
