package services.content;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.User;
import dom.documentsManager.Document;
import services.documentsManager.ConcreteProfilePictureService;

/**
 * Test class for User service
 * 
 * @author petrbinko
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class ConcreteUserServiceTest {
	
	
	// Mock objects
	@Mock
	private EntityManager fakeEntityManager;
	
	@Mock
	private EntityTransaction fakeEntityTransaction;
	
	@Mock
	private CriteriaBuilder fakeCriteriaBuilder;
	
	@Mock
	private CriteriaQuery<Object> fakeCriteriaQuery;
	
	@Mock
	private Root<User> fakeRoot;
	
	@Mock
	private TypedQuery<Object> fakeTypedQuery;
	
	@Mock
	private User fakeUser;
	
	@Mock
	private Document fakeDocument;
	
	private ConcreteUserService userService;
	
	
	@Before
	public void setEntityManager() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		userService = new ConcreteUserService();
		Field classAttributeUser = userService.getClass().getDeclaredField("entityManager");
		classAttributeUser.setAccessible(true);
		classAttributeUser.set(userService, fakeEntityManager);		
		
	}
		
//	/**
//	 * Testing that not empty constructor returns mock entity manager factory
//	 */
//	@Test
//	public void testConstructorNotEmpty() {
//		
//		// Mock objects
//		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);
//
//		// Calling new user service
//		ConcreteUserService userServiceFake = new ConcreteUserService(fakeEmf);
//				
//		// Testing right constructors
//		assertEquals(userServiceFake.getEmf(), fakeEmf);
//		
//	}
//	@Test(expected = PersistenceException.class)
//	public void testConstructorEmpty() {
//		new ConcreteUserService();
//	}
	
	/**
	 * Unit tests for addUser method from service.	
	 */
	@Test
	public void testAdduser() {

		// Calling user service method					
		userService.addUser(fakeUser);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).persist(fakeUser);	

		
	}
	
	/**
	 * Unit tests for getUser from service.
	 */
	@Test
	public void testGetUser() {
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);		
		when(fakeCriteriaQuery.from(User.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		// Calling new user service 
		long id = ThreadLocalRandom.current().nextLong();
		userService.getUser(id);
			
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(User.class);
		verify(fakeCriteriaQuery, times(1)).from(User.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
		
		
	}
	
	/**
	 * Unit tests for modifyUser from service
	 */
	@Test
	public void testModifyUser() {		
		
		long id = ThreadLocalRandom.current().nextLong();

		// Specifying behavior for mock objects related to calls in the service
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);		
		when(fakeCriteriaQuery.from(User.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		when(userService.getUser(id)).thenReturn(fakeUser);
		
		// Calling method modify user on user service
		userService.modifyUser(id, fakeUser);
		
		// Verifying right method calls on objects in the service's function
		InOrder order2 = inOrder(fakeUser);
		order2.verify(fakeUser, times(1)).setBio(null);
		order2.verify(fakeUser, times(1)).setCanBeModerator(any(boolean.class));
		order2.verify(fakeUser, times(1)).setEmail(null);
		order2.verify(fakeUser, times(1)).setPassword(null);
		order2.verify(fakeUser, times(1)).setProfilePicture(null);
		order2.verify(fakeUser, times(1)).setType(any(int.class));
		order2.verify(fakeUser, times(1)).setUsername(null);
		
	}
	
	
	/**
	 * Unit test for modifyUserProfilePicture from service
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
//	@Test
//	public void testModifyUserProfilePicture() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//
//		long id = ThreadLocalRandom.current().nextLong();
//		
//		ConcreteProfilePictureService profilePictureService = new ConcreteProfilePictureService();
//		Field classAttribute = profilePictureService.getClass().getDeclaredField("entityManager");
//		classAttribute.setAccessible(true);
//		classAttribute.set(profilePictureService, fakeEntityManager);	
//		
//		// Specifying behavior for mock objects related to calls in the service
//		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
//		when(fakeUser.getProfilePicture()).thenReturn(fakeDocument);
//		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);		
//		when(fakeCriteriaQuery.from(User.class)).thenReturn(fakeRoot);
//		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
//		when(fakeTypedQuery.getSingleResult()).thenReturn(fakeUser);
//		when(userService.getUser(id)).thenReturn(fakeUser);
//		when(profilePictureService.modifyProfilePicture(fakeDocument, fakeDocument)).thenReturn(fakeDocument);
//		//doNothing().when(fakeEntityManager).persist(fakeDocument);
//		
//		// Calling new user service
//		userService.modifyUserProfilePicture(id, fakeDocument);
//		
//
//		// Verifying right method calls on objects in the service's function
//		// verify(fakeUser).setProfilePicture(fakeDocument);
//
//	}
	
}