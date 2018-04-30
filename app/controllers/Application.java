package controllers;

import play.*;
import play.mvc.*;
import java.util.List;
import java.util.Random;

import views.html.*;
import jpa.Question;
import models.QuestionForm;
import play.db.jpa.JPA;

import services.QuestionPersistenceService;

import views.html.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Application extends Controller {

	@Inject
	private QuestionPersistenceService questionPersist;

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	/**
	 * 
	 * @return Renders the form for the Common Sense app.
	 */
	public Result index() {
		return ok(index.render("Common Sense!", play.data.Form.form(QuestionForm.class)));

	}
	/**
	 * This method binds from the form the question, and then persists it to the database. If the question is already in there, it will 
	 * retrieve the question from the database.
	 * @return Redirects to the page that will display the question added 
	 */
	public Result addQuestion() {
		logger.info("I made it to line 1 of addQuestion");
		play.data.Form<QuestionForm> form = play.data.Form.form(QuestionForm.class).bindFromRequest();
		if (form.hasErrors()) {
			//flash(s: "danger", s1: "Please fill out a correct question");
			return badRequest(index.render("Common Sense!", form));

		}
		Question question = questionPersist.fetchQuestion(form.get().getQuestion());
		if (question == null) {
			question = new Question();
			question.setQuestion(form.get().getQuestion());
			question.setAnswer(form.get().setAnswer());
			questionPersist.saveQuestion(question);

		}
		if(question.getQuestion() == null) {
			logger.warn("Checking empty question");
			return badRequest(index.render("Common Sense!", form));
		}
		
		

		return redirect(routes.Application.showAnswer(question.getQuestion()));

	}
	/**
	 * 
	 * @param questionQ A string that is the question that will be searched for in the database
	 * @return This returns a webpage result 
	 */
	public Result showAnswer(String questionQ) {
		Question q = questionPersist.fetchQuestion(questionQ);
		return ok(question.render("Common Sense!", q));

	}

}
