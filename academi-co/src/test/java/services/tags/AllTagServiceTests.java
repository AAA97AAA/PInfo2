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
@SuiteClasses({ ConcreteTagServiceTest.class, TagServiceRsTest.class })
public class AllTagServiceTests {

}
