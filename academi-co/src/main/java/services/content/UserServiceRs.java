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

import dom.content.User;
import dom.documentsManager.Document;

/**
 * JAX RS annotated class for REST services, using UserService (@Inject annotation)
 * @author petrbinko
 *
 */

@Path("/users")
public class UserServiceRs {
	
	@Inject
	private UserService userService;
		
	/**
	 * Get a user by his ID
	 * @param id
	 * @return
	 */
	@GET
	@Path("/getById/{id}")
	@Produces("application/json")
	public Response getUser(@PathParam("id") long id) {
		
//		User user = null;
		
		// Try if user exists in database
//		try { 
		User user = userService.getUser(id);	
//		} 
		// Catching no result exception and return response
//		catch (NoResultException e) {
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}
		
		return Response.ok().entity(user).build();
	}
	
	
	/**
	 * Add user to database
	 * @param user
	 * @throws URISyntaxException 
	 */
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addUser(User user) throws URISyntaxException {
		
		userService.addUser(user);
		return Response.status(201).contentLocation(new URI("users/getById/" + user.getId())).build();
	}
	
	/**
	 * Modify picture
	 * @param id
	 * @param newUser
	 */
	@POST
	@Path("/modifyUser")
	@Consumes("application/json")
	@Produces("application/json")
	public void modifyUser(long id, User newUser) {
		
		userService.modifyUser(id, newUser);
	}
	
	/**
	 * Modify profile picture
	 * @param id
	 * @param newProfilePicture
	 */
	@POST
	@Path("/modifyProfilePicture")
	@Consumes("application/json")
	@Produces("application/json")
	public void modifyUserProfilePicture(long id, Document newProfilePicture) {
		
		userService.modifyUserProfilePicture(id, newProfilePicture);
		
	}
	
	
}
