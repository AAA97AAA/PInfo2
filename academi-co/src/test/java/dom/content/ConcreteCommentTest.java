package dom.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;


/**
 * Test class for ConreteComment
 * 
 * @author kaikoveritch
 *
 */
public class ConcreteCommentTest {
	
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
		Set<User> upvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			upvoters.add(mock(ConcreteUser.class));
		}
		Set<User> downvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			downvoters.add(mock(ConcreteUser.class));
		}
		int score = 3;
		boolean banned = false;
		QuestionThread question = mock(ConcreteQuestionThread.class);
		
		// Construct entity with full constructor call
		ConcreteComment comment = new ConcreteComment(author, content, date, upvoters,
				downvoters, score, banned, question);
		assertEquals("Unexpected author in entity.", author, comment.getAuthor());
		assertEquals("Unexpected content in entity.", content, comment.getContent());
		assertEquals("Unexpected date in entity.", date, comment.getCreationDate());
		assertEquals("Unexpected upvoters in entity.", upvoters, comment.getUpvoters());
		assertEquals("Unexpected downvoters in entity.", downvoters, comment.getDownvoters());
		assertEquals("Unexpected score in entity.", score, comment.getScore());
		assertEquals("Unexpected ban status in entity.", banned, comment.isBanned());
		assertEquals("Unexpected question in entity.", question, comment.getQuestion());
		
		// Construct entity with empty constructor call and setters
		ConcreteComment comment2 = new ConcreteComment();
		comment2.setId(id);
		comment2.setAuthor(author);
		comment2.setContent(content);
		comment2.setCreationDate(date);
		comment2.setUpvoters(upvoters);
		comment2.setDownvoters(downvoters);
		comment2.setScore(score);
		comment2.setBanned(banned);
		comment2.setQuestion(question);
		assertEquals("Unexpected id in entity.", id, comment2.getId());
		assertEquals("Unexpected author in entity.", author, comment2.getAuthor());
		assertEquals("Unexpected content in entity.", content, comment2.getContent());
		assertEquals("Unexpected date in entity.", date, comment2.getCreationDate());
		assertEquals("Unexpected upvoters in entity.", upvoters, comment2.getUpvoters());
		assertEquals("Unexpected downvoters in entity.", downvoters, comment2.getDownvoters());
		assertEquals("Unexpected score in entity.", score, comment2.getScore());
		assertEquals("Unexpected ban status in entity.", banned, comment2.isBanned());
		assertEquals("Unexpected question in entity.", question, comment2.getQuestion());
	}

	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteComment.class)
			.withIgnoredFields("author", "upvoters", "downvoters", "question")
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
		Set<User> upvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			upvoters.add(mock(ConcreteUser.class));
		}
		Set<User> downvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			downvoters.add(mock(ConcreteUser.class));
		}
		int score = 3;
		boolean banned = false;
		QuestionThread question = mock(ConcreteQuestionThread.class);
		
		// Construct entity
		ConcreteComment comment = new ConcreteComment(author, content, date, upvoters,
				downvoters, score, banned, question);
		
		// Clone entity
		ConcreteComment commentClone = comment.clone();
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", commentClone, comment);
		assertEquals("Clone has different values than the original.", comment, commentClone);
		
		// Same test with a defined id
		comment.setId(id);
		commentClone = comment.clone();
		assertNotEquals("Id was copied (should not be the case).", comment, commentClone);
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
		Set<User> upvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			User fakeUser = mock(ConcreteUser.class);
			when(fakeUser.getId()).thenReturn(i);
			when(fakeUser.getUsername()).thenReturn("upvoter" + i);
			upvoters.add(fakeUser);
		}
		Set<User> downvoters = new HashSet<User>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			User fakeUser = mock(ConcreteUser.class);
			when(fakeUser.getId()).thenReturn(i);
			when(fakeUser.getUsername()).thenReturn("downvoter" + i);
			downvoters.add(fakeUser);
		}
		int score = 3;
		boolean banned = false;
		QuestionThread question = mock(ConcreteQuestionThread.class);
		when(question.toString()).thenReturn("question");
		
		// Construct entity
		ConcreteComment comment = new ConcreteComment(author, content, date, upvoters,
				downvoters, score, banned, question);
		comment.setId(id);
		
		// Create expected result
		Collector<User, ?, Map<Long, String>> collector = Collectors.toMap(User::getId, User::getUsername);
		String upvotersText = upvoters.stream().collect(collector).toString();
		String downvotersText = downvoters.stream().collect(collector).toString();
		String expected = "ConcreteComment [question=" + question.getId() + ", id=" + id + ", author=" + author.getId()
				+ ", content=" + content + ", creationDate=" + date + ", upvoters={"
				+ upvotersText.substring(1, upvotersText.length()-1) + "}, downvoters={"
				+ downvotersText.substring(1, downvotersText.length()-1) + "}, score=" + score
				+ ", isBanned=" + banned + "]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, comment.toString());
	}
}
