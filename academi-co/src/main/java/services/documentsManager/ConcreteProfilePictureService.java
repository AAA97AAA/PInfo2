package services.documentsManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.documentsManager.Document;

public class ConcreteProfilePictureService implements ProfilePictureService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = 6703953886153042459L;
	
	@PersistenceUnit(unitName="academi-co1")
	private EntityManagerFactory emf;

	@Override
	public Document getProfilePicture(long id) {
		
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
	public void modifyProfilePicture(Document oldProfilePicture, Document newProfilePicture) {

		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(newProfilePicture);
			entityManager.remove(oldProfilePicture);
			entityManager.getTransaction().commit();
			
	
		}
		finally {
			if (entityManager != null) entityManager.close();
		}
	}
	
	
	

}
