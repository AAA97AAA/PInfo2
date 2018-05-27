package services.search;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * Test class for SearchInput
 * 
 * @author kaikoveritch
 *
 */
public class SearchInputTest {

	@Test
	public void testEntity() {
		
		// Test parameters
		String title = "title";
		String subject = "subject";
		String language = "language";
		String topics = "topics";
		String author = "author";
		LocalDateTime from = LocalDateTime.now().minusDays(1L);
		LocalDateTime to = LocalDateTime.now();
		
		// Test full constructor
		SearchInput input = new SearchInput(title, subject, language, topics, author, from, to);
		assertEquals("Unexpected title.", title, input.getTitle());
		assertEquals("Unexpected subject.", subject, input.getSubject());
		assertEquals("Unexpected language.", language, input.getLanguage());
		assertEquals("Unexpected topics.", topics, input.getTopics());
		assertEquals("Unexpected author.", author, input.getAuthor());
		assertEquals("Unexpected from.", from, input.getFrom());
		assertEquals("Unexpected to.", to, input.getTo());
		
		// Test empty constructor and setters
		SearchInput input2 = new SearchInput();
		input2.setTitle(title);
		input2.setSubject(subject);
		input2.setLanguage(language);
		input2.setTopics(topics);
		input2.setAuthor(author);
		input2.setFrom(from);
		input2.setTo(to);
		assertEquals("Unexpected title.", title, input2.getTitle());
		assertEquals("Unexpected subject.", subject, input2.getSubject());
		assertEquals("Unexpected language.", language, input2.getLanguage());
		assertEquals("Unexpected topics.", topics, input2.getTopics());
		assertEquals("Unexpected author.", author, input2.getAuthor());
		assertEquals("Unexpected from.", from, input2.getFrom());
		assertEquals("Unexpected to.", to, input2.getTo());
	}

}
