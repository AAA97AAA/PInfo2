package services.documentsManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;

/**
 * Test class for profile picture service class
 * 
 * @author petrbinko
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcreteProfilePictureServiceTest {
	
	// Mock objects 	
	@Mock
	private EntityManager fakeEntityManager;
	
	@Mock
	private CriteriaBuilder fakeCriteriaBuilder;
	
	@Mock
	private CriteriaQuery<Object> fakeCriteriaQuery;
	
	@Mock
	private Root<ConcreteDocument> fakeRoot;
	
	@Mock
	private TypedQuery<Object> fakeTypedQuery;
	
	@Mock
	private Document fakeDocument;
	
	@InjectMocks
	ConcreteProfilePictureService concreteProfilePictureService;

	
	@Before
	public void setup() {
		// Specifying behavior for mock objects related to calls in the service
		when(fakeEntityManager.getCriteriaBuilder()).thenReturn(fakeCriteriaBuilder);		
		when(fakeCriteriaBuilder.createQuery(any())).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(ConcreteDocument.class)).thenReturn(fakeRoot);
		when(fakeEntityManager.createQuery(fakeCriteriaQuery)).thenReturn(fakeTypedQuery);
	}
	
	// Tests	
	@Test
	public void testGetProfilePicture() {
		
		// Calling new profile picture service
		long id = ThreadLocalRandom.current().nextLong();

		concreteProfilePictureService.getProfilePicture(id);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(1)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(1)).createQuery(any());
		verify(fakeCriteriaQuery, times(1)).from(ConcreteDocument.class);
		verify(fakeCriteriaQuery, times(1)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), id));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
	
	}
	
	@Test 
	public void testModifyProfilePicture () {
		
		// Additional mock with behavior
		ConcreteProfilePictureService service = spy(concreteProfilePictureService);
		String name = "name";
		byte[] data = new byte[] {1, -42, 100};
		Document oldPicture = DocumentFactory.createDocument(name, data);
		when(service.getProfilePicture(anyInt())).thenReturn(oldPicture);
		when(fakeDocument.getName()).thenReturn(name);
		when(fakeDocument.getData()).thenReturn(data);
		
		// Calling new profile picture service
		service.modifyProfilePicture(oldPicture.getId(), fakeDocument);
		
		// Verifying right method calls on objects in the service's function
		InOrder order = inOrder(fakeEntityManager);
		order.verify(fakeEntityManager, times(2)).getCriteriaBuilder();
		verify(fakeCriteriaBuilder, times(2)).createQuery(any());
		verify(fakeCriteriaQuery, times(2)).from(ConcreteDocument.class);
		verify(fakeCriteriaQuery, times(2)).where(fakeCriteriaBuilder.equal(fakeRoot.get("ID"), oldPicture.getId()));
		order.verify(fakeEntityManager, times(1)).createQuery(fakeCriteriaQuery);
		verify(fakeTypedQuery, times(1)).getSingleResult();
	}

}
