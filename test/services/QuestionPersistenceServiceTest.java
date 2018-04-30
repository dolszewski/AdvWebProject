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
        t.setQuestion("question");
        assertNull("ID should not be set before persist is called", t.getId());
        questionPersist.saveQuestion(t);
        assertNotNull("ID should be set after persist is called", t.getId());
        final List<Question> list = questionPersist.fetchAllQuestions();
        assertTrue("List should have one element", list.size() == 1);
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
            t.setQuestion("question");
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
    			
    		}catch (PersistenceException ignored) {
    			
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
			
		}catch (PersistenceException ignored) {
			
		} 
    	}
    
   
   
    }
