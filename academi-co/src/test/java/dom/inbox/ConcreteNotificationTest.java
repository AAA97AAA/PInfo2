package dom.inbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

/**
 * Test class for ConcreteNotification
 * @author Aslam CADER
 *
 */
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ConcreteNotificationTest {

	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		long id = 10;
		String body = "One notification";
		LocalDateTime creationDate = LocalDateTime.now();
		

		
		// Construct entity with full constructor call
		ConcreteNotification notification = new ConcreteNotification(body);
		notification.setId(id);
		assertEquals("Unexpected id in entity.", id, notification.getId());
		assertEquals("Unexpected body in entity.", body, notification.getBody());
		notification.setCreationDate(creationDate);
		assertEquals("Unexpected creationDate in entity.", creationDate, notification.getCreationDate());
		assertEquals("Unexpected boolean (wasRead) in entity.", false, notification.isWasRead());
		notification.setWasRead(true);
		assertEquals("Unexpected boolean (wasRead) in entity.", true, notification.isWasRead());
		notification.setWasRead(false);
		assertEquals("Unexpected boolean (wasRead) in entity.", false, notification.isWasRead());
		

		
		// Construct entity with empty constructor call and setters
		ConcreteNotification notification2 = new ConcreteNotification();
		notification2.setBody(body);
		notification2.setCreationDate(creationDate);
		notification2.setId(id);
		assertEquals("Unexpected id in entity.", id, notification2.getId());
		assertEquals("Unexpected body in entity.", body, notification2.getBody());
		assertEquals("Unexpected creationDate in entity.", creationDate, notification.getCreationDate());
		assertEquals("Unexpected boolean (wasRead) in entity.", false, notification.isWasRead());
		
		
		
}
	
	
	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteNotification.class)
			.withRedefinedSuperclass()
			.verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		// Setup data to be put in the entity
		long id = 10;
		String body = "One notification";
		
		// Construct entity
		ConcreteNotification notification = new ConcreteNotification(body);
		
		
		// Clone entity
		ConcreteNotification notificationClone = notification.clone();
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", notification, notificationClone);
		assertEquals("Clone has differen values than the original.", notification, notificationClone);
	
		
		// Same test with a defined id
		notification.setId(id);
		notificationClone = notification.clone();
		assertNotEquals("Id was copied (should not be the case).", notification, notificationClone);

		
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity (and mock 'toString' values)
		long id = 10;
		String body = "One notification";
		LocalDateTime creationDate = LocalDateTime.now();
			
		
		// Construct entity
		ConcreteNotification notification = new ConcreteNotification(body);
		notification.setId(id);
		notification.setCreationDate(creationDate);
		
		
		// Create expected result
		String expected = "ConcreteNotification [id=" + id + ", body=" + body + ", wasRead=" + false + ", creationDate=" + creationDate + "]";

		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, notification.toString());

		
	}
	
	/**
	 * Tests the parent (inbox)
	 */
	@Test 
	public void testParent() {
		// Setup data to be put in the entity (and mock 'toString' values)
			// mock
			Inbox parent = mock(ConcreteInbox.class);
		
		// creating the entity
		ConcreteNotification notification = new ConcreteNotification();

		
		// set and test the get 
		notification.setParent(parent);
		assertEquals("Unexpected parent", parent, notification.getParent());
		
			
	}

}
