package services.documentsManager;

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

import dom.documentsManager.Document;

/**
 * Unit tests for service class Advertisement banner
 * 
 * @author petrbinko
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcreteAdvertisementBannerServiceTest {
	
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
	private Root<Document> fakeRoot;
	
	@Mock
	private TypedQuery<Object> fakeTypedQuery;
	
	@Mock
	private Document fakeDocument;
	
	

	@Test
	public void testConstructorNotEmpty() {
		
		// Calling new advertisement banner service
		ConcreteAdvertisementBannerService advertisementBannerServiceFake = new ConcreteAdvertisementBannerService(fakeEmf);
		
		assertEquals(advertisementBannerServiceFake.getEmf(), fakeEmf);
	}
	
	
	@Test(expected = PersistenceException.class)
	public void testConstructorEmpty() {
		new ConcreteAdvertisementBannerService();
	}
	
	@Test
	public void testAddAdvertisementBanner() {
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
				
		
		// Calling new advertisement banner service
		AdvertisementBannerService advertisementBannerServiceFake = new ConcreteAdvertisementBannerService(fakeEmf);
		advertisementBannerServiceFake.addAdvertisementBanner(fakeDocument);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).persist(fakeDocument);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();
		
	}
	
	@Test
	public void testGetAdvertisementBanner() {
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(Document.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		
		// Calling new advertisement banner service
		long id = ThreadLocalRandom.current().nextLong();
		AdvertisementBannerService advertisementBannerServiceFake = new ConcreteAdvertisementBannerService(fakeEmf);
		advertisementBannerServiceFake.getAdvertisementBanner(id);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(any());
		verify(fakeCriteriaQuery, times(1)).from(Document.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
		verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();
			
	}
	
	@Test
	public void testRemoveAdvertisementBanner() {
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEmf.createEntityManager()).thenReturn(fakeEntityManager);
		when(fakeEntityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
		
		// Calling new advertisement banner service
		AdvertisementBannerService advertisementBannerServiceFake = new ConcreteAdvertisementBannerService(fakeEmf);
		advertisementBannerServiceFake.removeAdvertisementBanner(fakeDocument);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).remove(fakeDocument);
		order.verify(fakeEntityManager, times(1)).getTransaction();
		order.verify(fakeEntityManager, times(1)).close();
		
	}
	
	
	
	

}
