package dom.inbox;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Test class for NotificationFactory
 * @author Aslam CADER
 *
 */
public class NotificationFactoryTest {
	
	/**
	 * Only instantiates the factory (unimportant)
	 */	
	@Test
	public void testConstructor() {
		
		new NotificationFactory();
	}
	
	
	/**
	 * Instantiate and compare
	 */
	@Test 
	public void testInstantiate() {
		// Setup data to be put in the entity
		String body = "SoftAware just replied to your thread";
		
		// Building test comparaison entity
		ConcreteNotification notification = new ConcreteNotification(body);
		
		// Building data 
		ConcreteNotification notificationFactory = (ConcreteNotification) NotificationFactory.createNotification(body);
		
		assertEquals("Unexpected Notification.",  notification, notificationFactory);

	}

}
