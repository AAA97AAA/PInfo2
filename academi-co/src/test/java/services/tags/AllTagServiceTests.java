package services.tags;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the services.tags package.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteMainTagServiceTest.class, ConcreteSecondaryTagServiceTest.class, ConcreteTagServiceTest.class,
		MainTagServiceRsTest.class, SecondaryTagServiceRsTest.class, TagServiceRsTest.class })
public class AllTagServiceTests {

}
