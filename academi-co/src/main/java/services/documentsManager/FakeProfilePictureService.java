package services.documentsManager;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import dom.content.UserFactory;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;

/**
 * Mock of the profile picture service for front-end use.
 * 
 * @author kaikoveritch
 *
 */
@Alternative
@Stateless
public class FakeProfilePictureService implements ProfilePictureService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -7094187279374862117L;
	

	@Override
	public Document getProfilePicture(long id) {
		Document picture = DocumentFactory.loadDocument(UserFactory.DEFAULT_PATH);
		return picture;
	}

	@Override
	public Document modifyProfilePicture(long oldPictureId, Document newProfilePicture) {
		return newProfilePicture;
	}

}
