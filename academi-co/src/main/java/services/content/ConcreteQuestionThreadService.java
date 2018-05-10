package services.content;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.content.ConcreteQuestionThread;
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
		CriteriaQuery<ConcreteQuestionThread> criteriaQuery = criteriaBuilder.createQuery(ConcreteQuestionThread.class);
		
		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
		Root<ConcreteQuestionThread> variableRoot = criteriaQuery.from(ConcreteQuestionThread.class);
		
		// Condition statement -> Where
		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("id"), id));
		
		// Creating typed query
		TypedQuery<ConcreteQuestionThread> query = entityManager.createQuery(criteriaQuery);
		
		// Return of single result. If we want a list of results, we use getResultList
		return query.getSingleResult();
	}
	
	@Override
	public QuestionThread addQuestionThread(QuestionThread questionThread) {
		
		System.out.println(entityManager);
		entityManager.persist(questionThread);
		
		return questionThread;
	}
	
}
