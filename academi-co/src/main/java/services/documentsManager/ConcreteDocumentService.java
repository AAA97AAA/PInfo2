package services.documentsManager;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;

/**
 * Profile picture service implementation
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */
@Default
@Stateless
public class ConcreteDocumentService implements DocumentService {
	
	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = 6703953886153042459L;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	/******************** Services **********************/

	@Override
	public Document getDocument(long id) {
		return entityManager.find(ConcreteDocument.class, id);
	}

	@Override
	public Document modifyProfilePicture(long oldPictureId, Document newProfilePicture) {

		Document oldPicture = getDocument(oldPictureId);
		DocumentFactory.replaceDocument(oldPicture, newProfilePicture);

		return oldPicture;
	}

}
