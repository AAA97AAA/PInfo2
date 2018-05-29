package services.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import dom.content.ConcreteUser;
import dom.content.Post;
import dom.content.User;
import dom.content.UserType;
import services.utility.ErrorPayload;

/**
 * Test class for UserServiceRs.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceRsTest {
	
	@Mock
	private UserService service;
	
	@InjectMocks
	private UserServiceRs serviceRest;
	
	@Mock
	private User user;
	
	@Mock
	private ConcreteUser cUser;
	
	@Mock
	private ConcreteUser badUser;
	
	@Mock
	private ConcreteUser worseUser;
	
	@Mock
	private UriInfo uriInfo;
	
	private long id;
	
	private String path;
	
	private URI uri;
	
	private String fault;
	
	private List<Post> posts;
	

	@Before
	public void setup() throws Throwable {
		when(service.getUser(anyLong())).thenReturn(user);
		when(service.getUser(anyString())).thenReturn(user);
		doAnswer(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArgument(0);
			}
		}).when(service).addUser(any(User.class));
		fault = "FAULT";
		String message = "something... for key '"+ fault +"'";
		when(service.addUser(badUser)).thenThrow(new MySQLIntegrityConstraintViolationException(message));
		when(service.addUser(worseUser)).thenThrow(UnknownError.class);
		id = 2;
		path = "path";
		when(cUser.getId()).thenReturn(id);
		UriBuilder builder = UriBuilder.fromPath(path);
		uri = UriBuilder.fromPath(path).path(Long.toString(id)).build();
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);
		doAnswer(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArgument(1);
			}
		}).when(service).modifyUser(anyLong(), any(User.class));
		when(service.modifyUser(id, badUser)).thenThrow(new MySQLIntegrityConstraintViolationException(message));
		when(service.modifyUser(id, worseUser)).thenThrow(UnknownError.class);
		posts = new ArrayList<Post>();
		when(service.getUserPosts(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(posts);
	}
	
	
	@Test
	public void testGetUserForSession() {
		
		// Test success
		String name = "lol";
		Response response = serviceRest.getUserForSession(name);
		verify(service, times(1)).getUser(name);
		assertSame("Wrong user in response.", user, response.getEntity());
		assertEquals("Wrong response code.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		String badname = "badlol";
		when(service.getUser(badname)).thenReturn(null);
		response = serviceRest.getUserForSession(badname);
		verify(service, times(1)).getUser(badname);
		assertNull("Wrong user in response.", response.getEntity());
		assertEquals("Wrong response code.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetUser() {

		// Test success
		long id = 42;
		Response response = serviceRest.getUser(id);
		verify(service, times(1)).getUser(id);
		assertSame("Wrong user in response.", user, response.getEntity());
		assertEquals("Wrong response code.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		long badid = 24;
		when(service.getUser(badid)).thenReturn(null);
		response = serviceRest.getUser(badid);
		verify(service, times(1)).getUser(badid);
		assertNull("Wrong user in response.", response.getEntity());
		assertEquals("Wrong response code.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testAddUser() throws NoSuchAlgorithmException {
		UserServiceRs spy = spy(serviceRest);
		spy.addUser(cUser, uriInfo);
		verify(cUser, times(1)).setType(UserType.REGISTERED.getStringVal());
		verify(spy, times(1)).addUserByAdministrator(cUser, uriInfo);
	}

	@Test
	public void testAddUserByAdministrator() throws Throwable {
		
		// Test success
		Response response = serviceRest.addUserByAdministrator(cUser, uriInfo);
		verify(service, times(1)).addUser(cUser);
		assertSame("Wrong payload.", cUser, response.getEntity());
		assertEquals("Wrong status.", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Wrong path.", uri, response.getLocation());
		
		// Test failure (illegal)
		response = serviceRest.addUserByAdministrator(badUser, uriInfo);
		verify(service, times(1)).addUser(badUser);
		assertEquals("Wrong status.", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Wrong error message.", fault, ((ErrorPayload) response.getEntity()).getMessage());
		
		// Test failure (other)
		response = serviceRest.addUserByAdministrator(worseUser, uriInfo);
		verify(service, times(1)).addUser(worseUser);
		assertEquals("Wrong status.", Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}

	@Test
	public void testModifyUser() {
		UserServiceRs spy = spy(serviceRest);
		spy.modifyUser(id, cUser);
		verify(cUser, times(1)).setType(UserType.REGISTERED.getStringVal());
		verify(spy, times(1)).modifyUserByAdministrator(id, cUser);
	}

	@Test
	public void testModifyUserByAdministrator() throws Throwable {

		// Test success
		Response response = serviceRest.modifyUserByAdministrator(id, cUser);
		verify(service, times(1)).modifyUser(id, cUser);
		assertSame("Wrong payload.", cUser, response.getEntity());
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure (illegal)
		response = serviceRest.modifyUserByAdministrator(id, badUser);
		verify(service, times(1)).modifyUser(id, badUser);
		assertEquals("Wrong status.", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Wrong error message.", fault, ((ErrorPayload) response.getEntity()).getMessage());
		
		// Test failure (other)
		response = serviceRest.modifyUserByAdministrator(id, worseUser);
		verify(service, times(1)).modifyUser(id, worseUser);
		assertEquals("Wrong status.", Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetUserPosts() {
		
		// Additional test parameters
		String order = "lol";
		int from = 2;
		int length = 33;
		long badId = 7;
		
		// Test success
		Response response = serviceRest.getUserPosts(id, order, from, length);
		verify(service, times(1)).getUserPosts(id, order, from, length);
		assertSame("Wrong payload.", posts, response.getEntity());
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		
		// Test failure
		when(service.getUserPosts(badId, order, from, length)).thenReturn(null);
		response = serviceRest.getUserPosts(badId, order, from, length);
		verify(service, times(1)).getUserPosts(badId, order, from, length);
		assertEquals("Wrong status.", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
