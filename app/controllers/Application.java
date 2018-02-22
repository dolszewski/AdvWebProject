package controllers;

import play.*;
import play.mvc.*;
import java.util.List;
import views.html.*;
import models.Task;
import play.db.jpa.JPA;

import services.TaskPersistenceService;

import views.html.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class Application extends Controller {

	@Inject
	private TaskPersistenceService taskPersist;
	
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    public Result index() {
        return ok(index.render("Hello World!", play.data.Form.form(models.Task.class)));
    }
    
    @Transactional
    public Result addTask() {
    		logger.info("Hey Nate I Did IT!");
    		
    		play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
    		if(form.hasErrors()) {
    			return badRequest(index.render("Hello World!", form));
    		}
    		
	    	models.Task task = form.get();
	    taskPersist.saveTask(task);

	    	return redirect(routes.Application.index());
    		
    }
    @javax.transaction.Transactional
    public Result getTasks() {
    		List<Task> tasks = taskPersist.fetchAllTasks();
    		return ok(play.libs.Json.toJson(tasks));
    }
    
 /*
  *  @play.db.jpa.Transactional
    public static Result getTasks(Integer searchId) {
    		java.util.List<models.Task> tasks = play.db.jpa.JPA.em()
    				.createQuery("FROM Task t WHERE t.id = :id", models.Task.class)
    				.setParameter("id",searchId )
    				.getResultList();
   /* 		if(tasks == null || tasks.isEmpty()) {
    			return null;
    		} else {
    			return tasks.get(0);
    		}
    		return ok(play.libs.Json.toJson(tasks));
    }  
  */
}

