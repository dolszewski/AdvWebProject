package services;

import jpa.Question;

import java.util.List;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.Application;

@Named
public class QuestionPersistenceServiceImpl implements QuestionPersistenceService {

	@PersistenceContext
	private EntityManager em;

	/** Saves a question into the database
	 * @parameter question This is a question that is bound from the form
	 * @return Integer This returns the ID for the question that was saved
	 */
	@Transactional
	@Override
	public Integer saveQuestion(Question question) {
		if (question == null) {
			throw new IllegalArgumentException("Contents must not be blank");

		}
		if (question.getQuestion() == null) {
			throw new IllegalArgumentException("Contents must not be blank");
		}
		
		String q = question.getQuestion();
		if (!q.endsWith("?")) {
			throw new IllegalArgumentException("Must end with question mark");

		}
		
		if (q.length() <5) {
			throw new IllegalArgumentException("Must be longer than 5 characters");

		}
		if (q.length() >=32) {
			throw new IllegalArgumentException("Must be shorter than 32 characters");

		}
		List<Question> questions = em.createQuery("from Question where UPPER(question) = :question", Question.class)
				.setParameter("question", q.toUpperCase()).getResultList();
		if ( !questions.isEmpty()) {
			throw new IllegalArgumentException("This question has already been asked");
		}
		em.persist(question);
		return question.getId();
	}

	/** Fetches a list of all of the questions
	 * @return List<Question> Returns every question stored in the database
	 */
	@Override
	public List<Question> fetchAllQuestions() {
		return em.createQuery("from Question", Question.class).getResultList();
	}

	/** Grabs one question from the database that matches the inputted string
	 * @parameter question This is a string that we wish to search in the database
	 * @return Question this is null if the string is null or there is no question.
	 *         Returns the question matching the parameter
	 */
	@Override
	public Question fetchQuestion(String question) {
		if (question == null) {
			return null;
		}
		
		if (!question.endsWith("?")) {
			throw new IllegalArgumentException("Must end with question mark");

		}
		
		if (question.length() <5) {
			throw new IllegalArgumentException("Must be longer than 5 characters");

		}
		if (question.length() >=32) {
			throw new IllegalArgumentException("Must be shorter than 32 characters");

		}
		List<Question> questions = em.createQuery("from Question where UPPER(question) = :question", Question.class)
				.setParameter("question", question.toUpperCase()).getResultList();
		if (questions.size() != 1) {
			// logger.warn("There were no questions that exist.");

			return null;
		}
		return questions.get(0);
	}

	/** Checks to see if a question exists in the database
	 * @parameter question A string that will be checked in the database on whether
	 *            or not it exists
	 * @return boolean If the question exists in the database it returns true.
	 *         Otherwise it will return false
	 */
	@Override
	public boolean questionExists(String question) {
		if (question == null) {
			return false;
		}
		
		if (!question.endsWith("?")) {
			return false;
		}
		
		if (question.length() <5) {
			return false;

		}
		if (question.length() >=32) {
			return false;

		}
		List<Question> questions = em.createQuery("from Question where UPPER(question) = :question", Question.class)
				.setParameter("question", question.toUpperCase()).getResultList();
		if (questions.size() != 1) {
			// logger.warn("There were no questions that exist.");
			return false;
		}

		return true;
	}

}
