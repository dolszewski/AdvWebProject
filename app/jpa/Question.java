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
	
	public Integer getId(){ 
		return id;
		
	}
	public String getQuestion() {
		return question;
	}
	public void setId(Integer newId) {
		id = newId;
	}
	public void setQuestion(String newQuestion) {
		question =newQuestion;
	}
	public String getAnswer() {
		return answer;
	}
}
