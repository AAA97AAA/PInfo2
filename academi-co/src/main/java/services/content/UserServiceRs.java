package services.content;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dom.content.User;

/**
 * JAX RS annotated class for REST services, using UserService (@Inject annotation)
 * 
 * @author petrbinko
 *
 */

@Path("/users")
public class UserServiceRs {
	
	@Inject
	private UserService userService;
	
	@GET
	@Path("/hello")
	@Produces("application/json")
	public String printHello() {
		return "Hello !";
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public User getUser(@PathParam("id") long id) {
		return userService.getUser(id);
	}
	
	
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public void addUser(User user) {
		userService.addUser(user);
	}
	
	
	@POST
	@Path("/modify")
	@Consumes("application/json")
	@Produces("application/json")
	public void modifyUser(User oldUser, User newUser) {
		userService.modifyUser(oldUser, newUser);
	}
	
	
}
