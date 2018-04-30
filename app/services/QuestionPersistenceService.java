package services;

import java.util.List;

import jpa.Question;

public interface QuestionPersistenceService {

    Integer saveQuestion(Question q);

    List<Question> fetchAllQuestions();

	Question fetchQuestion(String question);

	boolean questionExists(String question);
    
   

}
