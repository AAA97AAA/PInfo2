package services.utility;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import javax.servlet.ServletContext;

import org.junit.Test;

/**
 * Test class for ContextHandler.
 * 
 * @author kaikoveritch
 *
 */
public class ContextHandlerTest {
	
	@Test
	public void testConstructor() {
		new ContextHandler();
	}
	
	@Test
	public void testGetSet() {
		ServletContext context = mock(ServletContext.class);
		ContextHandler.setContext(context);
		assertEquals("Wrong context.", context, ContextHandler.getContext());
	}

}
