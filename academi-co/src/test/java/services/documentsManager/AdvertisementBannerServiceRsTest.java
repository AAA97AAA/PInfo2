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
 * Unit tests for advertisement banner service class
 * @author petrbinko
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdvertisementBannerServiceRsTest {

	@Mock
	Document fakeDocument;
	
	@Mock
	ConcreteAdvertisementBannerService service;
	
	@InjectMocks
	AdvertisementBannerServiceRs serviceRs;
	
	/**
	 * Unit test for method GetAdvertisementBanner from rest implementation of AdvertisementBannerService
	 */
	@Test
	public void testGetAdvertisementBanner() {
		
		// Random id generation
		long id = ThreadLocalRandom.current().nextLong();
		
		// Specifying behavior for mock objects related to calls in the service
		when(service.getAdvertisementBanner(id)).thenReturn(fakeDocument);
		
		// Calling method getAdvertisementBanner on rest service
		serviceRs.getAdvertisementBanner(id);
		
		// Verifying right calls on method
		verify(service, times(1)).getAdvertisementBanner(id);
		
	}
	
	/**
	 * Unit test for method addAdvertisementBanner in the rest implementation of AdvertisementBannerService
	 * @throws URISyntaxException 
	 */
	@Test
	public void testAddAdvertisementBanner() throws URISyntaxException {
		
		when(service.addAdvertisementBanner(fakeDocument)).thenReturn(fakeDocument);
		
		serviceRs.addAdvertisementBanner(fakeDocument);
		
		verify(service, times(1)).addAdvertisementBanner(fakeDocument);

	}
	
	/**
	 * Unit test for method removeAdvertisementBanner in the rest implementation of AdvertisementBannerService
	 */
	@Test
	public void testRemoveAdvertisementBanner() {
		
		// Random id generation
		long id = ThreadLocalRandom.current().nextLong();
				
		serviceRs.removeAdvertisementBanner(id);
		
		verify(service, times(1)).removeAdvertisementBanner(id);
		
	}

}
