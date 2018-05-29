package services.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
*
* Filter which add a JSON Web Token in a HTTP response
* 
* @author Clasino
* @source https://github.com/abhirockzz/jaxrs-with-jwt
* 
*/
public class JWTResponseFilter implements ContainerResponseFilter {
	
	@Inject
	private JWTokenUtility tokenUtility;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (requestContext.getProperty("auth-failed") != null) {
            Boolean failed = (Boolean) requestContext.getProperty("auth-failed");
            if (failed) {
                return;
            }
        }

        List<Object> jwt = new ArrayList<Object>();
        jwt.add(tokenUtility.buildJWT(requestContext.getSecurityContext().getUserPrincipal().getName()));
        responseContext.getHeaders().put("jwt", jwt);
    }
}