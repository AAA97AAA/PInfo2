package services.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

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

import dom.content.ConcreteUser;
import dom.content.Post;
import dom.content.User;
import dom.content.UserFactory;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;
import dom.inbox.Inbox;
import dom.tags.Tag;
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

}
