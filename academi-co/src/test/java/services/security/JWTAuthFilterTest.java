package services.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class for JWTAuthFilter.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JWTAuthFilterTest {
	
	@Mock
	private ContainerRequestContext requestContext;
	
	@InjectMocks
	private JWTAuthFilter filter;

	@Test
	public void testFilter() throws IOException {
		
		// Test invalid token case
		when(requestContext.getHeaderString("Authorization")).thenReturn("Bearer lol");
		filter.filter(requestContext);
		verify(requestContext, times(1)).setProperty("auth-failed", true);
		verify(requestContext, times(1)).abortWith(any(Response.class));
		
		// Test wrong header
		when(requestContext.getHeaderString("Authorization")).thenReturn("lol");
		filter.filter(requestContext);
		verify(requestContext, times(2)).setProperty("auth-failed", true);
		verify(requestContext, times(2)).abortWith(any(Response.class));
	}

}
