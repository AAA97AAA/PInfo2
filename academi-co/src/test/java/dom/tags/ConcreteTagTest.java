package dom.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteTag
 * 
 * @author Aslam CADER
 *
 */

public class ConcreteTagTest {
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test 
	public void TestEntity() {
		
		// Setup data to be put in the entity
		long id = 10;
		String name = "Animation";
		long id2 = 11;
		String name2 = "Sport";

		// Construct entity with full constructor call
		ConcreteTag tag = new ConcreteTag(name);
		assertNotNull("Unexpected Id null in entity.", tag.getId());
		assertEquals("Unexpected name in entity.", name, tag.getName());
		
		tag.setId(id);
		assertEquals("Unexpected id in entity", id, tag.getId());
		
		
		// Construct entity with empty constructor call and setters 
		ConcreteTag tag2 = new ConcreteTag();
		tag2.setId(id2);
		tag2.setName(name2);
		assertEquals("Unexpected id in entity.", id2, tag2.getId());
		assertEquals("Unexpected name in entity.", name2, tag2.getName());
		
		
	}
	
	
	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteTag.class)
		.usingGetClass()
		.withRedefinedSubclass(ConcreteSecondaryTag.class)
		.withRedefinedSubclass(ConcreteMainTag.class)
		.verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity 
		long id = 20;
		String name = "IT";
		
		// Construct entity
		ConcreteTag tag = new ConcreteTag(name);
		
		// Clone entity
		ConcreteTag tagClone = tag.clone();
		
		assertNotSame("Same instance instead of clone.", tag, tagClone);
		assertEquals("Clone has differen values than the original.", tag, tagClone);
	
		// Same test with a defined id
		tag.setId(id);
		tagClone = tag.clone();
		assertNotEquals("Id was copied (should not be the case).", tag, tagClone);
	
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity (and mock 'toString' values)
		long id = 42;
		String name = "Science";

		// Construct Entity
		ConcreteTag tag = new ConcreteTag(name);
		tag.setId(id);
		
		// Create expected result
		String expected = "ConcreteTag [id=" + id + ", name=" + name + "]";
		assertEquals("Incorrect conversion to string.", expected, tag.toString());
		
	}
	

}
