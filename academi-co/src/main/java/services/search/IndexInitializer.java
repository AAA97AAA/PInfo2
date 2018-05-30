package services.search;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet context listener to initialize Lucene indexes at
 * application startup for hibernate search.
 * 
 * @author kaikoveritch
 *
 */
@WebListener
public class IndexInitializer implements ServletContextListener {
	
	static private Logger LOG = LogManager.getLogger(IndexInitializer.class);
	
	@EJB
	private SearchService searchService;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			searchService.initIndex();
			LOG.info("Lucene indexes initialized.");
		} catch (InterruptedException e) {
			LOG.warn("Lucen was interrupted while initializing: There might be some issues", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// No action required at shutdown
	}

}
