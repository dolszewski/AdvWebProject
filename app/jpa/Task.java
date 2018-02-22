package jpa;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "task_table")
public class Task {
	@Id
	@GeneratedValue
	private Integer id;
	@play.data.validation.Constraints.Required
	@Column(name = "contents_col", nullable = false, length = 32, unique = true)
	private String contents;
	@Column(name = "pass_hash")
	private String passHash;
	
	public Integer getId(){ 
		return id;
		
	}
	public String getContents() {
		return contents;
	}
	public void setId(Integer newId) {
		id = newId;
	}
	public void setContents(String newContents) {
		contents =newContents;
	}
}
