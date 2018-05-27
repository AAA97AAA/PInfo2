package services.security;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * Authentication service which returns the JSON Web Token token in response header
 * 
 * @author Clasino
 * @source https://github.com/abhirockzz/jaxrs-with-jwt
 * 
 */
@Path("auth")
public class AuthResource {
	
	@Context
    SecurityContext sctx;

    @GET
    @Produces("text/plain")
    public Response auth() {
    		String authenticatedUser = sctx.getUserPrincipal().getName();
    		String jwt = JWTokenUtility.buildJWT(authenticatedUser);
    		Response resp = Response.ok(authenticatedUser + " authenticated with: " + jwt)
    				.header("jwt", jwt)
    				.build();
    		return resp;
    }
}
