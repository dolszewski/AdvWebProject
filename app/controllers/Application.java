package controllers;

import play.*;
import play.mvc.*;
import java.util.List;
import views.html.*;
import jpa.Task;
import jpa.Question;
import models.QuestionForm;
import models.TaskForm;
import play.db.jpa.JPA;

import services.TaskPersistenceService;
import services.QuestionPersistenceService;

import views.html.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Application extends Controller {
/*
	@Inject
	private TaskPersistenceService taskPersist;
	
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    public Result index() {
        return ok(index.render("Hello World!", play.data.Form.form(TaskForm.class)));
    }
    
    public Result addTask() {
    		logger.trace("I made it to line 1 of addTask");
    		
    		play.data.Form<TaskForm> form = play.data.Form.form(TaskForm.class).bindFromRequest();
    		if(form.hasErrors()) {
    			return badRequest(index.render("Hello World!", form));
    		}
    		
	   Task task = new Task();
	   task.setContents(form.get().getContents());
	    taskPersist.saveTask(task);

	    	return redirect(routes.Application.index());
    		
    }
    public Result getTasks() {
    		List<Task> tasks = taskPersist.fetchAllTasks();
    		return ok(play.libs.Json.toJson(tasks));
    }
    */
	
	@Inject
	private QuestionPersistenceService questionPersist;
	
	private static final Logger logger =  LoggerFactory.getLogger(Application.class);
	
	public Result index() {
		return ok(index.render("Common Sense!", play.data.Form.form(QuestionForm.class)));
		
	}
	
	public Result addQuestion() {
		logger.trace("I made it to line 1 of addQuestion");
		play.data.Form<QuestionForm> form = play.data.Form.form(QuestionForm.class).bindFromRequest();
		if(form.hasErrors()) {
			return badRequest(index.render("Common Sense!", form));
			
		}
		Question question = new Question();
		question.setQuestion(form.get().getQuestions());
		questionPersist.saveQuestion(question);
		return redirect(routes.Application.index());
		
	}
	public Result getQuestions() {
		List<Question> questions = questionPersist.fetchAllQuestions();
		return ok(play.libs.Json.toJson(questions));
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

