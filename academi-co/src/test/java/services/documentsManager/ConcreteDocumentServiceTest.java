package services.documentsManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ConcreteDocumentServiceTest {
	
	// Mock objects 	
	@Mock
	private EntityManager fakeEntityManager;
	
	@Mock
	private Document fakeDocument;
	
	@InjectMocks
	ConcreteDocumentService concreteDocumentService;

	
	// Tests	
	@Test
	public void testGetProfilePicture() {
		
		// Calling new profile picture service
		long id = 42;

		concreteDocumentService.getDocument(id);
		
		// Verifying right method calls on objects in the service's function
		verify(fakeEntityManager, times(1)).find(ConcreteDocument.class, id);
	
	}
	
	@Test 
	public void testModifyProfilePicture () {
		
		// Additional mock with behavior
		ConcreteDocumentService service = spy(concreteDocumentService);
		String name = "name";
		byte[] data = new byte[] {1, -42, 100};
		Document oldPicture = DocumentFactory.createDocument("puf", new byte[] {1, 1, 1});
		when(service.getDocument(anyInt())).thenReturn(oldPicture);
		when(fakeDocument.getName()).thenReturn(name);
		when(fakeDocument.getData()).thenReturn(data);
		
		// Calling new profile picture service
		service.modifyProfilePicture(oldPicture.getId(), fakeDocument);
		
		// Verifying right method calls on objects in the service's function
		verify(fakeEntityManager, times(1)).find(ConcreteDocument.class, oldPicture.getId());
		assertEquals("Document not updated.", name, oldPicture.getName());
		assertEquals("Document not updated.", data, oldPicture.getData());
	}

}
