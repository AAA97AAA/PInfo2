package services.content;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dom.content.Comment;

/**
 * Unit tests for comment service class
 * @author petrbinko
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceRsTest {
	
	
	// Mocks
	@Mock
	Comment fakeComment;
	
	@Mock
	ConcreteCommentService service;
	
	// 
	@InjectMocks
	CommentServiceRs serviceRs;
	
	/**
	 * Unit test for getComment method in the rest implementation of Comment Services
	 */
	@Test
	public void testGetComment() {
		
		long id = ThreadLocalRandom.current().nextLong();
				
		when(service.getComment(id)).thenReturn(fakeComment);
				
		serviceRs.getComment(id);
		
		verify(service, times(1)).getComment(id);
		
	}
	
	/**
	 * Unit test for addComment method in the rest implementation of Comment Services
	 * @throws URISyntaxException
	 */
	@Test 
	public void testAddComment() throws URISyntaxException {
		
		doNothing().when(service).addComment(fakeComment);
		
		serviceRs.addComent(fakeComment);
		
		verify(service, times(1)).addComment(fakeComment);
		
		
	}

}
