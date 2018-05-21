package services.content;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dom.content.Comment;
import dom.content.ConcretePost;
import dom.content.ConcreteQuestionThread;
import dom.content.ConcreteUser;
import dom.content.Post;
import dom.content.PostFactory;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.Vote;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import services.tags.TagService;

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
	
	@Inject
	UserService userService;
	
	@Inject
	TagService tagService;

	
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
		
		// Fetch (and detach) the thread's attributes
		User author = userService.getUser(questionThread.getAuthor().getId());
		MainTag subject = tagService.getMainTag(questionThread.getSubject().getId());
		Tag language = tagService.getLanguageTag(questionThread.getLanguage().getId());
		entityManager.detach(author);
		entityManager.detach(subject);
		entityManager.detach(language);
		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>(subject.getChildren());
		topics.keySet().retainAll(questionThread.getTopics().keySet());
		
		// Control attributes' existence and validity
		if (author == null || subject == null || language == null) {
			return null;
		}
		if (topics.size() < questionThread.getTopics().size()) {
			throw new IllegalArgumentException("Wrong topics for the given subject"); // topics were wrong => semantic error
		}
		
		// Generate full entity to be stored
		QuestionThread entity = PostFactory.createQuestionThread(author, questionThread.getContent(),
						questionThread.getTitle(), subject, language, topics);
		
		// Store and return
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public Comment addPost(long parentId, Comment comment) {
		
		// Try to fetch the parent thread
		QuestionThread thread = getQuestionThread(parentId);
		User author = userService.getUser(comment.getAuthor().getId());
		if (thread == null || author == null) {
			return null;
		}
		entityManager.detach(thread);
		entityManager.detach(thread);
		
		// Construct the full entity
		Comment entity = PostFactory.createComment(author, comment.getContent(), thread);
		
		// Store and return
		entityManager.persist(entity);
		return entity;
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
