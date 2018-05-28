package services.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;

/**
 * Service class for hashing strings (passwords).
 * 
 * @author kaikoveritch
 *
 */
@Stateless
public class ConcreteHashProvider implements HashProvider {
	
	// Serial version (auto-generated)
	private static final long serialVersionUID = 1174014051242154529L;
	
	private MessageDigest hasher;
	
	public ConcreteHashProvider() throws NoSuchAlgorithmException {
		hasher = MessageDigest.getInstance("SHA-256");
	}
	
	/**
	 * Generates a SHA-256 Hexadecimal digest as String of a String message.
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String hash(String message) {
		
		// Generate byte digest of message
		byte[] digest = hasher.digest(message.getBytes(StandardCharsets.UTF_8));
		
		// Return in String form
		return byteToHex(digest);
	}
	
	/**
	 * Converts a byte array to hexadecimal String.
	 * @param bytes
	 * @return
	 */
	String byteToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte bits: bytes) {
			builder.append(String.format("%02x", bits));
		}
		return builder.toString();
	}
}
