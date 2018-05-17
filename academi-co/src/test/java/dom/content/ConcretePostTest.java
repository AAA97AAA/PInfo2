package dom.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcretePost
 * 
 * @author kaikoveritch
 *
 */
public class ConcretePostTest {
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		long id = 2;
		User author = mock(ConcreteUser.class);
		String content = "content";
		LocalDateTime date = LocalDateTime.now();
		long min = 1; long max = 100;
		Map<Long, User> upvoters = new HashMap<Long, User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			upvoters.put(i, mock(ConcreteUser.class));
		}
		Map<Long, User> downvoters = new HashMap<Long, User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			downvoters.put(i, mock(ConcreteUser.class));
		}
		int score = 3;
		boolean banned = false;
		
		// Construct entity with full constructor call
		ConcretePost post = new ConcretePost(author, content, date, upvoters,
				downvoters, score, banned);
		assertEquals("Unexpected author in entity.", author, post.getAuthor());
		assertEquals("Unexpected content in entity.", content, post.getContent());
		assertEquals("Unexpected date in entity.", date, post.getCreationDate());
		assertEquals("Unexpected upvoters in entity.", upvoters, post.getUpvoters());
		assertEquals("Unexpected downvoters in entity.", downvoters, post.getDownvoters());
		assertEquals("Unexpected score in entity.", score, post.getScore());
		assertEquals("Unexpected ban status in entity.", banned, post.isBanned());
		
		// Construct entity with empty constructor call and setters
		ConcretePost post2 = new ConcretePost();
		post2.setId(id);
		post2.setAuthor(author);
		post2.setContent(content);
		post2.setCreationDate(date);
		post2.setUpvoters(upvoters);
		post2.setDownvoters(downvoters);
		post2.setScore(score);
		post2.setBanned(banned);
		assertEquals("Unexpected id in entity.", id, post2.getId());
		assertEquals("Unexpected author in entity.", author, post2.getAuthor());
		assertEquals("Unexpected content in entity.", content, post2.getContent());
		assertEquals("Unexpected date in entity.", date, post2.getCreationDate());
		assertEquals("Unexpected upvoters in entity.", upvoters, post2.getUpvoters());
		assertEquals("Unexpected downvoters in entity.", downvoters, post2.getDownvoters());
		assertEquals("Unexpected score in entity.", score, post2.getScore());
		assertEquals("Unexpected ban status in entity.", banned, post2.isBanned());
	}
	
	/**
	 * Tests the 'addUpvoter', 'removeUpvoter', 'addDownvoter' and 'removeDownvoter' methods
	 * and the update of the score
	 */
	@Test
	public void testVotesManipulation() {
		
		// Bounds for randomization
		int min = 1; int max = 100;
		
		// Score keeping
		int actualScore = ThreadLocalRandom.current().nextInt(-100, 100);
		
		// Construct entity
		ConcretePost post = new ConcretePost();
		post.setScore(actualScore);

		// Try to add upvoters
		int upvotersSize = ThreadLocalRandom.current().nextInt(min+1, max);
		actualScore += upvotersSize;
		for (long i = 0; i < upvotersSize; i++) {
			User upvoter = mock(ConcreteUser.class);
			when(upvoter.getId()).thenReturn(i);
			post.addUpvoter(upvoter);
		}
		assertEquals("Wrong number of upvoters added.", upvotersSize, post.getUpvoters().size());
		assertEquals("Wrong score.", actualScore, post.getScore());
		
		// Try to add downvoters
		int downvotersSize = ThreadLocalRandom.current().nextInt(min+1, max);
		actualScore -= downvotersSize;
		for (long i = 0; i < downvotersSize; i++) {
			User downvoter = mock(ConcreteUser.class);
			when(downvoter.getId()).thenReturn(i);
			post.addDownvoter(downvoter);
		}
		assertEquals("Wrong number of downvoters added.", downvotersSize, post.getDownvoters().size());
		assertEquals("Wrong score.", actualScore, post.getScore());
		
		// Try to remove upvoters
		int removedUpvotersNumber = ThreadLocalRandom.current().nextInt(min, upvotersSize);
		actualScore -= removedUpvotersNumber;
		for (long i = 0; i < removedUpvotersNumber; i++) {
			post.removeUpvoter(i);
		}
		assertEquals("Wrong number of upvoters removed.",
				upvotersSize - removedUpvotersNumber, post.getUpvoters().size());
		assertEquals("Wrong score.", actualScore, post.getScore());
		
		// Try to remove downvoters
		int removedDownvotersNumber = ThreadLocalRandom.current().nextInt(min, downvotersSize);
		actualScore += removedDownvotersNumber;
		for (long i = 0; i < removedDownvotersNumber; i++) {
			post.removeDownvoter(i);
		}
		assertEquals("Wrong number of downvoters removed.",
				downvotersSize - removedDownvotersNumber, post.getDownvoters().size());
		assertEquals("Wrong score.", actualScore, post.getScore());
	}

	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcretePost.class)
			.withIgnoredFields("author", "upvoters", "downvoters")
			.verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity
		long id = 2;
		User author = mock(ConcreteUser.class);
		String content = "content";
		LocalDateTime date = LocalDateTime.now();
		long min = 1; long max = 100;
		Map<Long, User> upvoters = new HashMap<Long, User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			upvoters.put(i, mock(ConcreteUser.class));
		}
		Map<Long, User> downvoters = new HashMap<Long, User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			downvoters.put(i, mock(ConcreteUser.class));
		}
		int score = 3;
		boolean banned = false;
		
		// Construct entity
		ConcretePost post = new ConcretePost(author, content, date, upvoters,
				downvoters, score, banned);
		
		// Clone entity
		ConcretePost postClone = post.clone();
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", postClone, post);
		assertEquals("Clone has different values than the original.", post, postClone);
		
		// Same test with a defined id
		post.setId(id);
		postClone = post.clone();
		assertNotEquals("Id was copied (should not be the case).", post, postClone);
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity (and mock 'toString' values)
		long id = 2;
		User author = mock(ConcreteUser.class);
		when(author.toString()).thenReturn("author");
		String content = "content";
		LocalDateTime date = LocalDateTime.now();
		long min = 1; long max = 100;
		Map<Long, User> upvoters = new HashMap<Long, User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			upvoters.put(i, mock(ConcreteUser.class));
			when(upvoters.get(i).toString()).thenReturn("upvoter" + i);
		}
		Map<Long, User> downvoters = new HashMap<Long, User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			downvoters.put(i, mock(ConcreteUser.class));
			when(downvoters.get(i).toString()).thenReturn("downvoter" + i);
		}
		int score = 3;
		boolean banned = false;
		
		// Construct entity
		ConcretePost post = new ConcretePost(author, content, date, upvoters,
				downvoters, score, banned);
		post.setId(id);
		
		// Create expected result
		String upvotersText = upvoters.toString();
		String downvotersText = downvoters.toString();
		String expected = "ConcretePost [id=" + id + ", author=" + author
				+ ", content=" + content + ", creationDate=" + date + ", upvoters={"
				+ upvotersText.substring(1, upvotersText.length()-1) + "}, downvoters={"
				+ downvotersText.substring(1, downvotersText.length()-1) + "}, score=" + score
				+ ", isBanned=" + banned + "]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, post.toString());
	}
}
