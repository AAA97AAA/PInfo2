package dom.content;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for posts (Post, Comment, QuestionThread) and their factory.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ PostFactoryTest.class, ConcreteCommentTest.class, ConcretePostTest.class, ConcreteQuestionThreadTest.class })
public class AllPostTests {

}
