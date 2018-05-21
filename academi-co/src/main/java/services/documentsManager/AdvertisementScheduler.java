package services.documentsManager;

import java.util.Timer;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Scheduler class for updating the currently displayed advertisement banners.
 * 
 * @author kaikoveritch
 *
 */
@WebListener
public class AdvertisementScheduler implements ServletContextListener {
	
	// Context for timer
	private ServletContext context;
	
	@EJB
	private AdvertisementService service;
	

	/**
	 * Create a timer and attach it to the servlet context at application start.
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		// Fetch servlet context
		context = servletContextEvent.getServletContext();
		
		// initialize task
		BannersCycleTask task = new BannersCycleTask(service);
		
		// Define default timer
		int period = 3600000; // 1h
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, period);
		
		// Attach timer to servlet context
		context.setAttribute("timer", timer);
	}
	
	/**
	 * Fetch the running timer, stop and detach it from the servlet context at
	 * application shutdown.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
		// Fetch servlet context
		context = servletContextEvent.getServletContext();
		
		// Recover the running timer
		Timer timer = (Timer) context.getAttribute("timer");
		
		// Cancel any running task on the timer
		if (timer != null) {
			timer.cancel();
		}
		
		// Detach the timer from the servlet context
		context.removeAttribute("timer");
	}
}
