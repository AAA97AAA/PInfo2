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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.User;

@RunWith(MockitoJUnitRunner.class)
public class ConcreteUserServiceTest {
	
	
	@Mock
	private CriteriaQuery<Object> fakeCriteriaQuery;
	
	@Mock
	private Root<User> fakeRoot;
	
	@Mock
	private TypedQuery<Object> fakeTypedQuery;
		
	/**
	 * Testing that not empty constructor returns mock entity manager factory
	 */
	@Test
	public void testConstructor() {
		
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);

		ConcreteUserService userServiceFake = new ConcreteUserService(fakeEmf);
//		ConcreteUserService userService = new ConcreteUserService();
				
		assertEquals(userServiceFake.getEmf(), fakeEmf);
//		assertNotNull(userService.emf);
		
	}
	
	/**
	 * Unit tests for addUser method from service.	
	 */
	@Test
	public void testAdduser() {
		
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);
		EntityManager fakeEm = mock(EntityManager.class);
		User user = mock(User.class);

		
		when(fakeEmf.createEntityManager()).thenReturn(fakeEm);
		when(fakeEm.getTransaction()).thenReturn(mock(EntityTransaction.class));
		
		UserService userServiceFake = new ConcreteUserService(fakeEmf);
						
		userServiceFake.addUser(user);
		
		verify(fakeEm, times(2)).getTransaction();
		verify(fakeEm, times(1)).persist(user);		
		
	}
	
	/**
	 * Unit tests for getUser from service.
	 */
	@Test
	public void testGetUser() {
		
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);
		EntityManager fakeEm = mock(EntityManager.class);
		CriteriaBuilder fakeCriteriaBuilder = mock(CriteriaBuilder.class);
		
		when(fakeEmf.createEntityManager()).thenReturn(fakeEm);
		when(fakeEm.getTransaction()).thenReturn(mock(EntityTransaction.class));
		when(fakeEm.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);		
		when(fakeCriteriaQuery.from(User.class)).thenReturn(fakeRoot);
		when(fakeEm.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		
		UserService userServiceFake = new ConcreteUserService(fakeEmf);
		
		userServiceFake.getUser(1);
		
		verify(fakeEm).getTransaction();
		verify(fakeEm).getCriteriaBuilder();
		verify(fakeCriteriaBuilder).createQuery(any());
		verify(fakeCriteriaQuery).from(User.class);
		verify(fakeEm).createQuery(fakeCriteriaQuery);		
		
	}
	
	/**
	 * Unit tests for modifyUser from service
	 */
	@Test
	public void testModifyUser() {
		
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);
		EntityManager fakeEm = mock(EntityManager.class);
		User user = mock(User.class);
		
		when(fakeEmf.createEntityManager()).thenReturn(fakeEm);
		when(fakeEm.getTransaction()).thenReturn(mock(EntityTransaction.class));
		
		
		UserService userServiceFake = new ConcreteUserService(fakeEmf);
		
		userServiceFake.modifyUser(user, user);
		
		verify(fakeEm, times(2)).getTransaction();
		verify(fakeEm, times(1)).persist(user);
		verify(fakeEm, times(1)).remove(user);
		verify(fakeEm, times(1)).close();
		
	}
	
}