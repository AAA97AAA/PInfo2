package services.content;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.content.Comment;
import dom.content.ConcreteComment;
import dom.content.QuestionThread;

/**
 * Service class implementing services for comments
 * 
 * @author petrbinko
 *
 */

@Default
@Stateless
public class ConcreteCommentService implements CommentService {
	
	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = -1005497794725784917L;

	@PersistenceContext
	private EntityManager entityManager;

	
	/******************** Services ***********************/
	
	/**
	 * Get a comment from ID
	 */
	@Override
	public Comment getComment(long id) {
		
		// Creating criteria builder to create a criteria query
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				
		// Criteria query of return type QuestionThread
		CriteriaQuery<ConcreteComment> criteriaQuery = criteriaBuilder.createQuery(ConcreteComment.class);
		
		
		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
		Root<ConcreteComment> variableRoot = criteriaQuery.from(ConcreteComment.class);
		
		// Condition statement -> Where
		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("id"), id));
		
		
		// Creating typed query
		TypedQuery<ConcreteComment> query = entityManager.createQuery(criteriaQuery);
		
		// Return of single result. If we want a list of results, we use getResultList
		return query.getSingleResult();

	}
	
	/**
	 * Add a comment to a question
	 */
	@Override
	public Comment addComment(Comment comment) {
		
		entityManager.persist(comment);
		QuestionThread question = comment.getQuestion();
		question.addAnswer(comment);
		entityManager.persist(question);
		return comment;
		
	}
	

}
