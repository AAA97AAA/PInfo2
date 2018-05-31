package services.utility;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;

/**
 * Test class for ValidationHandling interface
 * @author kaikoveritch
 *
 */
public class ValidationHandlingTest {

	@Test
	public void testGetExceptionRootCause() {
		
		// Error mocks and behavior
		Throwable error1 = mock(SecurityException.class);
		Throwable error2 = mock(IOException.class);
		Throwable error3 = mock(IllegalArgumentException.class);
		Throwable error4 = mock(NullPointerException.class);
		when(error1.getCause()).thenReturn(error2);
		when(error2.getCause()).thenReturn(error3);
		when(error3.getCause()).thenReturn(error4);
		when(error4.getCause()).thenReturn(null);
		
		// Test catching root cause
		Throwable cause = ValidationHandling.getExceptionRootCause(error1);
		assertSame("Wrong root cause.", error4, cause);
	}

}
