package services.content;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.Comment;
import dom.content.QuestionThread;
import dom.content.User;

/**
 * Test class for Comment service
 * 
 * @author petrbinko
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class ConcreteCommentServiceTest {
	
	// Mock objects
	@Mock
	private EntityManagerFactory fakeEmf;
	
	@Mock
	private EntityManager fakeEntityManager;
	
	@Mock
	private CriteriaBuilder fakeCriteriaBuilder;
	
	@Mock
	private CriteriaQuery<Object> fakeCriteriaQuery;
	
	@Mock
	private Root<Comment> fakeRoot;
	
	@Mock
	private TypedQuery<Object> fakeTypedQuery;
	
	@Mock
	private User fakeUser;
	
	@Mock
	private QuestionThread fakeQuestionThread;
	
	@Mock
	private Comment fakeComment;
	
	@Mock
	DataSource fakeDS;
	
	private ConcreteCommentService commentService;
	
	@Before
	public void setEntityManager() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		commentService = new ConcreteCommentService();
		Field classAttribute = commentService.getClass().getDeclaredField("entityManager");
		classAttribute.setAccessible(true);
		classAttribute.set(commentService, fakeEntityManager);
		
	}

//	@Test
//	public void testConstructorNotEmpty() {
//
//		// Calling new user service
//		ConcreteCommentService commentServiceFake = new ConcreteCommentService(fakeEmf);
//				
//		// Testing right constructors
//		assertEquals(commentServiceFake.getEmf(), fakeEmf);
//	}
//	
//	@Test(expected = PersistenceException.class)
//	public void testConstructorEmpty() {
//		new ConcreteCommentService();
//	}
	
	@Test
	public void testAddComment() {
		
		// Specifying behavior for mock objects related to calls in the service		
		when(fakeComment.getQuestion()).thenReturn(fakeQuestionThread);
		
		
		// Calling new comment service
		commentService.addComment(fakeComment);
		
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).persist(fakeComment);
		order.verify(fakeEntityManager, times(1)).persist(fakeQuestionThread);
		
	}
	
	@Test
	public void testGetComment() {

		// Specifying behavior for mock objects related to calls in the service
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(Comment.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		// Calling new comment service
		long id = ThreadLocalRandom.current().nextLong();
		commentService.getComment(id);
		
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(any());
		verify(fakeCriteriaQuery, times(1)).from(Comment.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();

		
	}

}
