package services.content;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.content.ConcreteUser;
import dom.content.Post;
import dom.content.User;
import dom.content.UserFactory;
import services.documentsManager.DocumentService;

/**
 * Service class implementing services for users
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */
@Default
@Stateless
public class ConcreteUserService implements UserService {

	
	/******************* Attributes **********************/
	
	// Serial version (auto-generated)
	private static final long serialVersionUID = -7292125416040361069L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private DocumentService documentService;
		
	
	
	/******************** Services **********************/
	
	@Override
	public User getUser(long id) {
		User user = entityManager.find(ConcreteUser.class, id);
		user.getPosts().size();
		user.getFollowedThreads().size();
		return user;
	}
	
	@Override
	public User getUser(String username) {

		// Creating criteria builder to create a criteria query
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		// Criteria query of return type QuestionThread
		CriteriaQuery<ConcreteUser> criteriaQuery = criteriaBuilder.createQuery(ConcreteUser.class);
		
		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
		Root<ConcreteUser> variableRoot = criteriaQuery.from(ConcreteUser.class);
		
		// Condition statement -> Where
		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("username"), username));
		
		// Creating typed query
		TypedQuery<ConcreteUser> query = entityManager.createQuery(criteriaQuery);
		
		// Return of single result. If we want a list of results, we use getResultList
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Adding new user to database
	 */
	@Override
	public User addUser(User user) {
		User builtUser = UserFactory.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getType());
		entityManager.persist(builtUser);
		return builtUser;
	}
	
	/**
	 * Service to modify User in database
	 */
	@Override
	public User modifyUser(long id, User newUser) {
		
		User oldUser = getUser(id);
		
		oldUser.setBio(newUser.getBio());
		oldUser.setCanBeModerator(newUser.isCanBeModerator());
		oldUser.setPassword(newUser.getPassword());
		documentService.modifyProfilePicture(oldUser.getProfilePicture().getId(), newUser.getProfilePicture());
		oldUser.setType(newUser.getType());
		oldUser.setUsername(newUser.getUsername());
		
		return oldUser;
	}

	@Override
	public List<Post> getUserPosts(long id, String order, int from, int length) {
		
		// Fetch the target user and fail if it does not exist
		User user = getUser(id);
		if (user == null) {
			return null;
		}
		
		// Fetch the user's posts in given order
		TypedQuery<Post> query;
		if (order == "byDate") {
			query = entityManager.createNamedQuery("Post.fromAuthorByDate", Post.class);
		} else if (order == "byScore") {
			query = entityManager.createNamedQuery("Post.fromAuthorByScore", Post.class);
		} else {
			throw new IllegalArgumentException("Unrecognized order " + order);
		}
		query.setParameter("author", user).setFirstResult(from);
		if (length > 0) {
			query.setMaxResults(length);
		}
		return query.getResultList();
	}
}
