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

import dom.content.Comment;

/**
 * JAX RS annotated class for REST services, using CommentService (@Inject annotation)
 * 
 * @author petrbinko
 *
 */

@Path("/comments")
public class CommentServiceRs {

	@Inject
	private CommentService commentService;
	
	@GET
	@Path("/getById/{id}")
	@Produces("application/json")
	public Response getComment(@PathParam("id") long id) {
		
//		Comment comment = null;
		
		// Try if comment exists in database
//		try {
		Comment	comment = commentService.getComment(id);
			
//		}
		// Catching no result exception and return response
//		catch (NoResultException e) {
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}
		
		return Response.ok().entity(comment).build();
		
	}
	
	
	// TODO Security : registered users alone can post new Threads
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addComent(Comment comment) throws URISyntaxException {
		
		commentService.addComment(comment);
		return Response.status(201).contentLocation(new URI("comments/getById/" + comment.getId())).build();
		
	}
	
}
