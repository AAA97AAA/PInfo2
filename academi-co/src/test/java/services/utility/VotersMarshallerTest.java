package services.utility;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

import dom.content.User;

/**
 * Test class for VotersMarshaller.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VotersMarshallerTest {

	@Mock
	private JsonGenerator generator;
	
	@Mock
	private SerializerProvider provider;
	
	@Captor
	ArgumentCaptor<Set<Long>> captor;
	
	@Test
	public void testSerializeSetOfUserJsonGeneratorSerializerProvider() throws JsonProcessingException, IOException {
		
		// Test parameters
		Set<Long> expected = new HashSet<Long>();
		Set<User> users = new HashSet<User>();
		for (long i = 0; i < 4; i++) {
			expected.add(i);
			User user = mock(User.class);
			when(user.getId()).thenReturn(i);
			users.add(user);
		}
		
		// Call method under test
		new VotersMarshaller().serialize(users, generator, provider);
		
		// Verify the follow-up call
		verify(generator, times(1)).writeObject(captor.capture());
		assertEquals("Wrong agruments marshalled.", expected, captor.getValue());
	}

}
