package services.content;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
	public Comment getComment(@PathParam("id") long id) {
		
		return commentService.getComment(id);
		
	}
	
	
	// TODO Security : registered users alone can post new Threads
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public void addComent(Comment comment) {
		
		commentService.addComment(comment);
		
	}
}
