package services.utility;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for ErrorPayload.
 * 
 * @author kaikoveritch
 *
 */
public class ErrorPayloadTest {

	@Test
	public void testEntity() {
		
		// Test parameters
		String message = "message";
		
		// Test full constructor
		ErrorPayload error = new ErrorPayload(message);
		assertEquals("Wrong message.", message, error.getMessage());
		
		// Test empty constructor
		ErrorPayload error2 = new ErrorPayload();
		error2.setMessage(message);
		assertEquals("Wrong message.", message, error2.getMessage());
	}

}
