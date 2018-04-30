//package services.content;
//
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//
//import dom.content.User;
//import dom.documentsManager.Document;
//import services.documentsManager.ConcreteProfilePictureService;
//import services.documentsManager.ProfilePictureService;
//
///**
// * Service class implementing services for users
// * 
// * @author petrbinko
// *
// */
//@Stateless
//public class ConcreteUserService implements UserService {
//
//	
//	/******************* Attributes **********************/
//	
//	// Serial version (auto-generated)
//	private static final long serialVersionUID = -7292125416040361069L;
//	
//	@PersistenceContext
//	public EntityManager entityManager;
//	
//	
////	/******************* Constructors ********************/
////
////	public ConcreteUserService(EntityManager em) {
////		entityManager = em;
////	}
//	
//	
//	
//	/******************** Services **********************/
//
//	@Override
//	public User getUser(long id) {
//
//		// Creating criteria builder to create a criteria query
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		
//		// Criteria query of return type QuestionThread
//		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
//		
//		
//		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
//		Root<User> variableRoot = criteriaQuery.from(User.class);
//		
//		// Condition statement -> Where
//		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("ID"), id));
//		
//		
//		// Creating typed query
//		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
//		
//		// Return of single result. If we want a list of results, we use getResultList
//		return query.getSingleResult();
//		
//	}
//	
//	/**
//	 * Adding new user to database
//	 */
//	@Override
//	public void addUser(User user) {
//		
//		entityManager.persist(user);
//	}
//	
//	/**
//	 * Service to modify User in database
//	 */
//	@Override
//	public void modifyUser(long id, User newUser) {
//		
//		User oldUser = getUser(id);
//		
//		oldUser.setBio(newUser.getBio());
//		oldUser.setCanBeModerator(newUser.isCanBeModerator());
//		oldUser.setEmail(newUser.getEmail());
//		oldUser.setPassword(newUser.getPassword());
//		oldUser.setProfilePicture(newUser.getProfilePicture());
//		oldUser.setType(newUser.getType());
//		oldUser.setUsername(newUser.getUsername());
//	}
//	
//	@Override
//	public void modifyUserProfilePicture(long id, Document newProfilePicture) {
//		
//		
//		ProfilePictureService profilePictureService = new ConcreteProfilePictureService(entityManager);
//
//		User user = getUser(id);
//		Document oldProfilePicture = user.getProfilePicture();
//		user.setProfilePicture(profilePictureService.modifyProfilePicture(oldProfilePicture, newProfilePicture));
//		
//		
//	}
//	
//}
