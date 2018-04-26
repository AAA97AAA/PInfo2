package dom.tags;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the whole dom.tags package
 * 
 * @author Aslam CADER
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteMainTagTest.class, ConcreteSecondaryTagTest.class, ConcreteTagTest.class,
		TagFactoryTest.class })
public class AllTagsTests {

}
