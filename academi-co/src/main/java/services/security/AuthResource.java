package services.security;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
@Path("/auth")
public class AuthResource {
	
	@Context
    SecurityContext sctx;
	
	@Inject
	private JWTokenUtility tokenUtility;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth() {
    		String authenticatedUser = sctx.getUserPrincipal().getName();
    		String jwt = tokenUtility.buildJWT(authenticatedUser);
    		Response resp = Response.ok(jwt)
    				.header("jwt", jwt)
    				.build();
    		return resp;
    }
}
