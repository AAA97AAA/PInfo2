package services.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.security.Principal;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class for AuthRessource REST service.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthResourceTest {
	
	@Mock
	private SecurityContext context;
	
	@Mock
	private JWTokenUtility tokenUtility;
	
	@InjectMocks
	private AuthResource resource;
	
	@Mock
	private Principal principal;
	

	@Test
	public void testAuth() {
		
		// Setup test
		String name = "principal";
		String token = "token";
		String badName = "princopol";
		when(context.getUserPrincipal()).thenReturn(principal);
		when(tokenUtility.buildJWT(name)).thenReturn(token);
		when(tokenUtility.buildJWT(badName)).thenReturn(null);
		
		// Test success
		when(principal.getName()).thenReturn(name);
		Response response = resource.auth();
		assertEquals("Wrong status.", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Wrong payload.", token, response.getEntity());
		assertEquals("Wrong token header.", token, response.getHeaderString("jwt"));
		
		// Test failure
		when(principal.getName()).thenReturn(badName);
		response = resource.auth();
		assertEquals("Wrong status.", Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}

}
