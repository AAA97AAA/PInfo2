package services.content;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.QuestionThread;

/**
 * Unit tests for rest question thread service class
 * @author petrbinko
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionThreadServiceRsTest {

	
	// Mocks
	@Mock
	QuestionThread fakeQuestionThread;
	
	@Mock
	ConcretePostService service;
	
	// Instance of QuestionThreadServiceRs in which mocks will be injected
	@InjectMocks
	PostServiceRs serviceRs;
	
	/**
	 * Unit test for getComment method in the rest implementation of QuestionThreadServices
	 */
	@Test
	public void testGetQuestionThread() {
		
		// Random id generation
		long id = ThreadLocalRandom.current().nextLong();
		
		// Specifying behavior for mock objects related to calls in the service
		when(service.getQuestionThread(id)).thenReturn(fakeQuestionThread);
		
		// Calling method getQuestionThread on rest service
		serviceRs.getQuestionThread(id);
		
		// Verifying right calls on method
		verify(service, times(1)).getQuestionThread(id);
	}
	
//	/**
//	 * Unit test for addQuestionThread method in the rest implementation of QuestionThreadService
//	 * @throws URISyntaxException 
//	 */
//	@Test
//	public void testAddQuestionThread() throws URISyntaxException {
//		
//		when(service.addQuestionThread(fakeQuestionThread)).thenReturn(fakeQuestionThread);
//		
//		serviceRs.addQuestionThread(fakeQuestionThread);
//		
//		verify(service, times(1)).addQuestionThread(fakeQuestionThread);
//
//	}

}
