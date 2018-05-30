package services.security;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import services.content.UserService;

/**
*
* Constructor of a JSON Web Token
* 
* @author Clasino
* @source https://github.com/abhirockzz/jaxrs-with-jwt
* 
*/
@Stateless
public class ConcreteJWTokenUtility implements JWTokenUtility {
	
	// Error logger for file reading problems
	static private Logger logger = LogManager.getLogger(ConcreteJWTokenUtility.class);

    // Serial version (auto-generated)
	private static final long serialVersionUID = 6187832853534108134L;
	
	@Inject
	private UserService userService;

	@Override
	public String buildJWT(String subject) {
        JwtClaims claims = new JwtClaims();
        claims.setSubject(Long.toString(userService.getUser(subject).getId())); // the subject/principal is whom the token is about

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        try {
        		KeytoolUtility key = null;
			try {
				key = new KeytoolUtility();
			} catch (CertificateException e) {
				logger.error("Error with certificate.", e);
			} catch (IOException e) {
				logger.error("Could not open file.", e);
			}
			if (key == null) {
				return null;
			}
			jws.setKey(key.getPrivateKey());
		} catch (UnrecoverableKeyException e) {
			logger.error("Could not recover key.", e);
		} catch (KeyStoreException e) {
			logger.error("Key store exception.", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Algorithm does not exist.", e);
		}
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jwt = null;
        try {
            jwt = jws.getCompactSerialization();
        } catch (JoseException e) {
        		logger.error("Could not construct token.", e);
        }
        return jwt;
    }
}
