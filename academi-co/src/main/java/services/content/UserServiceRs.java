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
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonView;

import dom.content.ConcreteUser;
import dom.content.User;
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
		return Response.ok(result).build();
	}
	
	
	/**
	 * Add user to database
	 * @param user
	 * @return response
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.UserNew.class)
	public Response addUser(ConcreteUser user, @Context UriInfo uriInfo) {
		User result = service.addUser(user);
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
	
	/**
	 * Modify user data
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
		User result = service.modifyUser(id, newUser);
		return Response.ok(result).build();
	}
}
