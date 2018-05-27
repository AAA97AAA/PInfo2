package services.search;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the services.search package.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteSearchServiceTest.class, IndexInitializerTest.class, SearchInputTest.class,
		SearchResultTest.class, SearchServiceRsTest.class })
public class AllSearchTests {

}
