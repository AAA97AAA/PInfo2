package services.content;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonView;

import dom.content.Comment;
import dom.content.Post;
import dom.content.QuestionThread;
import dom.content.Vote;
import services.utility.View;

/**
 * JAX RS annotated class for REST services, using QuestionThreadService (@Inject annotation)
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */

@Path("/posts")
public class PostServiceRs {
	
	@Inject
	private PostService postService;
	
	
	/**
	 * Get question thread by ID
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.PostParentCentered.class)
	public Response getQuestionThread(@PathParam("id") long id) {
		QuestionThread questionThread = postService.getQuestionThread(id);
		if (questionThread == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(questionThread).build();
	}
	
	// TODO Security : registered users alone can post new Threads
	/**
	 * Add question thread to database
	 * @param questionThread
	 * @param uriInfo
	 * @return thread created or 404 if its attributes do not exist
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.PostNew.class)
	public Response addQuestionThread(QuestionThread questionThread, @Context UriInfo uriInfo) {
		QuestionThread result;
		try {
			result = postService.addPost(questionThread);
		} catch (IllegalArgumentException e) {
			return Response.status(422).build(); // Illegal topics for given subject
		}
		if (result == null) {
			return Response.status(Status.NOT_FOUND).build(); // Parent not found
		}
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
	
	/**
	 * Add a comment to the thread given by "id".
	 * 
	 * @param threadId
	 * @param comment
	 * @param uriInfo
	 * @return comment created or 404 if the author or thread does not exist
	 */
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.PostNew.class)
	public Response addComment(@PathParam("id") long threadId, Comment comment, @Context UriInfo uriInfo) {
		Comment result = postService.addPost(threadId, comment);
		if (result == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
	
	/**
	 * Sets the "banned" state of a post to the requested value in the payload.
	 * 
	 * @param id
	 * @param post (only contains the "banned" value)
	 * @return a post with only the new value of "banned"
	 */
	@PUT
	@Path("/{id}/ban")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.PostState.class)
	public Response setBanOnPost(@PathParam("id") long id, Post post) {
		Post result = postService.setBan(id, post.isBanned());
		if (result == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(result).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.PostVote.class)
	public Response vote(@PathParam("id") long id, Vote vote) {
		Post result = postService.vote(id, vote);
		return Response.ok(result).build();
	}
}
