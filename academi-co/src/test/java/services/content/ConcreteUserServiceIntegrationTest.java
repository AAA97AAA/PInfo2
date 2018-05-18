package services.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.servlet.ServletContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.logging.log4j.LogManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.spi.LoggableFailure;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lmax.disruptor.ExceptionHandler;

import dom.content.Comment;
import dom.content.ConcreteUser;
import dom.content.Post;
import dom.content.PostFactory;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.UserFactory;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;
import dom.inbox.Inbox;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;
import services.documentsManager.ConcreteDocumentService;
import services.documentsManager.DocumentService;
import services.utility.ContextHandler;
import services.utility.View;

/**
 * Integration tests for the ConcreteUserService class.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Arquillian.class)
public class ConcreteUserServiceIntegrationTest {
	
	@Deployment
	static public Archive<?> deploy() {
		return ShrinkWrap.create(WebArchive.class, "test-academi-co-users.war")
				.addClass(ConcreteUserService.class)
				.addClass(UserService.class)
				.addPackage(User.class.getPackage())
				.addPackage(Inbox.class.getPackage())
				.addPackage(Post.class.getPackage())
				.addPackage(Tag.class.getPackage())
				.addClass(ConcreteDocumentService.class)
				.addClass(DocumentService.class)
				.addPackage(Document.class.getPackage())
				.addPackage(View.class.getPackage())
				.addPackages(true, ServletContext.class.getPackage())
				.addPackages(true, LoggableFailure.class.getPackage())
				.addPackages(true, LogManager.class.getPackage())
				.addPackages(true, ExceptionHandler.class.getPackage())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("log4j2-test.xml", "log4j2.xml")
				.addAsResource("defaultPP.png")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@ArquillianResource
	private ServletContext context;

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private UserTransaction trx;
	
	private User sampleUser;
	
	@Inject
	private UserService service;

	
	@Before
	public void setup() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		// Set context (for path navigation)
		ContextHandler.setContext(context);
		// Clear table
		trx.begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<ConcreteUser> cd = cb.createCriteriaDelete(ConcreteUser.class);
		cd.from(ConcreteUser.class);
		em.createQuery(cd).executeUpdate();
		trx.commit();
		// Add test user to table
		trx.begin();
		sampleUser = UserFactory.createUser("someVerySpecialName", "email@lol.com", "password", User.REGISTERED);
		em.persist(sampleUser);
		trx.commit();
	}
	
	
	@Test
	public void testGetUserLong() throws IOException {
		User result = service.getUser(sampleUser.getId());
		assertEquals("Wrong user fetched.", sampleUser, result);
	}

	@Test
	public void testGetUserString() {
		User result = service.getUser(sampleUser.getUsername());
		assertEquals("Wrong user fetched.", sampleUser, result);
	}

	@Test
	public void testAddUser() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		User newUser = service.addUser(UserFactory.createUser("imaginativeName", "puf@pouf.paf", "secret", User.REGISTERED));
		trx.begin();
		User inMemory = em.find(ConcreteUser.class, newUser.getId());
		trx.commit();
		assertEquals("Entity in memory is wrong.", newUser, inMemory);
	}

	@Test
	public void testModifyUser() {
		
		// Test parameters
		String username = "mirobolous";
		String email = "frivolous@outrageous.ous";
		String password = "dubious";
		int type = User.ADMINISTRATOR;
		String bio = "Scrumptious";
		boolean canBeModerator = false;
		Document profilePicture = DocumentFactory.createDocument("spontaneous.jpg", new byte[] {1, 2, 3});
		
		// Create expectation
		User newUser = UserFactory.createUser(username, email, password, type);
		newUser.setBio(bio);
		newUser.setCanBeModerator(canBeModerator);
		DocumentFactory.replaceDocument(newUser.getProfilePicture(), profilePicture);
		
		// Apply modifications to test target
		User result = service.modifyUser(sampleUser.getId(), newUser);
		
		// Control result
		assertEquals("Username wrongly updated.", newUser.getUsername(), result.getUsername());
		assertNotEquals("Email changed (should not).", newUser.getEmail(), result.getEmail());
		assertEquals("Password wrongly updated.", newUser.getPassword(), result.getPassword());
		assertEquals("Type wrongly updated.", newUser.getType(), result.getType());
		assertEquals("Bio wrongly updated.", newUser.getBio(), result.getBio());
		assertEquals("Moderation postulation status wrongly updated.", newUser.isCanBeModerator(), result.isCanBeModerator());
		assertTrue("Profile picture wrongly updated.",
				newUser.getProfilePicture().getName().equals(result.getProfilePicture().getName()) &&
				newUser.getProfilePicture().getData().equals(result.getProfilePicture().getData())
			);
	}

	@Test
	public void testGetUserPosts() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Create another user
		User author = UserFactory.createUser("patrick", "p.brunschwick@ponpon.com", "ppp", User.REGISTERED);
		trx.begin();
		em.persist(author);
		trx.commit();
		
		// Create a few threads
		MainTag subject = TagFactory.createMainTag("subject");
		Tag languageTag = TagFactory.createTag("language");
		trx.begin();
		em.persist(subject);
		em.persist(languageTag);
		trx.commit();
		SecondaryTag topic1 = TagFactory.createSecondaryTag("topic1", subject);
		SecondaryTag topic2 = TagFactory.createSecondaryTag("topic2", subject);
		trx.begin();
		em.persist(topic1);
		em.persist(topic2);
		trx.commit();
		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
		topics.put(topic1.getId(), topic1);
		topics.put(topic2.getId(), topic2);
		QuestionThread thread1 = PostFactory.createQuestionThread(author, "content1", "title1", subject, languageTag, topics);
		QuestionThread thread2 = PostFactory.createQuestionThread(sampleUser, "content2", "title2", subject, languageTag, topics);
		QuestionThread thread3 = PostFactory.createQuestionThread(author, "content3", "title3", subject, languageTag, topics);
		thread2.addDownvoter(author);
		trx.begin();
		em.persist(thread1);
		em.persist(thread2);
		em.persist(thread3);
		trx.commit();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Comment comment1 = PostFactory.createComment(sampleUser, "answer1", thread1);
		comment1.addUpvoter(author);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Comment comment2 = PostFactory.createComment(sampleUser, "answer2", thread3);
		trx.begin();
		em.persist(comment1);
		em.persist(comment2);
		trx.commit();
		
		// Group expectation
		List<Post> expected = new ArrayList<Post>();
		expected.add(thread2);
		expected.add(comment1);
		expected.add(comment2);
		
		// Test fetching by date
		orderPosts(expected, "byDate");
		List<Post> result = service.getUserPosts(sampleUser.getId(), "byDate");
		assertEquals("Wrong posts fetched.", new HashSet<Post>(expected), new HashSet<Post>(result));
		assertEquals("Wrong order (by date).", expected, result);
		
		// Test fetching by score
		orderPosts(expected, "byScore");
		result = service.getUserPosts(sampleUser.getId(), "byScore");
		assertEquals("Wrong order (by score).", expected, result);
	}
	
	private void orderPosts(List<Post> list, String order) {
		Comparator<Post> comparator;
		if (order == "byDate") {
			comparator = new Comparator<Post>() {
				@Override
				public int compare(Post p1, Post p2) {
					int diffenrence = p2.getCreationDate().compareTo(p1.getCreationDate());
					if (diffenrence != 0) {
						return diffenrence;
					}
					return Long.compare(p1.getId(), p2.getId());
				}
			};
		} else if (order == "byScore") {
			comparator = new Comparator<Post>() {
				@Override
				public int compare(Post p1, Post p2) {
					int difference = Integer.compare(p2.getScore(), p1.getScore());
					if (difference != 0) {
						return difference;
					}
					difference = p2.getCreationDate().compareTo(p1.getCreationDate());
					if (difference != 0) {
						return difference;
					}
					return Long.compare(p1.getId(), p2.getId());
				}
			};
		} else {
			comparator = new Comparator<Post>() {
				@Override
				public int compare(Post p1, Post p2) {
					return Long.compare(p1.getId(), p2.getId());
				}
			};
		}
		Collections.sort(list, comparator);
	}
}
