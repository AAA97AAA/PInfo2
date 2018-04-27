package services.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	@Test
	public void testConstructorNotEmpty() {

		// Calling new user service
		ConcreteCommentService commentServiceFake = new ConcreteCommentService(fakeEmf);
				
		// Testing right constructors
		assertEquals(commentServiceFake.getEmf(), fakeEmf);
	}
	
	@Test(expected = PersistenceException.class)
	public void testConstructorEmpty() {
		new ConcreteCommentService();
	}
	
	@Test
	public void testAddComment() {
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
		
		when(fakeComment.getQuestion()).thenReturn(fakeQuestionThread);
		
		
		// Calling new comment service
		CommentService commentServiceFake = new ConcreteCommentService(fakeEmf);
		commentServiceFake.addComment(fakeComment);
		
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).persist(fakeComment);
		order.verify(fakeEntityManager, times(1)).persist(fakeQuestionThread);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();
		
	}
	
	@Test
	public void testGetComment() {

		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(Comment.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		// Calling new comment service
		long id = ThreadLocalRandom.current().nextLong();
		CommentService commentServiceFake = new ConcreteCommentService(fakeEmf);
		commentServiceFake.getComment(id);
		
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(any());
		verify(fakeCriteriaQuery, times(1)).from(Comment.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
		verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();

		
	}

}
