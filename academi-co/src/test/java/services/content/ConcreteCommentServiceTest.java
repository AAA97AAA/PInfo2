package services.content;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
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
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.Comment;
import dom.content.ConcreteComment;
import dom.content.QuestionThread;
import dom.content.User;

/**
 * Unit test for comment service class
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
	private Root<ConcreteComment> fakeRoot;
	
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
	
	@InjectMocks
	private ConcreteCommentService commentService;
	
	
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
		when(fakeCriteriaQuery.from(ConcreteComment.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		// Calling new comment service
		long id = ThreadLocalRandom.current().nextLong();
		commentService.getComment(id);
		
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(any());
		verify(fakeCriteriaQuery, times(1)).from(ConcreteComment.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();

		
	}

}
