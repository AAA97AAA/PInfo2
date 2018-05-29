package services.security;

import java.io.Serializable;

import javax.ejb.Local;

/**
 * Definition of a token constructor
 * 
 * @author kaikoveritch
 *
 */
@Local
public interface JWTokenUtility extends Serializable {

	public String buildJWT(String subject);
}
