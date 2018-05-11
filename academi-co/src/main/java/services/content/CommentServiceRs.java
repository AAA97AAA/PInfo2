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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
	private CommentService service;
	
	@GET
	@Path("/getById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComment(@PathParam("id") long id) {
		
//		Comment comment = null;
		
		// Try if comment exists in database
//		try {
		Comment	comment = service.getComment(id);
			
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(Comment comment, @Context UriInfo uriInfo) throws URISyntaxException {
		
		return Response.created(new URI(uriInfo.getPath() + comment.getId())).entity(service.addComment(comment)).build();
		
	}
	
}
