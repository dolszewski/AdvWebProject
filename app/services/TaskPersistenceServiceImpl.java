package services;

import models.Task;

import play.db.jpa.JPA;

import java.util.List;
import javax.inject.Named;


@Named
public class TaskPersistenceServiceImpl implements TaskPersistenceService {

  @Override
  public void saveTask(Task task) {
      JPA.em().persist(task);
  }

  @Override
  public List<Task> fetchAllTasks() {
    return JPA.em().createQuery("from Task", Task.class).getResultList();
  }

}
