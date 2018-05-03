package services.inbox;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the services.inbox package.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteInboxServiceTest.class, InboxServiceRsTest.class })
public class AllInboxServiceTests {

}
