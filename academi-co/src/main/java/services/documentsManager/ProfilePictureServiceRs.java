package services.documentsManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dom.documentsManager.Document;

/**
 * JAX RS annotated class for REST services, using ProfilePictureService (@Inject annotation)
 * 
 * @author petrbinko
 *
 */
@Path("/profilePictures")
public class ProfilePictureServiceRs {
	
	@Inject
	private ProfilePictureService profilePictureService;
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfilePicture(@PathParam("id") long id) {
		
		Document profilePicture = profilePictureService.getProfilePicture(id);
		
		return Response.ok(profilePicture).build();
		
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void modifyProfilePicture(Document oldProfilePicture, Document newProfilePicture) {
		// TODO Not done yet !
		
		profilePictureService.modifyProfilePicture(oldProfilePicture, newProfilePicture);
		
	}
	
}
