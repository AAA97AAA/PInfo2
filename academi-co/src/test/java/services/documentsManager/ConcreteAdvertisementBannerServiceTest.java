package services.documentsManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
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

import dom.documentsManager.Document;

/**
 * Unit tests for service class Advertisement banner
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcreteAdvertisementBannerServiceTest {
	
	@Mock
	private EntityManager fakeEntityManager;
	@Mock
	private CriteriaBuilder fakeCriteriaBuilder;
	@Mock
	private CriteriaQuery<Document> fakeCriteriaQuery;
	@Mock
	private Root<Document> fakeRoot;
	@Mock
	private TypedQuery<Document> fakeTypedQuery;
	
	private ConcreteAdvertisementBannerService service;
	
	@Before
	public void testSomeShit() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		// Instantiate an actual instance of the service to then modify and spy
		ConcreteAdvertisementBannerService realService = new ConcreteAdvertisementBannerService();
		
		// Replace entityManager attribute with mock
		Field attribute = realService.getClass().getDeclaredField("entityManager");
		attribute.setAccessible(true);
		attribute.set(realService, fakeEntityManager);
		
		// Set the spy-able entityManager
		service = spy(realService);
	}
	
	@Test
	public void testAddAdvertisementBanner() {
		
		// Call method under test with mock
		Document fakeDocument = mock(Document.class);
		service.addAdvertisementBanner(fakeDocument);
		
		// Verifying right method calls on objects in the service's function
		verify(fakeEntityManager, times(1)).persist(fakeDocument);
	}
	
	@Test
	public void testGetAdvertisementBanner() {
		
		Document fakeDocument = mock(Document.class);
		
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(Document.class)).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(Document.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
		when(fakeTypedQuery.getSingleResult()).thenReturn(fakeDocument);
		
		// Calling new advertisement banner service
		long id = ThreadLocalRandom.current().nextLong();
		service.getAdvertisementBanner(id);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(any());
		verify(fakeCriteriaQuery, times(1)).from(Document.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
			
	}
	
	@Test
	public void testRemoveAdvertisementBanner() {
		
		// Test data
		long id = 42; //lol
		Document fetchedDocument = mock(Document.class);
		
		// Override the service's behavior
		doReturn(fetchedDocument).when(service).getAdvertisementBanner(id);
		
		// Call method under test
		service.removeAdvertisementBanner(id);
		
		// Verify follow-up calls
		verify(service, times(1)).getAdvertisementBanner(id);
		verify(fakeEntityManager, times(1)).remove(fetchedDocument);
	}
}
