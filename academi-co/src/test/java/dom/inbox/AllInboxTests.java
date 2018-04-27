package dom.inbox;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the whole dom.inbox package
 * 
 * @author Aslam CADER
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteInboxTest.class, ConcreteNotificationTest.class, InboxFactoryTest.class,
		NotificationFactoryTest.class })
public class AllInboxTests {

}
