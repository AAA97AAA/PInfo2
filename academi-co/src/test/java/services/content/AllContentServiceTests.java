package services.content;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Class to run all tests for content services
 * 
 * @author petrbinko
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcretePostServiceTest.class, ConcreteUserServiceTest.class,
	QuestionThreadServiceRsTest.class, UserServiceRsTest.class, PostServiceRsTest.class})
public class AllContentServiceTests {

}
