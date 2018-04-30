package services.content;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Before;
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
 * @author petrbinko (rework)
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

	private ConcreteQuestionThreadService questionThreadService;
	
	@Before
	public void setEntityManager() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		// Instantiate tested service
		questionThreadService = new ConcreteQuestionThreadService();
		
		// Replace entity manager with mock
		Field classAttribute = questionThreadService.getClass().getDeclaredField("entityManager");
		classAttribute.setAccessible(true);
		classAttribute.set(questionThreadService, fakeEntityManager);
		
	}
	
//	/**
//	 * Tests the empty constructor
//	 * (Should throw an exception because there is no database)
//	 */
//	@Test(expected = PersistenceException.class)
//	public void testConstructorNoArgument() {
//		new ConcreteQuestionThreadService();
//	}
//	
//	/**
//	 * Tests that a mock can successfully be given to the constructor
//	 */
//	@Test
//	public void testConstructor() {
//		ConcreteQuestionThreadService service = new ConcreteQuestionThreadService(fakeEntityManagerFactory);
//		assertEquals("Unexpected entity manager factory.", fakeEntityManagerFactory, service.getEmf());
//	}
//	
//	/**
//	 * Tests construction with null pointer (should fail)
//	 */
//	@Test(expected = IllegalArgumentException.class)
//	public void testIllegalConstruction() {
//		new ConcreteQuestionThreadService(null);
//	}

	/**
	 * Tests the 'getQuestionThread' method
	 */
	@Test
	public void testGetQuestionThread() {
		
		// Additional mocks
		QuestionThread thread = mock(QuestionThread.class);
		
		// Add behavior to the mocks
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);
		when(fakeEntityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
		when(fakeCriteriaBuilder.createQuery(QuestionThread.class)).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(QuestionThread.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeQuery);
		when(fakeQuery.getSingleResult()).thenReturn(thread);
		
		// Set parameters for the test and send method call
		long id = ThreadLocalRandom.current().nextLong();
		QuestionThread result = questionThreadService.getQuestionThread(id);
		
		// Control the follow-up calls triggered
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(QuestionThread.class);
		verify(fakeCriteriaQuery, times(1)).from(QuestionThread.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeQuery, times(1)).getSingleResult();
		
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
		
		// Call method
		QuestionThread result = questionThreadService.addQuestionThread(thread);
		
		// Control the follow-up calls triggered
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).persist(thread);
		
		// Control the result
		assertSame("Wrong result received.", thread, result);
	}
}
