package dom.inbox;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Test class for InboxFactory
 * @author Aslam CADER
 *
 */
public class InboxFactoryTest {

	/**
	 * Only instantiates the factory (unimportant)
	 */	
	@Test
	public void testConstructor() {
		new InboxFactory();
	}
	
	/**
	 * Instantiate and compare
	 */
	
	@Test
	public void testInstance() {
		ConcreteInbox inbox = (ConcreteInbox) InboxFactory.createInbox();
		
		assertEquals("Object wrongly instantiated.", inbox, new ConcreteInbox());
	}

}
