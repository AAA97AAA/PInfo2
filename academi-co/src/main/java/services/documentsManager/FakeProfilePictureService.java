package services.documentsManager;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

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
		byte[] data = new byte[] {-119, 20, 50, 1};
		return DocumentFactory.createDocument("picture.png", data);
	}

	@Override
	public Document modifyProfilePicture(Document oldProfilePicture, Document newProfilePicture) {
		// TODO Not implemented yet !
		return null;
	}

}
