package dom.tags;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.HashMap;

import org.junit.Test;

/**
 * Test class for TagFactory
 * 
 * @author Aslam CADER
 *
 */

public class TagFactoryTest {
	
	/**
	 * Only instantiates the factory (unimportant)
	 */	
	@Test
	public void testConstructor() {
		new TagFactory();
	}

	@Test
	public void testCreateTag() {
		
		// Setup data to be put in the entity
		String name = "GSOC";
		
		// Call the construction method
		ConcreteTag tag = (ConcreteTag) TagFactory.createTag(name);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.", tag, new ConcreteTag(name));
	}

	@Test
	public void testCreateMainTag() {
		
		// Setup data to be put in the entity
		String name = "ISS";
		
		// Call the construction method
		ConcreteMainTag tag = (ConcreteMainTag) TagFactory.createMainTag(name);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.", tag, new ConcreteMainTag(name, new HashMap<Long, SecondaryTag>()));
	}

	@Test
	public void testCreateSecondaryTag() {
		// Setup data to be put in the entity
		String name = "Environment";
		MainTag parent = mock(MainTag.class);
		
		// Call the construction method
		ConcreteTag tag = (ConcreteTag) TagFactory.createSecondaryTag(name, parent);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.", tag, new ConcreteSecondaryTag(name, parent));
	}

}
