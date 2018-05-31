package services.security;

import javax.ws.rs.container.ContainerRequestContext;

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
	public void testFilter() {
		
		// Test behavior
//		String badToken = "bad token";
//		when()
		
		// Test invalid token case
		
	}

}
