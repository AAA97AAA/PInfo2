package services.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

/**
 * Test class for TagServiceRs.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TagServiceRsTest {
	
	@Mock
	private TagService service;
	
	@InjectMocks
	private TagServiceRs serviceRest;

	@Test
	public void testGetAllSubjects() {
		List<MainTag> expected = new ArrayList<MainTag>();
		when(service.getAllSubjects()).thenReturn(expected);
		Response response = serviceRest.getAllSubjects();
		verify(service, times(1)).getAllSubjects();
		assertSame("Wrong payload.", expected, response.getEntity());
		assertEquals("Wrong code.", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetLanguageTag() {
		
		// Test success
		long coolId = 42;
		Tag expected = mock(Tag.class);
		when(service.getLanguageTag(coolId)).thenReturn(expected);
		Response response = serviceRest.getLanguageTag(coolId);
		verify(service, times(1)).getLanguageTag(coolId);
		assertSame("Wrong payload.", expected, response.getEntity());
		assertEquals("Wrong code.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		long badId = 666;
		when(service.getLanguageTag(badId)).thenReturn(null);
		response = serviceRest.getLanguageTag(badId);
		verify(service, times(1)).getLanguageTag(badId);
		assertNull("Wrong payload.", response.getEntity());
		assertEquals("Wrong code.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetMainTag() {

		// Test success
		long coolId = 42;
		MainTag expected = mock(MainTag.class);
		when(service.getMainTag(coolId)).thenReturn(expected);
		Response response = serviceRest.getMainTag(coolId);
		verify(service, times(1)).getMainTag(coolId);
		assertSame("Wrong payload.", expected, response.getEntity());
		assertEquals("Wrong code.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		long badId = 666;
		when(service.getMainTag(badId)).thenReturn(null);
		response = serviceRest.getMainTag(badId);
		verify(service, times(1)).getMainTag(badId);
		assertNull("Wrong payload.", response.getEntity());
		assertEquals("Wrong code.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetSecondaryTag() {

		// Test success
		long coolId = 42;
		SecondaryTag expected = mock(SecondaryTag.class);
		when(service.getSecondaryTag(coolId)).thenReturn(expected);
		Response response = serviceRest.getSecondaryTag(coolId);
		verify(service, times(1)).getSecondaryTag(coolId);
		assertSame("Wrong payload.", expected, response.getEntity());
		assertEquals("Wrong code.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		long badId = 666;
		when(service.getSecondaryTag(badId)).thenReturn(null);
		response = serviceRest.getSecondaryTag(badId);
		verify(service, times(1)).getSecondaryTag(badId);
		assertNull("Wrong payload.", response.getEntity());
		assertEquals("Wrong code.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testAddLanguageTag() {
		
		// Test parameters
		long id = 2;
		String path = "path";
		ConcreteTag tag = mock(ConcreteTag.class);
		UriInfo uriInfo = mock(UriInfo.class);
		UriBuilder builder = UriBuilder.fromPath(path);
		URI uri = UriBuilder.fromPath(path).path(Long.toString(id)).build();
		when(tag.getId()).thenReturn(id);
		when(service.addTag(tag)).thenReturn(tag);
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);
		
		// Call method
		Response response = serviceRest.addLanguageTag(tag, uriInfo);
		verify(service, times(1)).addTag(tag);
		assertSame("Wrong payload.", tag, response.getEntity());
		assertEquals("Wrong code.", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Wrong path.", uri, response.getLocation());
	}

	@Test
	public void testAddMainTag() {

		// Test parameters
		long id = 2;
		String path = "path";
		ConcreteMainTag tag = mock(ConcreteMainTag.class);
		UriInfo uriInfo = mock(UriInfo.class);
		UriBuilder builder = UriBuilder.fromPath(path);
		URI uri = UriBuilder.fromPath(path).path(Long.toString(id)).build();
		when(tag.getId()).thenReturn(id);
		when(service.addTag(tag)).thenReturn(tag);
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);
		
		// Call method
		Response response = serviceRest.addMainTag(tag, uriInfo);
		verify(service, times(1)).addTag(tag);
		assertSame("Wrong payload.", tag, response.getEntity());
		assertEquals("Wrong code.", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Wrong path.", uri, response.getLocation());
	}

	@Test
	public void testAddSecondaryTag() {

		// Test parameters
		long coolParentId = 42;
		long id = 2;
		String path = "path";
		ConcreteSecondaryTag tag = mock(ConcreteSecondaryTag.class);
		UriInfo uriInfo = mock(UriInfo.class);
		UriBuilder builder = UriBuilder.fromPath(path);
		URI uri = UriBuilder.fromPath(path).path(Long.toString(id)).build();
		when(tag.getId()).thenReturn(id);
		when(service.addTag(coolParentId, tag)).thenReturn(tag);
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);
		
		// Call method
		Response response = serviceRest.addSecondaryTag(coolParentId, tag, uriInfo);
		verify(service, times(1)).addTag(coolParentId, tag);
		assertSame("Wrong payload.", tag, response.getEntity());
		assertEquals("Wrong code.", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Wrong path.", uri, response.getLocation());
		
		// Test failure
		long badParentId = 123;
		when(service.addTag(badParentId, tag)).thenReturn(null);
		response = serviceRest.addSecondaryTag(badParentId, tag, uriInfo);
		assertEquals("Wrong code.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
