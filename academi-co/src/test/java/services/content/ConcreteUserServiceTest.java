package services.content;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
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
import dom.content.Post;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.UserType;
import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import services.documentsManager.ConcreteDocumentService;
import services.security.HashProvider;
import services.utility.ContextProvider;

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
	private ConcreteDocumentService profilePictureService;
	
	@Mock
	private HashProvider hasher;
	
	@InjectMocks
	private ConcreteUserService userService;
	
	@Mock
	private Map<Long, Post> userPosts;
	
	@Mock
	private Map<Long, QuestionThread> userFollowed;


	@Test
	public void testGetUser() {
		long id = 0;
		long badId = 23;
		ConcreteUser user = mock(ConcreteUser.class);
		when(user.getPosts()).thenReturn(userPosts);
		when(user.getFollowedThreads()).thenReturn(userFollowed);
		when(fakeEntityManager.find(ConcreteUser.class, id)).thenReturn(user);
		when(fakeEntityManager.find(ConcreteUser.class, badId)).thenReturn(null);
		
		// Test success
		User result = userService.getUser(id);
		verify(userPosts, times(1)).size();
		verify(userFollowed, times(1)).size();
		assertSame("Wrong user returned", user, result);
		
		// Test failure
		result = userService.getUser(badId);
		assertNull("User wrongly returned.", result);
	}
	
	/**
	 * Unit tests for addUser method from service.	
	 * @throws Throwable 
	 */
	@Test
	public void testAdduser() throws Throwable {
		
		// Test parameters
		String name = "name";
		String email = "email";
		String password = "password";
		String type = UserType.REGISTERED.getStringVal();
		String digest = "nksdfgjpot323lkjlk";
		
		// Adding behavior
		when(fakeUser.getUsername()).thenReturn(name);
		when(fakeUser.getEmail()).thenReturn(email);
		when(fakeUser.getPassword()).thenReturn(password);
		when(fakeUser.getType()).thenReturn(type);
		when(hasher.hash(anyString())).thenReturn(digest);
		
		// Mock of servlet context for local tests
		ServletContext fakeContext = mock(ServletContext.class);
		when(fakeContext.getRealPath(anyString()))
			.thenReturn(new File("src/test/resources").getAbsolutePath() + "/defaultPP.png");
		ContextProvider.setContext(fakeContext);

		// Calling user service method					
		userService.addUser(fakeUser);
		
		// Verifying right method calls on objects in the service's function
		verify(fakeEntityManager, times(1)).persist(any(User.class));
		
		// Control failure case
		doThrow(new PersistenceException()).when(fakeEntityManager).persist(any(User.class));
		boolean exceptionRaised = false;
		try {
			userService.addUser(fakeUser);
		} catch (Throwable e) {
			exceptionRaised = true;
		}
		assertTrue("Exception was not raised.", exceptionRaised);
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
		
		// Control failure
		when(fakeTypedQuery.getSingleResult()).thenThrow(NoResultException.class);
		User result = userService.getUser(username);
		assertNull("User wrongly returned.", result);
	}
	
	/**
	 * Unit tests for modifyUser from service
	 * @throws Throwable 
	 */
	@Test
	public void testModifyUser() throws Throwable {		
		
		// Test parameters
		long id = 2;
		long id2 = 42;

		// Specifying behavior for mock objects related to calls in the service
		String fakeHash = "lol";
		User oldUser = mock(ConcreteUser.class);
		Document fakePicture = mock(Document.class);
		when(oldUser.getProfilePicture()).thenReturn(fakePicture);
		when(fakePicture.getId()).thenReturn(id2);
		when(fakeEntityManager.find(ConcreteUser.class, id)).thenReturn((ConcreteUser) oldUser);
		
		// Calling method modify user on user service
		when(fakeUser.getPassword()).thenReturn("a");
		when(hasher.hash("a")).thenReturn(fakeHash);
		userService.modifyUser(id, fakeUser);
		
		// Verifying right method calls on objects in the service's function
		verify(oldUser, times(1)).setBio(any());
		verify(oldUser, times(1)).setCanBeModerator(any(boolean.class));
		verify(oldUser, times(1)).setPassword(fakeHash);
		verify(oldUser, times(1)).setType(any());
		verify(oldUser, times(1)).setUsername(any());
		
		// Call with empty password
		when(fakeUser.getPassword()).thenReturn("");
		userService.modifyUser(id, fakeUser);
		verify(oldUser, times(2)).setBio(any());
		verify(oldUser, times(2)).setCanBeModerator(any(boolean.class));
		verify(oldUser, times(1)).setPassword(null);
		verify(oldUser, times(2)).setType(any());
		verify(oldUser, times(2)).setUsername(any());
	}
}