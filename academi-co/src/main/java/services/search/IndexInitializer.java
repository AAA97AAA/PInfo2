package services.search;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet context listener to initialize Lucene indexes at
 * application startup for hibernate search.
 * 
 * @author kaikoveritch
 *
 */
@WebListener
public class IndexInitializer implements ServletContextListener {
	
	@EJB
	private SearchService searchService;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			searchService.initIndex();
			System.out.println("Lucene indexes initialized.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// No action required at shutdown
	}

}
