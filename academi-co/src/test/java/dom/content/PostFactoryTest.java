package dom.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

/**
 * Test class for PostFactory
 * 
 * @author kaikoveritch
 *
 */
public class PostFactoryTest {
	
	/**
	 * Only instantiates the factory (unimportant)
	 */
	@Test
	public void testConstructor() {
		new PostFactory();
	}

	/**
	 * Tests the call creating a new thread
	 */
	@Test
	public void testCreateQuestionThread() {
		
		// Setup data to be put in the entity
		User author = mock(ConcreteUser.class);
		String content = "content";
		long min = 1; long max = 100;
		Set<User> upvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			upvoters.add(mock(ConcreteUser.class));
		}
		Set<User> downvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			downvoters.add(mock(ConcreteUser.class));
		}
		String title = "title";
		Map<Long, Comment> answers = new HashMap<Long, Comment>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			answers.put(i, mock(ConcreteComment.class));
		}
		MainTag subject = mock(ConcreteMainTag.class);
		Tag language = mock(ConcreteTag.class);
		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			topics.put(i, mock(ConcreteSecondaryTag.class));
		}
		LocalDateTime date = LocalDateTime.now();
		
		// Call the construction method
		ConcreteQuestionThread thread = (ConcreteQuestionThread) PostFactory.createQuestionThread(author, content, title, subject, language, topics);
		thread.setCreationDate(date);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.",
				new ConcreteQuestionThread(author, content, date, new HashSet<User>(),
						new HashSet<User>(), 0, false, title, new LinkedList<Comment>(),
						subject, language, topics), thread);
	}
	
	/**
	 * Tests the call for copying a thread
	 */
	@Test
	public void testCreateQuestionThreadNoArgument() {
		
		// Call method with a mock
		QuestionThread thread = mock(ConcreteQuestionThread.class);
		PostFactory.createQuestionThread(thread);
		
		// Verify that the right follow-up method was called
		verify((ConcreteQuestionThread) thread, times(1)).clone();
	}
	
	/**
	 * Tests the call creating a new comment
	 */
	@Test
	public void testCreateComment() {
		
		// Setup data to be put in the entity
		User author = mock(ConcreteUser.class);
		String content = "content";
		LocalDateTime date = LocalDateTime.now();
		long min = 1; long max = 100;
		Set<User> upvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			upvoters.add(mock(ConcreteUser.class));
		}
		Set<User> downvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			downvoters.add(mock(ConcreteUser.class));
		}
		QuestionThread question = mock(ConcreteQuestionThread.class);
		
		// Call the construction method
		ConcreteComment comment = (ConcreteComment) PostFactory.createComment(author, content, question);
		comment.setCreationDate(date);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.",
				new ConcreteComment(author, content, date, new HashSet<User>(),
						new HashSet<User>(), 0, false, question), comment);
	}
	
	/**
	 * Tests the call for copying a comment
	 */
	@Test
	public void testCreateCommentNoArguments() {
		
		// Call method with a mock
		Comment comment = mock(ConcreteComment.class);
		PostFactory.createComment(comment);
		
		// Verify that the right follow-up method was called
		verify((ConcreteComment) comment, times(1)).clone();
	}
}
