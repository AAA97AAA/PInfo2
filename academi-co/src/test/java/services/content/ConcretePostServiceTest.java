package services.content;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.ConcreteQuestionThread;
import dom.content.QuestionThread;
import services.tags.TagService;

/**
 * unit test class for concrete question thread service class
 * 
 * @author kaikoveritch
 * @author petrbinko (rework)
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcretePostServiceTest {
	
	@Mock
	private EntityManagerFactory fakeEntityManagerFactory;
	
	@Mock
	private EntityManager fakeEntityManager;
	
	@Mock
	private CriteriaBuilder fakeCriteriaBuilder;
	
	@Mock
	private CriteriaQuery<ConcreteQuestionThread> fakeCriteriaQuery;
	
	@Mock
	private Root<ConcreteQuestionThread> fakeRoot;
	
	@Mock
	private TypedQuery<ConcreteQuestionThread> fakeQuery;
	
	@Mock
	private UserService userService;
	
	@Mock
	private TagService tagService;
	
	@InjectMocks
	ConcretePostService postService;
	
	/**
	 * Tests the 'getQuestionThread' method
	 */
	@Test
	public void testGetQuestionThread() {
		
		// Test parameters
		ConcreteQuestionThread thread = mock(ConcreteQuestionThread.class);
		long id = ThreadLocalRandom.current().nextLong();
		when(fakeEntityManager.find(ConcreteQuestionThread.class, id)).thenReturn(thread);

		// Call method under test
		QuestionThread result = postService.getQuestionThread(id);
		
		// Control the follow-up calls triggered
		verify(fakeEntityManager, times(1)).find(ConcreteQuestionThread.class, id);
		
		// Control the result
		assertSame("Wrong result received.", thread, result);
	}

	/**
	 * Tests the 'addQuestionThread' method
	 */
	@Test
	public void testAddQuestionThread() { // obsolete
		
//		// Additional mocks
//		User author = mock(User.class);
//		MainTag subject = mock(MainTag.class);
//		Tag language = mock(Tag.class);
//		QuestionThread thread = mock(QuestionThread.class);
//		when(thread.getAuthor()).thenReturn(author);
//		when(thread.getSubject()).thenReturn(subject);
//		when(thread.getLanguage()).thenReturn(language);
//		when(subject.getChildren())
//		
//		// Call method
//		QuestionThread result = postService.addPost(thread);
//		
//		// Control the follow-up calls triggered
//		InOrder order = inOrder(fakeEntityManager);
//		order.verify(fakeEntityManager, times(1)).persist(thread);
//		
//		// Control the result
//		assertSame("Wrong result received.", thread, result);
	}
}
