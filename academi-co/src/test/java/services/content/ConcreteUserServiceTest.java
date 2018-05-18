package services.content;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.ConcreteUser;
import dom.content.User;
import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import services.documentsManager.ConcreteDocumentService;
import services.utility.ContextHandler;

/**
 * Unit test for user service class
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class ConcreteUserServiceTest {
	
	// Mock objects
	@Mock
	private EntityManager fakeEntityManager;
	
	@Mock
	private EntityTransaction fakeEntityTransaction;
	
	@Mock
	private CriteriaBuilder fakeCriteriaBuilder;
	
	@Mock
	private CriteriaQuery<ConcreteUser> fakeCriteriaQuery;
	
	@Mock
	private Root<ConcreteUser> fakeRoot;
	
	@Mock
	private TypedQuery<ConcreteUser> fakeTypedQuery;
	
	@Mock
	private User fakeUser;
	
	@Mock
	private ConcreteDocument fakeDocument;
	
	@Mock
	ConcreteDocumentService profilePictureService;
	
	@InjectMocks
	ConcreteUserService userService;


	/**
	 * Unit tests for addUser method from service.	
	 */
	@Test
	public void testAdduser() {
		
		// Test parameters
		String name = "name";
		String email = "email";
		String password = "password";
		int type = User.REGISTERED;
		
		// Adding behavior
		when(fakeUser.getUsername()).thenReturn(name);
		when(fakeUser.getEmail()).thenReturn(email);
		when(fakeUser.getPassword()).thenReturn(password);
		when(fakeUser.getType()).thenReturn(type);
		
		// Mock of servlet context for local tests
		ServletContext fakeContext = mock(ServletContext.class);
		when(fakeContext.getRealPath(anyString()))
			.thenReturn(new File("src/test/resources").getAbsolutePath() + "/defaultPP.png");
		ContextHandler.setContext(fakeContext);

		// Calling user service method					
		userService.addUser(fakeUser);
		
		// Verifying right method calls on objects in the service's function
		verify(fakeEntityManager, times(1)).persist(any(User.class));	

		
	}
	
	/**
	 * Unit tests for getUser from service.
	 */
	@Test
	public void testGetUserByName() {
		
		String username = "name";
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(ConcreteUser.class)).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(ConcreteUser.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		// Calling new user service 
		userService.getUser(username);
			
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(ConcreteUser.class);
		verify(fakeCriteriaQuery, times(1)).from(ConcreteUser.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("username"), username));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
		
		
	}
	
	/**
	 * Unit tests for modifyUser from service
	 */
	@Test
	public void testModifyUser() {		
		
		// Test parameters
		long id = 2;
		long id2 = 42;

		// Specifying behavior for mock objects related to calls in the service
		User oldUser = mock(ConcreteUser.class);
		Document fakePicture = mock(Document.class);
		when(oldUser.getProfilePicture()).thenReturn(fakePicture);
		when(fakePicture.getId()).thenReturn(id2);
		when(fakeEntityManager.find(ConcreteUser.class, id)).thenReturn((ConcreteUser) oldUser);
		
		// Calling method modify user on user service
		userService.modifyUser(id, fakeUser);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(oldUser);
		order.verify(oldUser, times(1)).setBio(null);
		order.verify(oldUser, times(1)).setCanBeModerator(any(boolean.class));
		order.verify(oldUser, times(1)).setPassword(null);
		order.verify(oldUser, times(1)).setType(any(int.class));
		order.verify(oldUser, times(1)).setUsername(null);
		
	}
}