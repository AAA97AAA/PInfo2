package services.security;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the services.security package.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteHashProviderTest.class, RootUserInitializerTest.class, AuthResourceTest.class,
	JWTResponseFilterTest.class })
public class AllSecurityTests {

}
