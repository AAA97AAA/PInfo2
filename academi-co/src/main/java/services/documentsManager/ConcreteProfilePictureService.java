package services.documentsManager;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;

/**
 * Profile picture service implementation
 * 
 * @author petrbinko
 *
 */
@Default
@Stateless
public class ConcreteProfilePictureService implements ProfilePictureService {
	
	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = 6703953886153042459L;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	/******************** Services **********************/

	@Override
	public Document getProfilePicture(long id) {
		
		// Creating criteria builder to create a criteria query
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		// Criteria query of return type QuestionThread
		CriteriaQuery<ConcreteDocument> criteriaQuery = criteriaBuilder.createQuery(ConcreteDocument.class);
		
		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
		Root<ConcreteDocument> variableRoot = criteriaQuery.from(ConcreteDocument.class);
		
		// Condition statement -> Where
		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("id"), id));
		
		// Creating typed query
		TypedQuery<ConcreteDocument> query = entityManager.createQuery(criteriaQuery);
		
		// Return of single result. If we want a list of results, we use getResultList
		return query.getSingleResult();
	}

	@Override
	public Document modifyProfilePicture(long oldPictureId, Document newProfilePicture) {

		Document oldPicture = getProfilePicture(oldPictureId);
		DocumentFactory.replaceDocument(oldPicture, newProfilePicture);

		return oldPicture;
	}

}
