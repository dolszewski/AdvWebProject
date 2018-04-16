package services;

import configs.AppConfig;
import configs.TestDataConfig;

import jpa.Question;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

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
        final List<Question> list = questionPersist.fetchAllQuestions();
        assertTrue("List should be empty", list.isEmpty());
    }

    @Test
    public void saveValidTaskTest() {
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
    public void saveBlankTaskTest() {
        try {
            final Question t = new Question();
            questionPersist.saveQuestion(t);
            fail("This should have failed since contents is blank");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void saveNonBlankIdTaskTest() {
        try {
            final Question t = new Question();
            t.setQuestion("question");
            t.setId(1L);
            questionPersist.saveQuestion(t);
            fail("This should have failed since id is not blank");
        } catch (PersistenceException ignored) {
        }
    }
}