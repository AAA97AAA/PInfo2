package services.documentsManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
	@Path("/getById/{id}")
	@Produces("application/json")
	public Document getProfilePicture(long id) {
		
		return profilePictureService.getProfilePicture(id);
		
	}
	
	@PUT
	@Path("/modify")
	@Consumes("application/json")
	@Produces("application/json")
	public void modifyProfilePicture(Document oldProfilePicture, Document newProfilePicture) {
		
		profilePictureService.modifyProfilePicture(oldProfilePicture, newProfilePicture);
		
	}
	
}
