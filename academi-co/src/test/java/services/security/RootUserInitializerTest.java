package services.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.User;
import services.content.UserService;

/**
 * Test class for RootUserInitializer.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RootUserInitializerTest {
	
	@Mock
	private UserService service;
	
	@InjectMocks
	private RootUserInitializer initializer;
	
	@Mock
	private ServletContextEvent event;
	
	@Mock
	private ServletContext context;
	
	@Spy
	private Throwable error;
	

	@Test
	public void testContextDestroyed() {
		initializer.contextDestroyed(event); // nothing happens
	}

	@Test
	public void testContextInitialized() throws Throwable {
		
		// Add behavior
		when(event.getServletContext()).thenReturn(context);
		when(context.getRealPath(anyString()))
		.thenReturn(new File("src/test/resources").getAbsolutePath() + "/defaultPP.png");
		
		// Test new root required case
		initializer.contextInitialized(event);
		verify(service, times(1)).getUser("root");
		
		// Test new root creation error
		when(service.addUser(any(User.class))).thenThrow(error);
		PrintStream err = System.err;
		System.setErr(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {}
		}));
		initializer.contextInitialized(event);
		System.setErr(err);
		verify(error, times(1)).printStackTrace();
		
		// Test pre-existing root case
		when(service.getUser("root")).thenReturn(mock(User.class));
		initializer.contextInitialized(event);
		verify(service, times(2)).addUser(any(User.class));
	}

}
