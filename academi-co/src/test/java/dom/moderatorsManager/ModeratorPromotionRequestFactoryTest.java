package dom.moderatorsManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import dom.content.User;
import dom.moderatorsManager.ConcreteModeratorPromotionRequest;
import dom.moderatorsManager.ModeratorPromotionRequest;
import dom.moderatorsManager.ModeratorPromotionRequestFactory;

/**
 * Test class for ModeratorPromotionRequestFactory.
 * 
 * @author kaikoveritch
 *
 */
public class ModeratorPromotionRequestFactoryTest {
	
	/**
	 * Only instantiates the factory (unimportant)
	 */
	@Test
	public void testConstructor() {
		new ModeratorPromotionRequestFactory();
	}

	/**
	 * Tests the call creating a new request
	 */
	@Test
	public void testCreateRequest() {
		
		// Setup data to be put in the entity
		User originator = mock(User.class);
		when(originator.isCanBeModerator()).thenReturn(true);
		
		// Call the construction method
		ModeratorPromotionRequest request = ModeratorPromotionRequestFactory.createRequest(originator);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.", new ConcreteModeratorPromotionRequest(originator), request);
		verify(originator, times(1)).setCanBeModerator(false);
	}
	
	/**
	 * Tests a refused request
	 */
	@Test
	public void testIllegalCreateRequest() {
		// Setup data to be put in the entity
		User originator = mock(User.class);
		when(originator.isCanBeModerator()).thenReturn(false);
		
		// Call the construction method
		ModeratorPromotionRequest request = ModeratorPromotionRequestFactory.createRequest(originator);
		
		// Verify that the expected object was obtained
		assertNull("Request was not null (should be refused).", request);
	}
}
