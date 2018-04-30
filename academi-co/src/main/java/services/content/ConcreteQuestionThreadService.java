package services.content;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	/******************* Constructors ********************/
//
//	public ConcreteQuestionThreadService(EntityManager em) {
//		entityManager = em;
//	}
//	
	
	
	/****************** Constructors ********************/
	
//	public ConcreteQuestionThreadService() {
//		emf = Persistence.createEntityManagerFactory("academi-co");
//	}
//	
//	protected ConcreteQuestionThreadService(EntityManagerFactory emf) throws IllegalArgumentException {
//		if (emf == null) {
//			throw new IllegalArgumentException("Entity manager factory cannot be null");
//		}
//		this.emf = emf;
//	}


	
	/********************* Services **********************/
	
	/**
	 * Fetches a thread from the database (by id) and returns it
	 */
	@Override
	public QuestionThread getQuestionThread(long id) {
				
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
	
	@Override
	public QuestionThread addQuestionThread(QuestionThread questionThread) {
		
		entityManager.persist(questionThread);
		
		return questionThread;
	}
	
}
