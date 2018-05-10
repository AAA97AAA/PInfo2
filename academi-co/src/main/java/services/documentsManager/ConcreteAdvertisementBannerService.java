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

/**
 * Service for finding/adding/removing advertisement banners in the DB
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */
@Default
@Stateless
public class ConcreteAdvertisementBannerService implements AdvertisementBannerService {
	
	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = 4673084995448831858L;
	
	@PersistenceContext
	public EntityManager entityManager;
	
	
	/******************** Services **********************/

	/**
	 * Finds the element attached to the given id in the DB
	 */
	@Override
	public Document getAdvertisementBanner(long id) {
		
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

	/**
	 * Persists its argument
	 */
	@Override
	public Document addAdvertisementBanner(Document advertisementBanner) {
		entityManager.persist(advertisementBanner);
		return advertisementBanner;
	}

	/**
	 * Finds the element attached to the given id in DB and removes it
	 */
	@Override
	public void removeAdvertisementBanner(long id) {
		Document advertisementBanner = getAdvertisementBanner(id);
		entityManager.remove(advertisementBanner);
	}
	
}
