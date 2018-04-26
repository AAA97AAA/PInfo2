package services.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.User;

@RunWith(MockitoJUnitRunner.class)
public class ConcreteUserServiceTest {
	
	@Mock
	CriteriaQuery<Object> fakeCriteriaQuery;
	
	@Mock
	Root<User> fakeRoot;
	
	@Mock
	TypedQuery<Object> fakeTypedQuery;
		
	
	@Test
	public void testConstructor() {
		
		EntityManagerFactory fakeEmf = mock(EntityManagerFactory.class);
		EntityManager fakeEm = mock(EntityManager.class);
		
		when(fakeEmf.createEntityManager()).thenReturn(fakeEm);
		
		UserService userServiceFake = new ConcreteUserService(fakeEmf);
				
		assertEquals(userServiceFake.getEmf(), fakeEmf);	
		
	}
	
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
		verify(fakeCriteriaBuilder).createQuery();
		verify(fakeCriteriaQuery).from(User.class);
		verify(fakeEm).createQuery(fakeCriteriaQuery);
		
	}

	
}
