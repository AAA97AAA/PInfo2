package services.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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

import dom.content.QuestionThread;

/**
 * Test class for ConcreteQuestionThreadService
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcreteQuestionThreadServiceTest {
	
	@Mock
	private EntityManagerFactory fakeEntityManagerFactory;
	
	@Mock
	private EntityManager fakeEntityManager;
	
	@Mock
	private CriteriaBuilder fakeCriteriaBuilder;
	
	@Mock
	private CriteriaQuery<QuestionThread> fakeCriteriaQuery;
	
	@Mock
	private Root<QuestionThread> fakeRoot;
	
	@Mock
	private TypedQuery<QuestionThread> fakeQuery;

	/**
	 * Tests the empty constructor
	 * (Should throw an exception because there is no database)
	 */
	@Test(expected = PersistenceException.class)
	public void testConstructorNoArgument() {
		new ConcreteQuestionThreadService();
	}
	
	/**
	 * Tests that a mock can successfully be given to the constructor
	 */
	@Test
	public void testConstructor() {
		ConcreteQuestionThreadService service = new ConcreteQuestionThreadService(fakeEntityManagerFactory);
		assertEquals("Unexpected entity manager factory.", fakeEntityManagerFactory, service.getEmf());
	}
	
	/**
	 * Tests construction with null pointer (should fail)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalConstruction() {
		new ConcreteQuestionThreadService(null);
	}

	/**
	 * Tests the 'getQuestionThread' method
	 */
	@Test
	public void testGetQuestionThread() {
		
		// Additional mocks
		QuestionThread thread = mock(QuestionThread.class);
		
		// Add behavior to the mocks
		when(fakeEntityManagerFactory.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);
		when(fakeEntityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
		when(fakeCriteriaBuilder.createQuery(QuestionThread.class)).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(QuestionThread.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeQuery);
		when(fakeQuery.getSingleResult()).thenReturn(thread);
		
		// Set parameters for the test and send method call
		long id = ThreadLocalRandom.current().nextLong();
		QuestionThread result = new ConcreteQuestionThreadService(fakeEntityManagerFactory).getQuestionThread(id);
		
		// Control the follow-up calls triggered
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(QuestionThread.class);
		verify(fakeCriteriaQuery, times(1)).from(QuestionThread.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeQuery, times(1)).getSingleResult();
		order.verify(fakeEntityManager, times(1)).close();
		
		// Control the result
		assertSame("Wrong result received.", thread, result);
	}

	/**
	 * Tests the 'addQuestionThread' method
	 */
	@Test
	public void testAddQuestionThread() {
		
		// Additional mocks
		QuestionThread thread = mock(QuestionThread.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		
		// Add behavior to the mocks
		when(fakeEntityManagerFactory.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(transaction);
		
		// Call method
		QuestionThread result = new ConcreteQuestionThreadService(fakeEntityManagerFactory).addQuestionThread(thread);
		
		// Control the follow-up calls triggered
		InOrder order = inOrder(fakeEntityManager);
		InOrder order2 = inOrder(transaction);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order2.verify(transaction, times(1)).begin();
		order.verify(fakeEntityManager, times(1)).persist(thread);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order2.verify(transaction, times(1)).commit();
		order.verify(fakeEntityManager, times(1)).close();
		
		// Control the result
		assertSame("Wrong result received.", thread, result);
	}
}
