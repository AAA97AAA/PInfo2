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

import dom.content.ConcreteUser;
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
	private UserService service;
		
	/**
	 * Get a user by his ID
	 * @param id
	 * @return
	 */
	@GET
	@Path("/getById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") long id) {
		
//		User user = null;
		
		// Try if user exists in database
//		try { 
		return service.getUser(id);	
//		} 
		// Catching no result exception and return response
//		catch (NoResultException e) {
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}
		
		//return Response.ok().entity(user).build();
	}
	
	
	/**
	 * Add user to database
	 * @param user
	 * @throws URISyntaxException 
	 */
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(ConcreteUser user, @Context UriInfo uriInfo) throws URISyntaxException {
		
		return Response.created(new URI(uriInfo.getPath() + user.getId())).entity(service.addUser(user)).build();
	}
	
	/**
	 * Modify picture
	 * @param id
	 * @param newUser
	 */
	@POST
	@Path("/modifyUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void modifyUser(long id, ConcreteUser newUser) {
		
		service.modifyUser(id, newUser);
	}
	
	/**
	 * Modify profile picture
	 * @param id
	 * @param newProfilePicture
	 */
	@POST
	@Path("/modifyProfilePicture")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void modifyUserProfilePicture(long id, Document newProfilePicture) {
		
		service.modifyUserProfilePicture(id, newProfilePicture);
		
	}
	
	
}
