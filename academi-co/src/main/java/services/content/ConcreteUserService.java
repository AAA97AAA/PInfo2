package services.content;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.content.User;

/**
 * Service class implementing services for users
 * 
 * @author petrbinko
 *
 */
@Stateless
public class ConcreteUserService implements UserService {


	// Serial version (auto-generated)
	private static final long serialVersionUID = -7292125416040361069L;
	
	
	public EntityManagerFactory emf;
	
	
	/*************** Constructors *****************/
	
	public ConcreteUserService () {
		emf = Persistence.createEntityManagerFactory("academi-co");
	}
	
	protected ConcreteUserService(EntityManagerFactory emf) {
		
		this.emf = emf;
		
	}
	
	
	@Override
	public User getUser(long id) {
		
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		
		// Creating criteria builder to create a criteria query
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		// Criteria query of return type QuestionThread
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		
		
		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
		Root<User> variableRoot = criteriaQuery.from(User.class);
		
		// Condition statement -> Where
		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("ID"), id));
		
		
		// Creating typed query
		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		
		entityManager.close();

		// Return of single result. If we want a list of results, we use getResultList
		return query.getSingleResult();
		
	}
	
	/**
	 * Adding new user to database
	 */
	@Override
	public void addUser(User user) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	/**
	 * Service to modify User in database
	 */
	@Override
	public void modifyUser(User oldUser, User newUser) {
		
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		
		oldUser.setBio(newUser.getBio());
		oldUser.setCanBeModerator(newUser.isCanBeModerator());
		oldUser.setEmail(newUser.getEmail());
		oldUser.setPassword(newUser.getPassword());
		oldUser.setProfilePicture(newUser.getProfilePicture());
		oldUser.setType(newUser.getType());
		oldUser.setUsername(newUser.getUsername());
		entityManager.persist(oldUser);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	
	/****************** Getters / Setters *************/
	

	public EntityManagerFactory getEmf() {
		return emf;
	}
	
	
}
