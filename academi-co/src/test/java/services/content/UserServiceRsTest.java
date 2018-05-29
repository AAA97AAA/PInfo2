package services.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.User;

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
	
//	private List<Post> posts;
	

	@Before
	public void setup() throws Throwable {
//		posts = new ArrayList<Post>();
		when(service.getUser(anyLong())).thenReturn(user);
		when(service.getUser(anyString())).thenReturn(user);
//		when(service.addUser(any(User.class))).thenReturn(user);
//		when(service.modifyUser(anyLong(), any(User.class))).thenReturn(user);
//		when(service.getUserPosts(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(posts);
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

//	@Test
//	public void testAddUser() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testAddUserByAdministrator() {
//		
//		// Test success
//		Response response = serviceRest.addUserByAdministrator(user, uriInfo);
//	}

//	@Test
//	public void testModifyUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testModifyUserByAdministrator() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUserPosts() {
//		fail("Not yet implemented");
//	}

}
