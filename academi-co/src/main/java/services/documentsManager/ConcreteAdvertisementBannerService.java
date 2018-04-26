package services.documentsManager;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.documentsManager.Document;

@Stateless
public class ConcreteAdvertisementBannerService implements AdvertisementBannerService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = 4673084995448831858L;
	
	@PersistenceUnit(unitName="academi-co1")
	EntityManagerFactory emf;

	@Override
	public Document getAdvertisementBanner(long id) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Document> c = qb.createQuery(Document.class);
			
			Root<Document> variableRoot = c.from(Document.class);
			c.where(qb.equal(variableRoot.get("ID"), id));
			
			TypedQuery<Document> query = entityManager.createQuery(c);
			
			return query.getSingleResult();
			
		}
		
		finally {
			if (entityManager != null) entityManager.close();
		}
		
	}

	@Override
	public void addAdvertisementBanner(Document advertisementBanner) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(advertisementBanner);
			entityManager.getTransaction().commit();
			
		}
		
		finally {
			if (entityManager != null) entityManager.close();
		}
		
	}

	@Override
	public void removeAdvertisementBanner(Document advertisementBanner) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(advertisementBanner);
			entityManager.getTransaction().commit();
			
		}
		
		finally {
			if (entityManager != null) entityManager.close();
		}		
	}
	
	

}
