package services.security;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

    // Serial version (auto-generated)
	private static final long serialVersionUID = 6187832853534108134L;
	
	@Inject
	private UserService userService;

	@Override
	public String buildJWT(String subject) {
        JwtClaims claims = new JwtClaims();
        claims.setSubject(subject); // the subject/principal is whom the token is about

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        try {
			jws.setKey(KeytoolUtility.getPrivateKey());
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jwt = null;
        try {
            jwt = jws.getCompactSerialization();
        } catch (JoseException ex) {
            Logger.getLogger(JWTAuthFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jwt;
    }
}
