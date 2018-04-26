package services.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.mockito.Mock;

import dom.content.Comment;
import dom.content.QuestionThread;
import dom.content.User;

public class ConcreteCommentServiceTest {
	
	@Mock
	private CriteriaQuery<Object> fakeCriteriaQuery;
	
	@Mock
	private Root<Comment> fakeRoot;
	
	@Mock
	private TypedQuery<Object> fakeTypedQuery;

	@Test
	public void testConstructor() {
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);

		ConcreteCommentService commentServiceFake = new ConcreteCommentService(fakeEmf);
		// ConcreteCommentService commentService = new ConcreteCommentService();
				
		assertEquals(commentServiceFake.getEmf(), fakeEmf);
		// assertNotNull(commentService.getEmf());
	}
	
	@Test
	public void testAddComment() {
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);
		EntityManager fakeEm = mock(EntityManager.class);
		Comment comment = mock(Comment.class);
		QuestionThread questionThread = mock(QuestionThread.class);

		ConcreteCommentService commentServiceFake = new ConcreteCommentService(fakeEmf);
		
		when(fakeEmf.createEntityManager()).thenReturn(fakeEm);
		when(fakeEm.getTransaction()).thenReturn(mock(EntityTransaction.class));
		
		when(comment.getQuestion()).thenReturn(questionThread);
		
		commentServiceFake.addComment(comment);
		
		verify(fakeEm, times(2)).getTransaction();
		verify(fakeEm, times(1)).persist(comment);
		verify(fakeEm, times(1)).persist(questionThread);
		
	}
	
//	@Test
//	public void testGetComment() {
//		
//		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);
//		EntityManager fakeEm = mock(EntityManager.class);
//		CriteriaBuilder fakeCriteriaBuilder = mock(CriteriaBuilder.class);
//		
//		ConcreteCommentService commentServiceFake = new ConcreteCommentService(fakeEmf);
//
//		
//		when(fakeEmf.createEntityManager()).thenReturn(fakeEm);
//		when(fakeEm.getTransaction()).thenReturn(mock(EntityTransaction.class));
//		when(fakeEm.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
//		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);
//		
//		System.out.println(fakeCriteriaQuery);
//		
//		when(fakeCriteriaQuery.from(Comment.class)).thenReturn(fakeRoot);
//		when(fakeEm.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
//		
//		commentServiceFake.getComment(1);
//		
//		verify(fakeEm).getTransaction();
//		verify(fakeEm).getCriteriaBuilder();
//		verify(fakeCriteriaBuilder).createQuery(any());
//		verify(fakeCriteriaQuery).from(User.class);
//		verify(fakeEm).createQuery(fakeCriteriaQuery);	
//		
//	}

}
