package services;

import jpa.Task;

import java.util.List;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class TaskPersistenceServiceImpl implements TaskPersistenceService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void saveTask(Task task) {
		if (task.getContents() == null) {
			throw new IllegalArgumentException("Contents must not be blank");
		}
		
		em.persist(task);
	}

	@Override
	public List<Task> fetchAllTasks() {
		return em.createQuery("from Task", Task.class).getResultList();
	}

}
