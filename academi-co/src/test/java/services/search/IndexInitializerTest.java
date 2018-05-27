package services.search;

import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletContextEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class for IndexInitializer.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class IndexInitializerTest {
	
	@Mock
	private SearchService service;
	
	@InjectMocks
	private IndexInitializer initializer;
	
	@Mock
	ServletContextEvent fakeContext;
	
	@Test
	public void testContextDestroyed() throws InterruptedException {

		// TODO not working yet
		// Disable system out/err
		PrintStream out = System.out;
		PrintStream err = System.err;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {}
		}));
		System.setErr(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {}
		}));
		
		// Call method under test
		initializer.contextInitialized(fakeContext);
		
		// Call with exception
		doThrow(InterruptedException.class).when(service).initIndex();
		initializer.contextInitialized(fakeContext);
		
		// Reset system out/err
		System.setOut(out);
		System.setErr(err);
	}

	@Test
	public void testContextInitialized() {
		initializer.contextDestroyed(fakeContext);
	}

}
