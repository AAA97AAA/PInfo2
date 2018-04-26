package services.content;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dom.content.QuestionThread;

/**
 * JAX RS annotated class for REST services, using QuestionThreadService (@Inject annotation)
 * 
 * @author petrbinko
 *
 */

@Path("/questionThreads")
public class QuestionThreadServiceRs {
	
	@Inject
	private QuestionThreadService questionThreadService;
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public QuestionThread getQuestionThread(@PathParam("id") long id) {
		
		return questionThreadService.getQuestionThread(id);
		
	}
	
	
	// TODO Security : registered users alone can post new Threads
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public void addQuestionThread(QuestionThread questionThread) {
		
		questionThreadService.addQuestionThread(questionThread);
	}
	
	
	
	
}
