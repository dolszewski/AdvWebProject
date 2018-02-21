package controllers;

import play.*;
import play.mvc.*;
import java.util.List;
import views.html.*;
import models.Task;
import play.db.jpa.JPA;

import services.TaskPersistenceService;
import services.TaskPersistenceServiceImpl;

import views.html.index;




public class Application extends Controller {

	private static final TaskPersistenceService taskPersist = new TaskPersistenceServiceImpl();

    public static Result index() {
        return ok(index.render("Hello World!", play.data.Form.form(models.Task.class)));
    }
    
    @play.db.jpa.Transactional
    public static Result addTask() {
    		play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
    		if(form.hasErrors()) {
    			return badRequest(index.render("Hello World!", form));
    		}
    		
	    	models.Task task = form.get();
	    taskPerist.saveTask(task);

	    	return redirect(routes.Application.index());
    		
    }
    @play.db.jpa.Transactional
    public static Result getTasks() {
        List<Task> tasks = JPA.em().createQuery("from Task", Task.class).getResultList();
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

