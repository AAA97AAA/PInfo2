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
@SuiteClasses({ ConcreteCommentServiceTest.class, ConcreteQuestionThreadServiceTest.class,
		ConcreteUserServiceTest.class, CommentServiceRsTest.class, QuestionThreadServiceRsTest.class, 
		UserServiceRsTest.class})
public class AllContentServiceTests {

}
