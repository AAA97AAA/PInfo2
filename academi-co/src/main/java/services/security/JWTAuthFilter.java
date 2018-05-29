package services.security;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

/**
*
* Filter which validate a JSON Web Token
* 
* @author Clasino
* @source https://github.com/abhirockzz/jaxrs-with-jwt
* 
*/
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authHeaderVal = requestContext.getHeaderString("Authorization");

        //consume JWT i.e. execute signature validation
        if (authHeaderVal.startsWith("Bearer")) {
            try {
                final String subject = validate(authHeaderVal.split(" ")[1]);
                final SecurityContext securityContext = requestContext.getSecurityContext();
                if (subject != null) {
                    requestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return new Principal() {
                                @Override
                                public String getName() {
                                    return subject;
                                }
                            };
                        }

                        @Override
                        public boolean isUserInRole(String role) {
                            return securityContext.isUserInRole(role);
                        }

                        @Override
                        public boolean isSecure() {
                            return securityContext.isSecure();
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return securityContext.getAuthenticationScheme();
                        }
                    });
                }
            } catch (InvalidJwtException ex) {
                requestContext.setProperty("auth-failed", true);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

            }
        } else {
            requestContext.setProperty("auth-failed", true);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private String validate(String jwt) throws InvalidJwtException {
        String subject = null;

        JwtConsumer jwtConsumer;
		try {
			KeytoolUtility key = null;
			try {
				key = new KeytoolUtility();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jwtConsumer = new JwtConsumerBuilder()
			        .setRequireSubject() // the JWT must have a subject claim
			        .setVerificationKey(key.getPublicKey()) // verify the signature with the public key
			        .build(); // create the JwtConsumer instance
			try {
	            //  Validate the JWT and process it to the Claims
	            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
	            subject = (String) jwtClaims.getClaimValue("sub");
	        } catch (InvalidJwtException e) {
	            e.printStackTrace(); //on purpose
	            throw e;
	        }
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        return subject;
    }
}
