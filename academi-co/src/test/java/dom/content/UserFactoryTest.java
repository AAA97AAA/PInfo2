package dom.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletContext;

import org.junit.Test;

import dom.documentsManager.DocumentFactory;
import dom.inbox.InboxFactory;
import services.utility.ContextHandler;

/**
 * Test class for UserFactory
 * 
 * @author kaikoveritch
 *
 */
public class UserFactoryTest {
		
	/**
	 * Only instantiates the factory (unimportant)
	 */
	@Test
	public void testConstructor() {
		new UserFactory();
	}

	/**
	 * Tests the call creating a new thread
	 */
	@Test
	public void testCreateUser() {
		
		// Setup data to be put in the entity
		String username = "Patrik Brunschwick";
		String email = "email@domain.com";
		String password = "supersecret";
		int type = ConcreteUser.REGISTERED;
		long min = 1; long max = 100;
		Map<Long, Post> posts = new HashMap<Long, Post>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			posts.put(i, mock(ConcretePost.class));
		}
		Map<Long, QuestionThread> followedThreads = new HashMap<Long, QuestionThread>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			followedThreads.put(i, mock(ConcreteQuestionThread.class));
		}
		
		// Mock of servlet context for local tests
		ServletContext fakeContext = mock(ServletContext.class);
		when(fakeContext.getRealPath(anyString()))
			.thenReturn(new File("src/test/resources").getAbsolutePath() + "/defaultPP.png");
		ContextHandler.setContext(fakeContext);
		
		// Call the construction method
		User user = UserFactory.createUser(username, email, password, type);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.",
				new ConcreteUser(username, email, password,
						DocumentFactory.loadDocument(UserFactory.DEFAULT_PATH),
						type, "", true, InboxFactory.createInbox(), new HashMap<Long, Post>(),
						new HashMap<Long, QuestionThread>()), user);
	}
	
	/**
	 * Tests the call for copying a thread
	 */
	@Test
	public void testCreateUserNoArgument() {
		
		// Call method with a mock
		User user = mock(ConcreteUser.class);
		UserFactory.createUser(user);
		
		// Verify that the right follow-up method was called
		verify((ConcreteUser) user, times(1)).clone();
	}
}
