package services;

import configs.AppConfig;
import configs.TestDataConfig;

import jpa.Question;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

@ContextConfiguration(classes = { AppConfig.class, TestDataConfig.class })
public class QuestionPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Inject
    private QuestionPersistenceService questionPersist;
    
    @Test
    public void emptyListTest() {
    		//logger.warn("Question Persistence Test 1");
        final List<Question> list = questionPersist.fetchAllQuestions();
        assertTrue("List should be empty", list.isEmpty());
    }

    @Test
    public void saveValidQuestion() {
        assertTrue("List should be empty", questionPersist.fetchAllQuestions().isEmpty());

        final Question t = new Question();
        t.setQuestion("question?");
        assertNull("ID should not be set before persist is called", t.getId());
        questionPersist.saveQuestion(t);
        assertNotNull("ID should be set after persist is called", t.getId());
        final List<Question> list = questionPersist.fetchAllQuestions();
        assertTrue("List should have one element", list.size() == 1);
    }

    @Test
    public void saveNonValidQuestion() {
        assertTrue("List should be empty", questionPersist.fetchAllQuestions().isEmpty());

        final Question t = new Question();
        t.setQuestion("question");
        assertNull("ID should not be set before persist is called", t.getId());
        try {
        questionPersist.saveQuestion(t);
        } catch (IllegalArgumentException ignored) {
        	
        }
        final List<Question> list = questionPersist.fetchAllQuestions();
        assertTrue("List should have no elements", list.size() == 0);
    }
    
    @Test
    public void saveBlankQuestionTest() {
        try {
            final Question t = new Question();
            questionPersist.saveQuestion(t);
            fail("This should have failed since contents is blank");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void saveNonBlankIdQuestionTest() {
        try {
            final Question t = new Question();
            t.setQuestion("question?");
            t.setId(1);
            questionPersist.saveQuestion(t);
            fail("This should have failed since id is not blank");
        } catch (PersistenceException ignored) {
        }
        
    }
  
    @Test
    public void saveTooLongQuestionTest() {
    		try {
    			final Question t = new Question();
    			t.setQuestion("This question is hopefully too long for us to be able to answer it conveniently?");
    			questionPersist.saveQuestion(t);
    			fail("This should have failed since the question is too long");
    			
    		}catch (IllegalArgumentException ignored) {
    			
    		}
    }
    
    @Test
    public void nullQuestionExistsTest() {
    		assertFalse(questionPersist.questionExists(""));
    }
    
    @Test
    public void addMultipleQuestions() {
    		Question q1 = new Question();
    		q1.setQuestion("What is life?");
    		questionPersist.saveQuestion(q1);
    		
    		Question q2 = new Question();
    		q2.setQuestion("What can I do?");
    		questionPersist.saveQuestion(q2);
    		
    		assertTrue(questionPersist.questionExists(q1.getQuestion()));

    		assertTrue(questionPersist.questionExists(q2.getQuestion()));
    		
    }
    
    @Test
    public void questionDoesNotExist() {
    		assertFalse(questionPersist.questionExists(">>>SDFLK:J:?"));
    }
    
    @Test
    public void nullQuestionData() {
    		assertNull(questionPersist.fetchQuestion(null));
    }
    
    @Test
    public void  saveSameQuestion() {
    	try {
    		Question q1 = new Question();
    		q1.setQuestion("What are we doing?");
    		questionPersist.saveQuestion(q1);
    		
    		Question q2 = new Question();
    		q2.setQuestion("What are we doing?");
    		questionPersist.saveQuestion(q2);
    		
    		
    		fail("This should fail as questions must be unique");
			
		}catch (IllegalArgumentException ignored) {
			
		} 
    	}
    
    @Test
    public void saveNonInitializedQuestion() {
    	try {

    		Question q2 = new Question();
    	
    		questionPersist.saveQuestion(q2);
    		
    		
    		fail("This should fail as questions must be initialized");
			
		}catch (IllegalArgumentException ignored) {
			
		} 
    }
    @Test
    public void saveNull() {
    	try {
		
	
		questionPersist.saveQuestion(null);
		
		
		fail("This should fail as questions must not be null");
		
	}catch (IllegalArgumentException ignored) {
		
	} 
    }
    
    @Test
    public void fetchAllQuestionsEmptyList() {
        assertTrue("List should be empty", questionPersist.fetchAllQuestions().isEmpty());
    }
    @Test
    public void fetchAllQuestionsWithOneQuestion() {
    		Question q1 = new Question();
    		q1.setQuestion("Am I alive?");
    		
    		questionPersist.saveQuestion(q1);
    		final List<Question> list = questionPersist.fetchAllQuestions();
        assertTrue("List should have one element", list.size() == 1);

    		
    }
    
 
    @Test
    public void fetchAllQuestionsWithTwoQuestions() {
    		Question q1 = new Question();
    		q1.setQuestion("Am I alive?");
    		questionPersist.saveQuestion(q1);
    		Question q2 = new Question();
    		q2.setQuestion("Am I dead?");
    		questionPersist.saveQuestion(q2);
    		final List<Question> list = questionPersist.fetchAllQuestions();
        assertTrue("List should have one element", list.size() == 2);

    		
    }
    
    @Test
    public void fetchValidQuestion() {
    		Question q1 = new Question();
    		q1.setQuestion("Can we hang out?");
    		questionPersist.saveQuestion(q1);
    		
    		assertTrue("Question returned should be the same", q1.equals(questionPersist.fetchQuestion(q1.getQuestion())));
    		
    }
    
    @Test
    public void fetchNullQuestion() {
    		assertNull("null questions shouldn't exist", questionPersist.fetchQuestion(null));
    	
    }
   
    @Test
    public void fetchEmptyQuestion() {
    		Question q1 = new Question();
    		assertNull("empty question shouldn't be found in the database", questionPersist.fetchQuestion(q1.getQuestion()));
    }
   
    @Test
    public void fetchNonValidQuestion() {
    		Question q1 = new Question(); 
    		q1.setQuestion("Help me!");
    		try {
    			questionPersist.fetchQuestion(q1.getQuestion());
    			
    		} catch (IllegalArgumentException ignored) {
    			
    		}
    }
    
    @Test
    public void fetchTooLongQuestion() {
    	try {
			final Question t = new Question();
			t.setQuestion("This question is hopefully too long for us to be able to answer it conveniently?");
			questionPersist.fetchQuestion(t.getQuestion());
			fail("This should have failed since the question is too long");
			
		}catch (IllegalArgumentException ignored) {
			
		}
    }
    @Test 
    public void fetchTooShortQuestion() {
     	try {
			final Question t = new Question();
			t.setQuestion(" ?");
			questionPersist.fetchQuestion(t.getQuestion());
			fail("This should have failed since the question is too long");
			
		}catch (IllegalArgumentException ignored) {
			
		}
    }
    @Test
    public void fetchMultipleQuestions() {
    		Question q1 = new Question();
    		q1.setQuestion("Are we fun?");
    		Question q2 = new Question();
    		q2.setQuestion("Is this cool?");
    		
    		questionPersist.saveQuestion(q1);
    		questionPersist.saveQuestion(q2);
    		
    		assertTrue("Question 1 should be fetched from the system.", q1.equals(questionPersist.fetchQuestion(q1.getQuestion())));
    		assertTrue("Question 2 should be fetched from the system.", q2.equals(questionPersist.fetchQuestion(q2.getQuestion())));

    		
    }
    
    @Test
    public void questionExistsValidQuestion() {
        assertTrue("List should be empty", questionPersist.fetchAllQuestions().isEmpty());
        Question q1 = new Question();
        q1.setQuestion("Can I help you?");
        questionPersist.saveQuestion(q1);
        assertTrue("This question should exist in the database", questionPersist.questionExists(q1.getQuestion()));
        
        
    }
    
    @Test
    public void questionExistsNonValidQuestin() {
    		assertFalse("This question shouldn't exist", questionPersist.questionExists("Help me!"));
    }
    @Test
    public void questionExistsTooLongQuestion() {
    		assertFalse("This question shouldn't exist", questionPersist.questionExists("This question is too long to reasonably answer anytime soon, isn't that right?"));
    }
    @Test
    public void questionExistsTooShortQuestion() {
		assertFalse("This question shouldn't exist", questionPersist.questionExists("This?"));
    }
    @Test
    public void questionExistsNullQuestion() {
		assertFalse("This question shouldn't exist", questionPersist.questionExists(null));
    }
    @Test
    public void questionExistsEmptyQuestion() {
    		Question q1 = new Question();
    		assertFalse("This question shouldn't exist", questionPersist.questionExists(q1.getQuestion()));

    }
    @Test
    public void questionExistsMultipleQuestions() {
    		Question q1 = new Question();
    		q1.setQuestion("What is this?");
    		
    		Question q2 = new Question();
    		q2.setQuestion("Can I help you?");
    		questionPersist.saveQuestion(q1);
    		questionPersist.saveQuestion(q2);

    		assertTrue("This question should exist", questionPersist.questionExists(q1.getQuestion()));
    		assertTrue("This question should exist", questionPersist.questionExists(q2.getQuestion()));

    }
    @Test
    public void questionExistsMultipleQuestionsNotValid() {
    		Question q1 = new Question();
    		q1.setQuestion("What is this?");
    		
    		Question q2 = new Question();
    		
    		questionPersist.saveQuestion(q1);

    		assertTrue("This question should exist", questionPersist.questionExists(q1.getQuestion()));
    		assertFalse("This question shouldn't exist", questionPersist.questionExists(q2.getQuestion()));

    }
    }
