package services.documentsManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import dom.documentsManager.Advertisement;

/**
 * Test class for AdvertisementServiceRs.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdvertisementServiceRsTest {
	
	@Mock
	private AdvertisementService service;
	
	@InjectMocks
	private AdvertisementServiceRs serviceRest;
	
	@Mock
	private List<Advertisement> ads;
	
	@Mock
	private Advertisement ad;
	

	@Test
	public void testGetAllAdvertisements() {
		when(service.getAllAdvertisements()).thenReturn(ads);
		Response response = serviceRest.getAllAdvertisements();
		assertSame("Wrong payload.", ads, response.getEntity());
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetCurrentAdvertisement() {
		
		// Test success
		when(service.getCurrentAdvertisement()).thenReturn(ad);
		Response response = serviceRest.getCurrentAdvertisement();
		assertSame("Wrong payload.", ad, response.getEntity());
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		when(service.getCurrentAdvertisement()).thenReturn(null);
		response = serviceRest.getCurrentAdvertisement();
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testAddAdvertisement() {
		
		// Test parameters
		long id = 2;
		String path = "path";
		UriInfo uriInfo = mock(UriInfo.class);
		UriBuilder builder = UriBuilder.fromPath(path);
		URI uri = UriBuilder.fromPath(path).path(Long.toString(id)).build();
		when(ad.getId()).thenReturn(id);
		when(service.addAdvertisement(ad)).thenReturn(ad);
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);
		
		// Test call
		Response response = serviceRest.addAdvertisement(ad, uriInfo);
		assertSame("Wrong payload.", ad, response.getEntity());
		assertEquals("Wrong status.", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Wrong location.", uri, response.getLocation());
	}

	@Test
	public void testRemoveAdvertisement() {
		
		// Test behavior
		long id = 42;
		long badId = 0;
		when(service.removeAdvertisement(id)).thenReturn(true);
		when(service.removeAdvertisement(badId)).thenReturn(false);
		
		// Test success
		Response response = serviceRest.removeAdvertisement(id);
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		response = serviceRest.removeAdvertisement(badId);
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testChangePeriod() {
		
		// Test behavior
		int period = 42;
		ServletContext context = mock(ServletContext.class);
		Timer timer = mock(Timer.class);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				((Timer) invocation.getArgument(1)).cancel();
				return null;
			}
		}).when(context).setAttribute(anyString(), any(Timer.class));
		
		// Test without timer
		when(context.getAttribute("timer")).thenReturn(null);
		Response response = serviceRest.changePeriod(period, context);
		verify(context, times(1)).setAttribute(anyString(), any(Timer.class));
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test with pre-existing timer
		when(context.getAttribute("timer")).thenReturn(timer);
		response = serviceRest.changePeriod(period, context);
		verify(timer, times(1)).cancel();
		verify(context, times(2)).setAttribute(anyString(), any(Timer.class));
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
	}

}
