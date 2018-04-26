package services.content;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
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
	
	@PersistenceUnit(unitName="academi-co")
	private EntityManagerFactory emf;
	
	
	/*************** Constructors *****************/
	
	public ConcreteUserService () {
	}
	
	protected ConcreteUserService(EntityManagerFactory emf) {
		
		this.emf = emf;
		
	}
	
	
	@Override
	public User getUser(long id) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
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
			
			// Return of single result. If we want a list of results, we use getResultList
			return query.getSingleResult();
		}
		
		finally {
			if (entityManager != null) entityManager.close();
		}
		
	}
	
	/**
	 * Adding new user to database
	 */
	@Override
	public void addUser(User user) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
		}
		
		finally {
			if (entityManager != null) entityManager.close();
		}
	}
	
	/**
	 * Service to modify User in database
	 */
	@Override
	public void modifyUser(User oldUser, User newUser) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(newUser);
			entityManager.remove(oldUser);
			entityManager.getTransaction().commit();
		}
		finally {
			if(entityManager != null) entityManager.close();
		}
			
	}
	
	
	/****************** Getters / Setters *************/
	
	@Override
	public EntityManagerFactory getEmf() {
		return emf;
	}
	
	
}
