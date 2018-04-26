package services.content;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.content.Comment;
import dom.content.QuestionThread;

/**
 * Service class implementing services for comments
 * 
 * @author petrbinko
 *
 */

@Stateless
public class ConcreteCommentService implements CommentService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -1005497794725784917L;

	@PersistenceUnit(unitName="academi-co1")
	private EntityManagerFactory emf;
	

	@Override
	public Comment getComment(long id) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Comment> c = qb.createQuery(Comment.class);
			
			Root<Comment> variableRoot = c.from(Comment.class);
			c.where(qb.equal(variableRoot.get("ID"), id));
			
			TypedQuery<Comment> query = entityManager.createQuery(c);
			
			return query.getSingleResult();
	
		}
		finally {
			if (entityManager != null) entityManager.close();
		}
	}
	
	@Override
	public void addComment(Comment comment) {
		
		emf = Persistence.createEntityManagerFactory("academi-co");
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(comment);
			QuestionThread question = comment.getQuestion();
			question.addAnswer(comment);
			entityManager.persist(question);
			entityManager.getTransaction().commit();
		}
		finally {
			if (entityManager != null) entityManager.close();
		}
		
	}
	
	

}
