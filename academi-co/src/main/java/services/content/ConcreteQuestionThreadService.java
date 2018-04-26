package services.content;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.content.QuestionThread;

/**
 * 
 * Service class implementing services for question threads
 * All methods should start with EntityManager.getTransaction().begin();
 * 
 * @author petrbinko
 *
 */

@Stateless
public class ConcreteQuestionThreadService implements QuestionThreadService {
	
	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = -5808948100662379670L;
	
	@PersistenceUnit(unitName="academi-co")
	private EntityManagerFactory emf;
	
	
	
	/****************** Constructors ********************/
	
	public ConcreteQuestionThreadService() {}
	
	protected ConcreteQuestionThreadService(EntityManagerFactory emf) {
		this.emf = emf;
	}


	
	/******************** Services **********************/
	
	@Override
	public QuestionThread getQuestionThread(long id) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			
			// Creating criteria builder to create a criteria query
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// Criteria query of return type QuestionThread
			CriteriaQuery<QuestionThread> criteriaQuery = criteriaBuilder.createQuery(QuestionThread.class);
			
			// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
			Root<QuestionThread> variableRoot = criteriaQuery.from(QuestionThread.class);
			
			// Condition statement -> Where
			criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("ID"), id));
			
			// Creating typed query
			TypedQuery<QuestionThread> query = entityManager.createQuery(criteriaQuery);
			
			// Return of single result. If we want a list of results, we use getResultList
			return query.getSingleResult();
		}
		// Block to close entityManager
		finally {
			if(entityManager != null) entityManager.close();
		}
	}
	
	@Override
	public void addQuestionThread(QuestionThread questionThread) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(questionThread);
			entityManager.getTransaction().commit();
		}
		finally {
			if (entityManager != null) entityManager.close();
		}
		
	}
	

	
	/****************** Getters / Setters *************/
	
	@Override
	public EntityManagerFactory getEmf() {
		return emf;
	}
	

}
