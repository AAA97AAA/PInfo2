package services.utility;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test class for ObjectMapperContextResolver.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ObjectMapperContextResolverTest {
	
	@Test
	public void testObjectMapperContextResolver() {
		new ObjectMapperContextResolver();
	}

	@Test
	public void testGetContext() {
		assertEquals("Wrong mapper returned.", ObjectMapper.class,
				new ObjectMapperContextResolver().getContext(getClass()).getClass());
	}

}
