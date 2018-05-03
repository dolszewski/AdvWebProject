package services;

import java.util.List;

import jpa.Question;

public interface QuestionPersistenceService {
	/** Saves a question into the database
	 * @parameter question This is a question that is bound from the form
	 * @return Integer This returns the ID for the question that was saved
	 */
    Integer saveQuestion(Question q);
	/** Fetches a list of all of the questions
	 * @return List<Question> Returns every question stored in the database
	 */
    List<Question> fetchAllQuestions();
	/** Grabs one question from the database that matches the inputted string
	 * @parameter question This is a string that we wish to search in the database
	 * @return Question this is null if the string is null or there is no question.
	 *         Returns the question matching the parameter
	 */
	Question fetchQuestion(String question);
	/** Checks to see if a question exists in the database
	 * @parameter question A string that will be checked in the database on whether
	 *            or not it exists
	 * @return boolean If the question exists in the database it returns true.
	 *         Otherwise it will return false
	 */
	boolean questionExists(String question);
    
   

}
