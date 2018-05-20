package services.content;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dom.content.Comment;
import dom.content.ConcretePost;
import dom.content.ConcreteQuestionThread;
import dom.content.ConcreteUser;
import dom.content.Post;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.Vote;

/**
 * 
 * Service class implementing services for question threads
 * All methods should start with EntityManager.getTransaction().begin();
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */

@Default
@Stateless
public class ConcretePostService implements PostService {
	
	
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
		return entityManager.find(ConcreteQuestionThread.class, id);
	}
	
	@Override
	public QuestionThread addPost(QuestionThread questionThread) {
		entityManager.persist(questionThread);
		return questionThread;
	}

	@Override
	public Comment addPost(Comment comment) {
		entityManager.persist(comment);
		return comment;
	}

	@Override
	public Post setBan(long id, boolean banned) {
		Post post = entityManager.find(ConcretePost.class, id);
		if (post == null) {
			return null;
		}
		post.setBanned(banned);
		return post;
	}

	@Override
	public Post vote(long id, Vote vote) {
		
		// Fetch the target post
		Post post = entityManager.find(ConcretePost.class, id);
		if (post == null) {
			return null;
		}
		
		// Fetch upvoter/downvoter
		User voter = entityManager.find(ConcreteUser.class, vote.getVoterId());
		if (voter == null) {
			return null;
		}
		
		// Apply vote
		if (vote.isUp()) { // upvote
			if (post.getUpvoters().contains(voter)) {
				post.removeUpvoter(voter);
				post.getDownvoters().size(); // dirty way to force the set to load
			} else {
				post.addUpvoter(voter);
				if (post.getDownvoters().contains(voter)) {
					post.removeDownvoter(voter);
				}
			}
		} else { // downvote
			if (post.getDownvoters().contains(voter)) {
				post.removeDownvoter(voter);
				post.getUpvoters().size(); // dirty way to force the set to load
			} else {
				post.addDownvoter(voter);
				if (post.getUpvoters().contains(voter)) {
					post.removeUpvoter(voter);
				}
			}
		}
		
		return post;
	}
}
