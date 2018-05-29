package services.security;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dom.content.User;
import dom.content.UserFactory;
import dom.content.UserType;
import services.content.UserService;
import services.utility.ContextProvider;

/**
 * Listener to initialize a root (admin) user at startup.
 * 
 * @author kaikoveritch
 *
 */
@WebListener
public class RootUserInitializer implements ServletContextListener {
	
	@EJB(beanName = "ConcreteUserService")
	private UserService userService;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Nothing to do
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ContextProvider.setContext(event.getServletContext());
		User root = userService.getUser("root");
		if (root == null) {
			try {
				userService.addUser(
						UserFactory.createUser("root", "root@academi-co.ch", "admin", UserType.ADMINISTRATOR.getStringVal())
						);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

}
