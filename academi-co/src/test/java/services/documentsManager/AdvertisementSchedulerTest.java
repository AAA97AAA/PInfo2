package services.documentsManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 * Test class for AdvertisementScheduler.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdvertisementSchedulerTest {

	@Mock
	private AdvertisementService service;
	
	@InjectMocks
	private AdvertisementScheduler scheduler;
	
	@Mock
	private ServletContextEvent event;
	
	@Mock
	private ServletContext context;
	
	@Mock
	Timer fakeTimer;
	
	@Before
	public void setup() {
		when(event.getServletContext()).thenReturn(context);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock arg0) throws Throwable {
				((Timer) arg0.getArgument(1)).cancel();
				return null;
			}
		}).when(context).setAttribute(anyString(), any(Timer.class));
		when(context.getAttribute("timer")).thenReturn(fakeTimer);
	}
	
	@Test
	public void testContextInitialized() {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		scheduler.contextInitialized(event);
		verify(context, times(1)).setAttribute(captor.capture(), any(Timer.class));
		assertEquals("Wrong context attribute set.", "timer", captor.getValue());
	}

	@Test
	public void testContextDestroyed() {
		scheduler.contextDestroyed(event);
		verify(fakeTimer, times(1)).cancel();
		verify(context, times(1)).removeAttribute("timer");
	}

}
