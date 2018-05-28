package services.utility;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener that initializes the ServletContext of the ContextProvider.
 * 
 * @author kaikoveritch
 *
 */
@WebListener
public class ContextInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// Nothing to do at shutdown
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		ContextProvider.setContext(contextEvent.getServletContext());
	}

}
