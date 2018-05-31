package services.utility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the services.utility package.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ContextHandlerTest.class, ObjectMapperContextResolverTest.class, VotersMarshallerTest.class,
	ValidationHandlingTest.class, ErrorPayloadTest.class })
public class AllUtilityTests {

}
