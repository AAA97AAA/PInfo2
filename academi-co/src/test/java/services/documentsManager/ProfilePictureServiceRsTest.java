package services.documentsManager;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.documentsManager.Document;

/**
 * Unit test for profile picture service class
 * @author petrbinko
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilePictureServiceRsTest {
	
	@Mock
	Document fakeDocument;
	
	@Mock
	ConcreteProfilePictureService service;
	
	@InjectMocks
	ProfilePictureServiceRs serviceRs;

	/**
	 * Unit test for method getProfilePicture from rest implementation of ProfilePictureService
	 */
	@Test
	public void testGetProfilePicture() {
		
		// Random id generation
		long id = ThreadLocalRandom.current().nextLong();
		
		// Specifying behavior for mock objects related to calls in the service
		when(service.getProfilePicture(id)).thenReturn(fakeDocument);
		
		// Calling method getProfilePicture on rest service
		serviceRs.getProfilePicture(id);
		
		// Verifying right calls on method
		verify(service, times(1)).getProfilePicture(id);
		
	}
	
	/**
	 * Unit test for method modifyProfilePicture in the rest implementation of ProfilePictureService
	 */
	@Test
	public void testModifyProfilePicture() throws URISyntaxException {
		
		when(service.modifyProfilePicture(fakeDocument, fakeDocument)).thenReturn(fakeDocument);
		
		serviceRs.modifyProfilePicture(fakeDocument, fakeDocument);
		
		verify(service, times(1)).modifyProfilePicture(fakeDocument, fakeDocument);

	}

}
