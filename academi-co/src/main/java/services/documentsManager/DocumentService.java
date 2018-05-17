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
public interface DocumentService extends Serializable {
	
	public Document getDocument(long id);
	
	public Document modifyProfilePicture(long oldPictureId, Document newProfilePicture);

}
