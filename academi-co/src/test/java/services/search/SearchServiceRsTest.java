package services.search;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.QuestionThread;

/**
 * Test class for SearchServiceRs.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchServiceRsTest {
	
	@Mock
	private SearchService service;
	
	@InjectMocks
	private SearchServiceRs serviceRs;
	
	@Mock
	private SearchResult<QuestionThread> result;

	@Test
	public void testSearch() {
		
		// Test parameters
		SearchInput input = mock(SearchInput.class);
		int from = 22;
		int size = 10;
		when(service.search(input, from, size)).thenReturn(result);
		
		// Call method under test
		Response response = serviceRs.search(input, from, size);
		
		// Control follow-up calls
		verify(service, times(1)).search(input, from, size);
		
		// Control returned response
		assertEquals("Wrong code.", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Wrong payload.", result, response.getEntity());
	}

}
