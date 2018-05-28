package services.utility;

import javax.servlet.ServletContext;
/**
 * Service class providing ServletContext for file finding.
 * 
 * @author kaikoveritch
 *
 */
public class ContextProvider {

	static private ServletContext context;
	
	static public ServletContext getContext() {
		return context;
	}
	
	static public void setContext(ServletContext context) {
		ContextProvider.context = context;
	}
}
