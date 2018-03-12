package services;

import configs.AppConfig;
import configs.TestDataConfig;

import jpa.Task;
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
public class TaskPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Inject
    private TaskPersistenceService taskPersist;

    @Test
    public void emptyListTest() {
        final List<Task> list = taskPersist.fetchAllTasks();
        assertTrue("List should be empty", list.isEmpty());
    }

    @Test
    public void saveValidTaskTest() {
        assertTrue("List should be empty", taskPersist.fetchAllTasks().isEmpty());

        final Task t = new Task();
        t.setContents("contents");
        assertNull("ID should not be set before persist is called", t.getId());
        taskPersist.saveTask(t);
        assertNotNull("ID should be set after persist is called", t.getId());
        final List<Task> list = taskPersist.fetchAllTasks();
        assertTrue("List should have one element", list.size() == 1);
    }

    @Test
    public void saveBlankTaskTest() {
        try {
            final Task t = new Task();
            taskPersist.saveTask(t);
            fail("This should have failed since contents is blank");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void saveNonBlankIdTaskTest() {
        try {
            final Task t = new Task();
            t.setContents("contents");
            t.setId(1L);
            taskPersist.saveTask(t);
            fail("This should have failed since id is not blank");
        } catch (PersistenceException ignored) {
        }
    }
}