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

import dom.content.ConcreteUser;
import dom.documentsManager.ConcreteDocument;

/**
 * Unit tests for user service class
 * @author petrbinko
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceRsTest {
	
	// Mocks
	@Mock
	ConcreteUser fakeUser;
	
	@Mock
	ConcreteDocument fakeDocument;
	
	@Mock
	ConcreteUserService service;
	
	// Instance of QuestionThreadServiceRs in which mocks will be injected
	@InjectMocks
	UserServiceRs serviceRs;
	
	/**
	 * Unit test for method GetUser from rest implementation of UserService
	 */
	@Test
	public void testGetUser() {
		
		// Random id generation
		long id = ThreadLocalRandom.current().nextLong();
		
		// Specifying behavior for mock objects related to calls in the service
		when(service.getUser(id)).thenReturn(fakeUser);
		
		// Calling method getUser on rest service
		serviceRs.getUser(id);
		
		// Verifying right calls on method
		verify(service, times(1)).getUser(id);
	}
	
//	/**
//	 * Unit test for addUser method in the rest implementation of UserService
//	 * @throws URISyntaxException 
//	 */
//	@Test
//	public void testAddUser() throws URISyntaxException {
//		
//		when(service.addUser(fakeUser)).thenReturn(fakeUser);
//		
//		serviceRs.addUser(fakeUser);
//		
//		verify(service, times(1)).addUser(fakeUser);
//
//	}
	
	/**
	 * Unit test for modifyUser method in the rest implementation of UserService
	 * @throws Throwable 
	 */
	@Test
	public void testModifyUser() throws Throwable {
		
		// Random id generation
		long id = ThreadLocalRandom.current().nextLong();
		
		when(service.modifyUser(id, fakeUser)).thenReturn(fakeUser);
		
		serviceRs.modifyUser(id, fakeUser);
		
		verify(service, times(1)).modifyUser(id, fakeUser);
		
		
	}
	
//	/**
//	 * Unit test for modifyUserProfilePicture in the rest implementation of UserService
//	 */
//	@Test
//	public void testModifyProfilePicture() {
//		
//		// Random id generation
//		long id = ThreadLocalRandom.current().nextLong();
//		
//		serviceRs.modifyUserProfilePicture(id, fakeDocument);
//		
//		verify(service, times(1)).modifyUserProfilePicture(id, fakeDocument);
//		
//	}

}
