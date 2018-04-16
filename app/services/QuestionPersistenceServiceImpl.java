package services;

import jpa.Question;

import java.util.List;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class QuestionPersistenceServiceImpl implements QuestionPersistenceService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void saveQuestion(Question question) {
		if (question.getQuestion() == null) {
			throw new IllegalArgumentException("Contents must not be blank");
		}
		
		em.persist(question);
	}

	@Override
	public List<Question> fetchAllQuestions() {
		return em.createQuery("from Question", Question.class).getResultList();
	}

}
