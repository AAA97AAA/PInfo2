package services.documentsManager;

import java.io.Serializable;

import javax.ejb.Local;

import dom.documentsManager.Document;

@Local
public interface ProfilePictureService extends Serializable {
	
	public Document getProfilePicture(long id);
	
	public void modifyProfilePicture(Document oldProfilePicture, Document newProfilePicture);

}
