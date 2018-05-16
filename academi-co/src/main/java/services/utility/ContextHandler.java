package services.utility;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("context")
@Startup
@Singleton
public class ContextHandler {

	@Context
	static private ServletContext context;
	
	static public ServletContext getContext() {
		return context;
	}
	
	static public void setContext(ServletContext context) {
		ContextHandler.context = context;
	}
}
