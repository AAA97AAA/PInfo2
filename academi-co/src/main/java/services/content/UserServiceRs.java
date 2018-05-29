package services.content;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonView;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import dom.content.ConcreteUser;
import dom.content.Post;
import dom.content.User;
import dom.content.UserType;
import services.utility.ErrorPayload;
import services.utility.View;

/**
 * JAX RS annotated class for REST services, using UserService (@Inject annotation)
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */

@Path("/users")
public class UserServiceRs {
	
	@Inject
	private UserService service;
	

	/**
	 * Get a user's session information from his username
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("/login/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.UserSession.class)
	public Response getUserForSession(@PathParam("username") String username) {
		User result = service.getUser(username);
		if (result == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(result).build();
	}
	
	/**
	 * Get a user's profile by his ID
	 * @param id
	 * @return response
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.UserProfile.class)
	public Response getUser(@PathParam("id") long id) {
		User result = service.getUser(id);
		if (result == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(result).build();
	}
	
	
	/**
	 * Add user to database (basic user version)
	 * @param user
	 * @return response
	 * @throws NoSuchAlgorithmException 
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.UserNew.class)
	public Response addUser(ConcreteUser user, @Context UriInfo uriInfo) throws NoSuchAlgorithmException {
		
		// only for basic users
		user.setType(UserType.REGISTERED.getStringVal());
		
		return addUserByAdministrator(user, uriInfo);
	}
	
	/**
	 * Add user to database (administrator-only version)
	 * @param user
	 * @return response
	 * @throws NoSuchAlgorithmException 
	 */
	@POST
	@Path("/administrator")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.UserNew.class)
	public Response addUserByAdministrator(ConcreteUser user, @Context UriInfo uriInfo) throws NoSuchAlgorithmException {
		
		// Attempt to store new user
		User result;
		try {
			result = service.addUser(user);
		} catch (Throwable e) {
			
			// Code 400 if constraint violation
			if (e.getClass().toString().equals(MySQLIntegrityConstraintViolationException.class.toString())) {
				String fault = isolateErrorMessage(e);
				return Response.status(Status.BAD_REQUEST).entity(new ErrorPayload(fault)).build();
			}
			
			// Else code 500
			return Response.serverError().build();
		}
		
		// If success give code 201 with new entity
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
	
	/**
	 * Modify user data (basic user version)
	 * @param id
	 * @param newUser
	 * @return response
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.UserModifiable.class)
	public Response modifyUser(@PathParam("id") long id, ConcreteUser newUser) {
		
		// basic user only
		newUser.setType(UserType.REGISTERED.getStringVal());
		
		return modifyUserByAdministrator(id, newUser);
	}
	
	/**
	 * Modify user data (basic user version)
	 * @param id
	 * @param newUser
	 * @return response
	 */
	@PUT
	@Path("/{id}/administrator")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.UserModifiable.class)
	public Response modifyUserByAdministrator(@PathParam("id") long id, ConcreteUser newUser) {
		
		// Attempt to modify user
		User result;
		try {
			result = service.modifyUser(id, newUser);
		} catch (Throwable e) {
			
			// Code 400 if constraint violation
			if (e.getClass().toString().equals(MySQLIntegrityConstraintViolationException.class.toString())) {
				String fault = isolateErrorMessage(e);
				return Response.status(Status.BAD_REQUEST).entity(new ErrorPayload(fault)).build();
			}
			
			// Else code 500
			return Response.serverError().build();
		}
		
		// If success give code 200 with new entity
		return Response.ok(result).build();
	}
	
	@GET
	@Path("/{id}/posts")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.PostBase.class)
	public Response getUserPosts(@PathParam("id") long id, @QueryParam("order") @DefaultValue("byDate") String order,
			@QueryParam("from") @DefaultValue("0") int from, @QueryParam("len") @DefaultValue("0") int length) {
		List<Post> posts = service.getUserPosts(id, order, from, length);
		if (posts == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(posts).build();
	}
	
	private String isolateErrorMessage(Throwable error) {
		Matcher match = Pattern.compile("for key '(.+)'").matcher(error.getMessage());
		if (match.find()) {
			return match.group(1);
		}
		return null;
	}
}
