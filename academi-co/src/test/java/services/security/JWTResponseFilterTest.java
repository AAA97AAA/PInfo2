package services.security;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class for JWTResponseFilter.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JWTResponseFilterTest {
	
	@Mock
	private JWTokenUtility tokenUtility;
	
	@InjectMocks
	private JWTResponseFilter responseFilter;
	
	@Mock
	private ContainerRequestContext requestContext;
	
	@Mock
	private ContainerResponseContext responseContext;
	
	@Mock
	private SecurityContext securityContext;
	
	@Mock
	private Principal principal;
	
	@Mock
	MultivaluedMap<String, Object> headers;
	

	@Test
	public void testFilter() throws IOException {
		
		// Test behavior
		String name = "name";
		String token = "token";
		when(requestContext.getSecurityContext()).thenReturn(securityContext);
		when(securityContext.getUserPrincipal()).thenReturn(principal);
		when(principal.getName()).thenReturn(name);
		when(tokenUtility.buildJWT(name)).thenReturn(token);
		when(responseContext.getHeaders()).thenReturn(headers);
		List<Object> jwt = new ArrayList<>();
		jwt.add(token);
		
		// Test authentication success
		when(requestContext.getProperty("auth-failed")).thenReturn(false);
		responseFilter.filter(requestContext, responseContext);
		verify(headers, times(1)).put("jwt", jwt);
		
		// Test authentication failure
		when(requestContext.getProperty("auth-failed")).thenReturn(true);
		responseFilter.filter(requestContext, responseContext);
		verify(headers, times(1)).put("jwt", jwt);
		
		// Test null authentication
		when(requestContext.getProperty("auth-failed")).thenReturn(null);
		responseFilter.filter(requestContext, responseContext);
		verify(headers, times(2)).put("jwt", jwt);
		
	}

}
