package services;

import java.util.List;

import jpa.Question;

public interface QuestionPersistenceService {

    void saveQuestion(Question q);

    List<Question> fetchAllQuestions();

}
