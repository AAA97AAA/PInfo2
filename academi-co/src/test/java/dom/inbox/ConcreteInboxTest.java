package dom.inbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteInbox
 * @author Aslam CADER
 *
 */
public class ConcreteInboxTest {
	
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		long id = 74;
		Map<Long, Notification> content = new HashMap<Long, Notification>();
		long min = 1;
		long max = 40;
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			content.put(i, mock(Notification.class));
		}

		
		// Construct entity with full constructor call
		ConcreteInbox inbox = new ConcreteInbox(content);
		inbox.setId(id);
		assertEquals("Unexepected id in entity.", id, inbox.getId());
		assertEquals("Unexepected content in entity.", content, inbox.getContent());	
		
		
		// Construct entity with empty constructor call and setters
		ConcreteInbox inbox2 = new ConcreteInbox();
		assertNotNull("Unexpected Null in entity.", inbox2.getContent());
		inbox2.setId(id);
		inbox2.setContent(content);
		assertEquals("Unexpected id in entity.", id, inbox2.getId());
		assertEquals("Unexpected content in entity.", content, inbox2.getContent());
	}
	
	
	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteInbox.class)
			.withRedefinedSuperclass()
			.verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		// Setup data to be put in the entity
		long id = 74;
		Map<Long, Notification> content = new HashMap<Long, Notification>();
		long min = 1;
		long max = 40;
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			content.put(i, mock(Notification.class));
		}

		
		// Construct entity
		ConcreteInbox inbox = new ConcreteInbox(content);
		
		// Clone entity
		ConcreteInbox inboxClone = inbox.clone();
		
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", inbox, inboxClone);
		assertEquals("Clone has different values than the original.", inbox, inboxClone);

		// Same test with a defined id
		inbox.setId(id);
		inboxClone = inbox.clone();
		assertNotEquals("Id was copied (should not be the case).", inbox, inboxClone);

		
		
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity (and mock 'toString' values)
		long id = 74;
		Map<Long, Notification> content = new HashMap<Long, Notification>();
		long min = 1;
		long max = 40;
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			content.put(i, mock(Notification.class));
		}
		when(content.toString()).thenReturn("oklm");
		// Construct entity
		ConcreteInbox inbox = new ConcreteInbox(content);
		inbox.setId(id);
				
		
		// Create expected result
		String contentTxt = content.toString();
		String expected = "ConcreteInbox [id=" + id + ", content={" + contentTxt.substring(1, contentTxt.length()-1) + "}]";
		
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, inbox.toString());

	}

	@Test
	public void testNotification() {
		// Setup data to be put in the entity 
		long idNotif = 123;
		Map<Long, Notification> content = new HashMap<Long, Notification>();
		
		
		// Construct entity
		ConcreteInbox inbox = new ConcreteInbox(content);
		
		
		
		int min = 1; int max = 100;
		int addedThreadsSize = ThreadLocalRandom.current().nextInt(min+1, max);
		for (long i = 0; i < addedThreadsSize; i++) {
			Notification notification = mock(Notification.class);
			when(notification.getId()).thenReturn(i);
			inbox.addNotification(notification);
		}

		assertEquals("Wrong number of notification added.", addedThreadsSize, inbox.getContent().size());
		
		
		
		// Try to remove followed threads
		
		int removedThreadsSize = ThreadLocalRandom.current().nextInt(min, addedThreadsSize);
		for (long i = 0; i < removedThreadsSize; i++) {
			inbox.removeNotification(i);
		}
		assertEquals("Wrong number of notification removed.",
				addedThreadsSize - removedThreadsSize, inbox.getContent().size());
		
	
	
		// mark notification 
		Notification notification = mock(Notification.class);
		when(notification.getId()).thenReturn(idNotif);
		inbox.addNotification(notification);
		assertEquals("Wrong notification", notification, inbox.getNotification(idNotif));
		inbox.markNotificationAsRead(idNotif);
		verify(notification).setWasRead(true);

	}

}
