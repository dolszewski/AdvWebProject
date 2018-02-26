package services;

import configs.AppConfig;
import configs.TestDataConfig;

import jpa.Task;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

@ContextConfiguration(classes = { AppConfig.class, TestDataConfig.class })
public class TaskPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Inject
	private TaskPersistenceService taskPersist;

	@Test
	public void saveTaskTest() {
		Task t = new Task();
		taskPersist.saveTask(t);
	}
}