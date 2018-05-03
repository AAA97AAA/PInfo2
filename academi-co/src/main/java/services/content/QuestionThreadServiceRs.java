package services.content;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
	
	/**
	 * Get question thread by ID
	 * @param id
	 * @return
	 */
	@GET
	@Path("/getById/{id}")
	@Produces("application/json")
	public Response getQuestionThread(@PathParam("id") long id) {
		
//		QuestionThread questionThread = null;
		
		// Try if question thread exists in database
//		try {
		QuestionThread questionThread = questionThreadService.getQuestionThread(id);
//		}
		// Catching no result exception and return response
//		catch (NoResultException e) {
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}
		
		return Response.ok().entity(questionThread).build();
		
	}
	
	
	// TODO Security : registered users alone can post new Threads
	/**
	 * Add question thread to database
	 * @param questionThread
	 * @return
	 * @throws URISyntaxException
	 */
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addQuestionThread(QuestionThread questionThread) throws URISyntaxException {
		
		questionThreadService.addQuestionThread(questionThread);
		return Response.status(201).contentLocation(new URI("questionThreads/getById/" + questionThread.getId())).build();
	}
	
	
	
	
}
