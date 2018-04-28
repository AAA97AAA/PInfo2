package dom.inbox;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

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
		String body = "SoftAware";
		LocalDateTime creationDate = LocalDateTime.now();
		
		// Building test comparaison entity
		ConcreteNotification notification = new ConcreteNotification(body);
		notification.setCreationDate(creationDate);
		
		// Building data 
		ConcreteNotification notificationFactory =  (ConcreteNotification) NotificationFactory.createNotification(body);
		notificationFactory.setCreationDate(creationDate);
		assertEquals("Unexpected Notification.",  notification, notificationFactory);

	}

}
