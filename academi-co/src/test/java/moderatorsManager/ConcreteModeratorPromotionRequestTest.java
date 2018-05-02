package moderatorsManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import dom.content.User;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteModeratorPromotionRequest.
 * 
 * @author kaikoveritch
 *
 */
public class ConcreteModeratorPromotionRequestTest {
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		long id = 2;
		User originator = mock(User.class);
		
		// Construct entity with full constructor call
		ConcreteModeratorPromotionRequest request = new ConcreteModeratorPromotionRequest(originator);
		assertEquals("Unexpected author in entity.", originator, request.getOriginator());
		
		// Construct entity with empty constructor call and setters
		ConcreteModeratorPromotionRequest request2 = new ConcreteModeratorPromotionRequest();
		request2.setId(id);
		request2.setOriginator(originator);
		assertEquals("Unexpected id in entity.", id, request2.getId());
		assertEquals("Unexpected originator in entity.", originator, request2.getOriginator());
	}
	
	@Test
	public void testAccept() {
		
		// Create a valid request
		User originator = mock(User.class);
		ConcreteModeratorPromotionRequest request = new ConcreteModeratorPromotionRequest(originator);
		
		// Accept request
		request.accept();
		
		// Verify that originator has been promoted
		verify(originator, times(1)).setType(User.MODERATOR);
	}

	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteModeratorPromotionRequest.class).verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity
		long id = 2;
		User originator = mock(User.class);
		
		// Construct entity
		ConcreteModeratorPromotionRequest request = new ConcreteModeratorPromotionRequest(originator);
		
		// Clone entity
		ConcreteModeratorPromotionRequest requestClone = request.clone();
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", requestClone, request);
		assertEquals("Clone has different values than the original.", request, requestClone);
		
		// Same test with a defined id
		request.setId(id);
		requestClone = request.clone();
		assertNotEquals("Id was copied (should not be the case).", request, requestClone);
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity
		long id = 2;
		User originator = mock(User.class);
		when(originator.toString()).thenReturn("originator");
		
		// Construct entity
		ConcreteModeratorPromotionRequest request = new ConcreteModeratorPromotionRequest(originator);
		request.setId(id);
		
		// Create expected result
		String expected = "ConcreteModeratorPromotionRequest [id=" + id + ", originator=" + originator + "]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, request.toString());
	}
}
