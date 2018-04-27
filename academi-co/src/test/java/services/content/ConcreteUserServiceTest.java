package services.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.User;

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
	private EntityManagerFactory fakeEmf;
	
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

		
	/**
	 * Testing that not empty constructor returns mock entity manager factory
	 */
	@Test
	public void testConstructorNotEmpty() {
		
		// Mock objects
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);

		// Calling new user service
		ConcreteUserService userServiceFake = new ConcreteUserService(fakeEmf);
				
		// Testing right constructors
		assertEquals(userServiceFake.getEmf(), fakeEmf);
		
	}
	@Test(expected = PersistenceException.class)
	public void testConstructorEmpty() {
		new ConcreteUserService();
	}
	
	/**
	 * Unit tests for addUser method from service.	
	 */
	@Test
	public void testAdduser() {
		

		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(fakeEntityTransaction);
		
		// Calling new user service
		UserService userServiceFake = new ConcreteUserService(fakeEmf);
						
		userServiceFake.addUser(fakeUser);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).persist(fakeUser);	
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();

		
	}
	
	/**
	 * Unit tests for getUser from service.
	 */
	@Test
	public void testGetUser() {
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(fakeEntityTransaction);
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);		
		when(fakeCriteriaQuery.from(User.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		// Calling new user service 
		long id = ThreadLocalRandom.current().nextLong();
		UserService userServiceFake = new ConcreteUserService(fakeEmf);
		userServiceFake.getUser(id);
			
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(User.class);
		verify(fakeCriteriaQuery, times(1)).from(User.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
		verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();
		
		
	}
	
	/**
	 * Unit tests for modifyUser from service
	 */
	@Test
	public void testModifyUser() {		
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(fakeEntityTransaction);
//		when(fakeUser.getBio()).thenReturn(any(String.class));
		
		
		// Calling new user service 
		UserService userServiceFake = new ConcreteUserService(fakeEmf);
		userServiceFake.modifyUser(fakeUser, fakeUser);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		InOrder order2 = inOrder(fakeUser);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order2.verify(fakeUser, times(1)).setBio(null);
		order2.verify(fakeUser, times(1)).setCanBeModerator(any(boolean.class));
		order2.verify(fakeUser, times(1)).setEmail(null);
		order2.verify(fakeUser, times(1)).setPassword(null);
		order2.verify(fakeUser, times(1)).setProfilePicture(null);
		order2.verify(fakeUser, times(1)).setType(any(int.class));
		order2.verify(fakeUser, times(1)).setUsername(null);
		order.verify(fakeEntityManager, times(1)).persist(fakeUser);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();
		
	}
	
}