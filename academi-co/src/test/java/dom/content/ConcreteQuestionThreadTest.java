package dom.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteQuestionThread
 * 
 * @author kaikoveritch
 *
 */
public class ConcreteQuestionThreadTest {

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
		String title = "title";
		List<Comment> answers = new LinkedList<Comment>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			answers.add(mock(ConcreteComment.class));
		}
		MainTag subject = mock(ConcreteMainTag.class);
		Tag language = mock(ConcreteTag.class);
		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			topics.put(i, mock(ConcreteSecondaryTag.class));
		}
		
		// Construct entity with full constructor call
		ConcreteQuestionThread thread = new ConcreteQuestionThread(author, content, date, upvoters,
				downvoters, score, banned, title, answers, subject, language, topics);
		assertEquals("Unexpected author in entity.", author, thread.getAuthor());
		assertEquals("Unexpected content in entity.", content, thread.getContent());
		assertEquals("Unexpected date in entity.", date, thread.getCreationDate());
		assertEquals("Unexpected upvoters in entity.", upvoters, thread.getUpvoters());
		assertEquals("Unexpected downvoters in entity.", downvoters, thread.getDownvoters());
		assertEquals("Unexpected score in entity.", score, thread.getScore());
		assertEquals("Unexpected ban status in entity.", banned, thread.isBanned());
		assertEquals("Unexpected title in entity.", title, thread.getTitle());
		assertEquals("Unexpected answers list in entity.", answers, thread.getAnswers());
		assertEquals("Unexpected subject in entity.", subject, thread.getSubject());
		assertEquals("Unexpected language in entity.", language, thread.getLanguage());
		assertEquals("Unexpected topics in entity.", topics, thread.getTopics());
		
		// Construct entity with empty constructor call and setters
		ConcreteQuestionThread thread2 = new ConcreteQuestionThread();
		thread2.setId(id);
		thread2.setAuthor(author);
		thread2.setContent(content);
		thread2.setCreationDate(date);
		thread2.setUpvoters(upvoters);
		thread2.setDownvoters(downvoters);
		thread2.setScore(score);
		thread2.setBanned(banned);
		thread2.setTitle(title);
		thread2.setAnswers(answers);
		thread2.setSubject(subject);
		thread2.setLanguage(language);
		thread2.setTopics(topics);
		assertEquals("Unexpected id in entity.", id, thread2.getId());
		assertEquals("Unexpected author in entity.", author, thread2.getAuthor());
		assertEquals("Unexpected content in entity.", content, thread2.getContent());
		assertEquals("Unexpected date in entity.", date, thread2.getCreationDate());
		assertEquals("Unexpected upvoters in entity.", upvoters, thread2.getUpvoters());
		assertEquals("Unexpected downvoters in entity.", downvoters, thread2.getDownvoters());
		assertEquals("Unexpected score in entity.", score, thread2.getScore());
		assertEquals("Unexpected ban status in entity.", banned, thread2.isBanned());
		assertEquals("Unexpected title in entity.", title, thread.getTitle());
		assertEquals("Unexpected answers list in entity.", answers, thread.getAnswers());
		assertEquals("Unexpected subject in entity.", subject, thread.getSubject());
		assertEquals("Unexpected language in entity.", language, thread.getLanguage());
		assertEquals("Unexpected topics in entity.", topics, thread.getTopics());
	}
	
	/**
	 * Tests the 'getAllTags' method
	 */
	@Test
	public void testGetAllTags() {
		
		// Create tags to be put in the entity
		MainTag subject = mock(ConcreteMainTag.class);
		Tag language = mock(ConcreteTag.class);
		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
		int min = 1; int max = 100;
		for (long i = 0; i < ThreadLocalRandom.current().nextInt(min, max); i++) {
			topics.put(i, mock(ConcreteSecondaryTag.class));
		}
		Set<Tag> allTags = new HashSet<Tag>(topics.values());
		allTags.add(subject);
		allTags.add(language);
		
		// Construct entity
		ConcreteQuestionThread thread = new ConcreteQuestionThread();
		thread.setSubject(subject);
		thread.setLanguage(language);
		thread.setTopics(topics);
		
		// Control that the method works as intended
		List<Tag> result = thread.getAllTags();
		assertEquals("First result is not the subject.", subject, result.get(0));
		assertEquals("Second result is not the language.", language, result.get(1));
		assertEquals("Wrong tags returned.", allTags, new HashSet<Tag>(result));
	}
	
	/**
	 * Tests the 'addAnswer' method
	 */
	@Test
	public void testAddAnswer() {
		
		// Construct empty entity
		ConcreteQuestionThread thread = new ConcreteQuestionThread();
		
		// Try to add answers to the entity
		int min = 1; int max = 100;
		int answersNumber = ThreadLocalRandom.current().nextInt(min, max);
		for (long i = 0; i < answersNumber; i++) {
			Comment answer = mock(ConcreteComment.class);
			when(answer.getId()).thenReturn(i);
			thread.addAnswer(answer);
		}
		assertEquals("Wrong number of answers added.", answersNumber, thread.getAnswers().size());
	}

	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteQuestionThread.class)
			.withIgnoredFields("author", "upvoters", "downvoters", "title", "answers", "subject", "language", "topics")
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
		String title = "title";
		List<Comment> answers = new LinkedList<Comment>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			answers.add(mock(ConcreteComment.class));
		}
		MainTag subject = mock(ConcreteMainTag.class);
		Tag language = mock(ConcreteTag.class);
		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			topics.put(i, mock(ConcreteSecondaryTag.class));
		}
		
		// Construct entity
		ConcreteQuestionThread thread = new ConcreteQuestionThread(author, content, date, upvoters,
				downvoters, score, banned, title, answers, subject, language, topics);
		
		// Clone entity
		ConcreteQuestionThread threadClone = thread.clone();
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", threadClone, thread);
		assertEquals("Clone has different values than the original.", thread, threadClone);
		
		// Same test with a defined id
		thread.setId(id);
		threadClone = thread.clone();
		assertNotEquals("Id was copied (should not be the case).", thread, threadClone);
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
		String title = "title";
		List<Comment> answers = new LinkedList<Comment>();
		for (int i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			answers.add(mock(ConcreteComment.class));
			when(answers.get(i).toString()).thenReturn("answer" + i);
		}
		MainTag subject = mock(ConcreteMainTag.class);
		when(subject.toString()).thenReturn("subject");
		Tag language = mock(ConcreteTag.class);
		when(language.toString()).thenReturn("language");
		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			topics.put(i, mock(ConcreteSecondaryTag.class));
			when(topics.get(i).toString()).thenReturn("topics" + i);
		}
		
		// Construct entity
		ConcreteQuestionThread thread = new ConcreteQuestionThread(author, content, date, upvoters,
				downvoters, score, banned, title, answers, subject, language, topics);
		thread.setId(id);
		
		// Create expected result
		String answersText = answers.toString();
		String topicsText = topics.toString();
		Collector<User, ?, Map<Long, String>> collector = Collectors.toMap(User::getId, User::getUsername);
		String upvotersText = upvoters.stream().collect(collector).toString();
		String downvotersText = downvoters.stream().collect(collector).toString();
		String expected = "ConcreteQuestionThread [title=" + title + ", answers={"
				+ answersText.substring(1, answersText.length()-1) + "}, subject=" + subject.getId()
				+ ", language=" + language.getId() + ", topics={" + topicsText.substring(1, topicsText.length()-1)
				+ "}, id=" + id + ", author=" + author + ", content=" + content + ", creationdate="
				+ date + ", upvoters={" + upvotersText.substring(1, upvotersText.length()-1)
				+ "}, downvoters={" + downvotersText.substring(1, downvotersText.length()-1)
				+ "}, score=" + score + ", isbanned=" + banned + "]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, thread.toString());
	}
}
