package services.documentsManager;

import java.io.Serializable;

import javax.ejb.Local;

import dom.documentsManager.Document;

/**
 * Profile picture service definition.
 * 
 * @author petrbinko
 * @author kaikoveritch (added annotation... and the comment...)
 *
 */
@Local
public interface ProfilePictureService extends Serializable {
	
	public Document getProfilePicture(long id);
	
	public Document modifyProfilePicture(Document oldProfilePicture, Document newProfilePicture);

}
