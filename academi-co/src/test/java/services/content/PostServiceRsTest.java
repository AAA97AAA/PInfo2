package services.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.Comment;
import dom.content.Post;
import dom.content.QuestionThread;
import dom.content.Vote;

/**
 * Test class for PostServiceRs.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceRsTest {
	
	@Mock
	private PostService service;
	
	@InjectMocks
	private PostServiceRs serviceRest;
	
	@Mock
	private QuestionThread thread;
	
	@Mock
	private QuestionThread badThread;
	
	@Mock
	private QuestionThread worseThread;
	
	@Mock
	private Comment comment;
	
	@Mock
	private Post post;
	
	@Mock
	private UriInfo uriInfo;
	
	private String path;
	
	private URI uri;
	
	private long id;
	

	@Before
	public void setup() {
		id = 42;
		path = "path";
		UriBuilder builder = UriBuilder.fromPath(path);
		uri = UriBuilder.fromPath(path).path(Long.toString(id)).build();
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);
		when(thread.getId()).thenReturn(id);
	}
	
	
	@Test
	public void testGetQuestionThread() {
		
		// Test behavior
		long id = 3;
		long badId = 33;
		when(service.getQuestionThread(id)).thenReturn(thread);
		when(service.getQuestionThread(badId)).thenReturn(null);
		
		// Test success
		Response response = serviceRest.getQuestionThread(id);
		assertSame("Wrong payload.", thread, response.getEntity());
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure (404)
		response = serviceRest.getQuestionThread(badId);
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testAddQuestionThread() {
		
		// Test behavior
		when(service.addPost(thread)).thenReturn(thread);
		when(service.addPost(badThread)).thenReturn(null);
		when(service.addPost(worseThread)).thenThrow(IllegalArgumentException.class);
		
		// Test success
		Response response = serviceRest.addQuestionThread(thread, uriInfo);
		assertSame("Wrong payload.", thread, response.getEntity());
		assertEquals("Wrong status.", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Wrong location.", uri, response.getLocation());
		
		// Test 404
		response = serviceRest.addQuestionThread(badThread, uriInfo);
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
		
		// Test 422 (illegal)
		response = serviceRest.addQuestionThread(worseThread, uriInfo);
		assertEquals("Wrong status.", 422, response.getStatus());
	}

	@Test
	public void testAddComment() {
		
		// Test behavior
		long badId = 100;
		when(comment.getId()).thenReturn(id);
		when(service.addPost(id, comment)).thenReturn(comment);
		when(service.addPost(badId, comment)).thenReturn(null);
		
		// Test success
		Response response = serviceRest.addComment(id, comment, uriInfo);
		assertSame("Wrong payload.", comment, response.getEntity());
		assertEquals("Wrong status.", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Wrong location.", uri, response.getLocation());
		
		// Test failure (parent not found)
		response = serviceRest.addComment(badId, comment, uriInfo);
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testSetBanOnPost() {
		
		// Test behavior
		long badId = 100;
		when(post.isBanned()).thenReturn(true);
		when(service.setBan(id, true)).thenReturn(post);
		when(service.setBan(badId, true)).thenReturn(null);
		
		// Test success
		Response response = serviceRest.setBanOnPost(id, post);
		assertSame("Wrong payload.", post, response.getEntity());
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		response = serviceRest.setBanOnPost(badId, post);
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testVote() {
		
		// Test behavior
		long badId = 72;
		Vote vote = mock(Vote.class);
		when(service.vote(id, vote)).thenReturn(post);
		when(service.vote(badId, vote)).thenReturn(null);
		
		// Test success
		Response response = serviceRest.vote(id, vote);
		assertSame("Wrong payload.", post, response.getEntity());
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		response = serviceRest.vote(badId, vote);
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
