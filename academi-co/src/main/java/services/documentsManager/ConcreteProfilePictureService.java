package services.documentsManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.documentsManager.Document;

public class ConcreteProfilePictureService implements ProfilePictureService {
	
	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = 6703953886153042459L;
	

	private EntityManagerFactory emf;
	
	
	
	/****************** Constructors ********************/

	public ConcreteProfilePictureService() {
		emf = Persistence.createEntityManagerFactory("academi-co");
	}
	
	public ConcreteProfilePictureService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	
	/******************** Services **********************/

	@Override
	public Document getProfilePicture(long id) {
		
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		
		// Creating criteria builder to create a criteria query
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		// Criteria query of return type QuestionThread
		CriteriaQuery<Document> criteriaQuery = criteriaBuilder.createQuery(Document.class);
		
		
		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
		Root<Document> variableRoot = criteriaQuery.from(Document.class);
		
		// Condition statement -> Where
		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("ID"), id));
		
		
		// Creating typed query
		TypedQuery<Document> query = entityManager.createQuery(criteriaQuery);
		
		entityManager.close();

		// Return of single result. If we want a list of results, we use getResultList
		return query.getSingleResult();
				
		
	}

	@Override
	public void modifyProfilePicture(Document oldProfilePicture, Document newProfilePicture) {

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.persist(newProfilePicture);
		entityManager.remove(oldProfilePicture);
		entityManager.getTransaction().commit();
		entityManager.close();
		
	}
	
	
	
	/****************** Getters / Setters *************/
	
	public EntityManagerFactory getEmf() {
		return emf;
	}
}
