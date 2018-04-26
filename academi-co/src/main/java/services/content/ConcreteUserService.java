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
	public EntityManagerFactory getEmf() {
		return emf;
	}
	
	
	
	@Override
	public User getUser(long id) {
		
		EntityManager entityManager = emf.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> c = qb.createQuery(User.class);
			
//			System.out.println(c);
			
			Root<User> variableRoot = c.from(User.class);
			c.where(qb.equal(variableRoot.get("ID"), id));
			
			TypedQuery<User> query = entityManager.createQuery(c);
			
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
	
}
