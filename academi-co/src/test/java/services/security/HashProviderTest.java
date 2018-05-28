package services.security;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * Test class for HashProvider.
 * 
 * @author kaikoveritch
 *
 */
public class HashProviderTest {

	@Test
	public void testHash() throws NoSuchAlgorithmException {
		
		// Test parameters (hash generated at https://approsto.com/sha-generator/)
		String password = "superSecretPassword_112389098*lol";
		String expected = "d6b3cc28682ae0235c034a31baf9239f0c913e25f0edc64e2b9ca4a39975192a";
		
		// Call hash function and control result
		String result = new ConcreteHashProvider().hash(password);
		assertEquals("Wrong hash produced.", expected, result);
	}

}
