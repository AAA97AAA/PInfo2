package services.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lmax.disruptor.ExceptionHandler;

import dom.content.Comment;
import dom.content.ConcreteComment;
import dom.content.ConcreteQuestionThread;
import dom.content.Post;
import dom.content.PostFactory;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.UserFactory;
import dom.content.UserType;
import dom.content.Vote;
import dom.documentsManager.Document;
import dom.inbox.Inbox;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;
import services.documentsManager.ConcreteDocumentService;
import services.documentsManager.DocumentService;
import services.security.ConcreteHashProvider;
import services.security.HashProvider;
import services.tags.ConcreteTagService;
import services.tags.TagService;
import services.utility.ContextProvider;
import services.utility.View;

/**
 * Integration tests for the ConcretePostService class.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Arquillian.class)
public class ConcretePostServiceIntegrationTest {
	
	@Deployment
	static public WebArchive deploy() {
		return ShrinkWrap.create(WebArchive.class, "test-academi-co-users.war")
				.addClass(ConcreteUserService.class)
				.addClass(UserService.class)
				.addPackage(Inbox.class.getPackage())
				.addClass(ConcretePostService.class)
				.addClass(PostService.class)
				.addPackage(Post.class.getPackage())
				.addClass(ConcreteTagService.class)
				.addClass(TagService.class)
				.addPackage(Tag.class.getPackage())
				.addClass(ConcreteDocumentService.class)
				.addClass(DocumentService.class)
				.addPackage(Document.class.getPackage())
				.addClass(ConcreteHashProvider.class)
				.addClass(HashProvider.class)
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
	UserTransaction trx;
	
	@Inject
	PostService service;
	
	private QuestionThread sampleThread;
	
	@Before
	public void setup() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		ContextProvider.setContext(context);
		if (sampleThread == null) {
			byte[] buffer = new byte[10];
			new Random().nextBytes(buffer);
			String baseName = new String(buffer);
			User author = UserFactory.createUser(baseName + "post", baseName + "@onch.com", "012345",
					UserType.REGISTERED.getStringVal());
			MainTag subject = TagFactory.createMainTag("subject42");
			Tag languageTag = TagFactory.createTag("engrrrish");
			trx.begin();
			em.persist(author);
			em.persist(subject);
			em.persist(languageTag);
			trx.commit();
			Set<SecondaryTag> topics = new HashSet<SecondaryTag>();
			for (int i = 0; i < 3; i++) {
				SecondaryTag topic = TagFactory.createSecondaryTag("topic" + i, subject);
				trx.begin();
				em.persist(topic);
				trx.commit();
				topics.add(topic);
			}
			sampleThread = PostFactory.createQuestionThread(author, "text", "question", subject, languageTag, topics);
			trx.begin();
			em.persist(sampleThread);
			trx.commit();
			Comment comment = PostFactory.createComment(author, "answer", sampleThread);
			trx.begin();
			em.persist(comment);
			trx.commit();
		}
	}

	@Test
	public void testGetQuestionThread() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Add some test comments to the sample thread
		for (int i = 0; i < 3; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Comment comment = PostFactory.createComment(sampleThread.getAuthor(), "", sampleThread);
			trx.begin();
			em.persist(comment);
			trx.commit();
		}
		
		// Control that a thread can be fetched
		QuestionThread result = service.getQuestionThread(sampleThread.getId());
		assertEquals("Wrong thread.", sampleThread, result);
		
		// Control that its comments are ordered correctly
		Comparator<Comment> comparator = new Comparator<Comment>() {
			@Override
			public int compare(Comment c1, Comment c2) {
				int difference = c1.getCreationDate().compareTo(c1.getCreationDate());
				if (difference != 0) {
					return difference;
				}
				return Long.compare(c1.getId(), c2.getId());
			}
		};
		List<Comment> orderedComments = new LinkedList<Comment>(result.getAnswers());
		Collections.sort(orderedComments, comparator);
		assertEquals("Comments in the wrong order.", orderedComments, result.getAnswers());
	}

	@Test
	public void testAddPostQuestionThread() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Create and persist a thread
		QuestionThread thread = PostFactory.createQuestionThread(sampleThread.getAuthor(),
				sampleThread.getContent(), sampleThread.getTitle(), sampleThread.getSubject(),
				sampleThread.getLanguage(), sampleThread.getTopics());
		QuestionThread result = service.addPost(thread);
		
		// Control that the correct data was input and returned
		assertNotNull("Could not store thread.", result);
		assertEquals("Wrong thread stored.", sampleThread.getAuthor(), result.getAuthor());
		assertEquals("Wrong thread stored.", sampleThread.getContent(), result.getContent());
		assertEquals("Wrong thread stored.", sampleThread.getTitle(), result.getTitle());
		assertEquals("Wrong thread stored.", sampleThread.getSubject(), result.getSubject());
		assertEquals("Wrong thread stored.", sampleThread.getLanguage(), result.getLanguage());
		assertEquals("Wrong thread stored.", sampleThread.getTopics(), result.getTopics());
		
		// Control that the entity is present in the database
		trx.begin();
		QuestionThread inMemory = em.find(ConcreteQuestionThread.class, result.getId());
		trx.commit();
		assertEquals("Wrong thread in memory.", result, inMemory);
	}

	@Test
	public void testAddPostComment() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Create and persist a comment
		Comment comment = PostFactory.createComment(sampleThread.getAuthor(), "answer2", sampleThread);
		Comment result = service.addPost(comment.getQuestion().getId(), comment);
		
		// Control that the correct data was input and returned
		assertNotNull("Could not store comment.", result);
		assertEquals("Wrong comment stored.", comment.getAuthor(), result.getAuthor());
		assertEquals("Wrong comment stored.", comment.getContent(), result.getContent());
		assertEquals("Wrong comment stored.", comment.getQuestion(), result.getQuestion());
		
		// Control that the entity is present in the database
		trx.begin();
		Comment inMemory = em.find(ConcreteComment.class, result.getId());
		trx.commit();
		assertEquals("Wrong comment in memory.", result, inMemory);
	}

	@Test
	public void testSetBan() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
				
		long commentId = sampleThread.getAnswers().get(0).getId();
		
		// Attempt to ban a thread and a comment
		service.setBan(sampleThread.getId(), true);
		service.setBan(commentId, true);
		
		// Control the result
		trx.begin();
		QuestionThread bannedThread = em.find(ConcreteQuestionThread.class, sampleThread.getId());
		Comment bannedComment = em.find(ConcreteComment.class, commentId);
		trx.commit();
		assertTrue("Thread was not banned.", bannedThread.isBanned());
		assertTrue("Comment was not banned.", bannedComment.isBanned());
		
		// Attempt to unban a thread and a comment
		service.setBan(sampleThread.getId(), false);
		service.setBan(commentId, false);
		
		// Control the result
		trx.begin();
		bannedThread = em.find(ConcreteQuestionThread.class, sampleThread.getId());
		bannedComment = em.find(ConcreteComment.class, commentId);
		trx.commit();
		assertFalse("Thread was not unbanned.", bannedThread.isBanned());
		assertFalse("Comment was not unbanned.", bannedComment.isBanned());
	}

	@Test
	public void testVote() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Create voter
		User voter = UserFactory.createUser("voterName", "haha@hoho.huhu", "pass", UserType.REGISTERED.getStringVal());
		trx.begin();
		em.persist(voter);
		trx.commit();
		
		// Fetch sample thread and its score
		QuestionThread target = em.find(ConcreteQuestionThread.class, sampleThread.getId());
		int score = target.getScore();
		
		// Test vote sequences
		Vote upvote = new Vote(true, voter.getId());
		Vote downvote = new Vote(false, voter.getId());
		Post result = service.vote(sampleThread.getId(), upvote);
		assertEquals("Wrong score.", score + 1, result.getScore());
		result = service.vote(sampleThread.getId(), upvote);
		assertEquals("Wrong score.", score, result.getScore());
		result = service.vote(sampleThread.getId(), downvote);
		assertEquals("Wrong score.", score - 1, result.getScore());
		result = service.vote(sampleThread.getId(), downvote);
		assertEquals("Wrong score.", score, result.getScore());
		service.vote(sampleThread.getId(), upvote);
		result = service.vote(sampleThread.getId(), downvote);
		assertEquals("Wrong score.", score - 1, result.getScore());
		
		// Control that changes are persistent
		trx.begin();
		QuestionThread inMemory = em.find(ConcreteQuestionThread.class, sampleThread.getId());
		trx.commit();
		assertEquals("Wrong score stored in database.", result, inMemory);
	}
}
