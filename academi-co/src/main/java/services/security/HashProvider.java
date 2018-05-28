package services.security;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Local;

/**
 * Definition of a hashing service.
 * 
 * @author kaikoveritch
 *
 */
@Local
public interface HashProvider extends Serializable {

	public String hash(String message) throws NoSuchAlgorithmException;
}
