package dom.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteSecondaryTag
 * @author Aslam CADER
 *
 */
public class ConcreteSecondaryTagTest {
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity 
		String name = "Physics";
		MainTag parent = mock(MainTag.class);
		long id = 42;
		
		
		// Construct entity with full constructor call
		ConcreteSecondaryTag secondaryTag = new ConcreteSecondaryTag(name, parent);
		assertEquals("Unexpected name in entity", name, secondaryTag.getName());
		assertEquals("Unexected parent in entity.", parent, secondaryTag.getParent());
		
		
		
		// Construct entity with empty constructor call and setters
		ConcreteSecondaryTag secondaryTag2 = new ConcreteSecondaryTag();
		secondaryTag2.setName(name);
		secondaryTag2.setId(id);
		secondaryTag2.setParent(parent);
		assertEquals("Unexpected name in entity", name, secondaryTag2.getName());
		assertEquals("Unexpected id in entity", id, secondaryTag2.getId());
		assertEquals("Unexected parent in entity.", parent, secondaryTag2.getParent());

	}
	

	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteSecondaryTag.class)
			.usingGetClass()
			.withRedefinedSuperclass()
			.verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity
		String name = "Law";
		MainTag parent = mock(MainTag.class);
		long id = 42;
		
		// Construct entity
		ConcreteSecondaryTag secondaryTag = new ConcreteSecondaryTag(name, parent);
		
		// Clone entity
		ConcreteSecondaryTag secondaryTagClone = secondaryTag.clone();		
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", secondaryTagClone, secondaryTag);
		assertEquals("Clone has different values than the original.", secondaryTagClone, secondaryTag);
		
		
		// Same test with a defined id
		secondaryTag.setId(id);
		secondaryTagClone = secondaryTag.clone();
		assertNotEquals("Id was copied (should not be the case).", secondaryTagClone, secondaryTag);

		
		
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity
		String name = "Law";
		String parentName = "University";
		MainTag parent = mock(MainTag.class);
		when(parent.getName()).thenReturn(parentName);		
		long id = 42;
		
		// Construct entity
		ConcreteSecondaryTag secondaryTag = new ConcreteSecondaryTag(name, parent);
		secondaryTag.setId(id);
		
		// Create expected result
		String expected = "ConcreteMainTag [id=" + id + ", name=" + name + "parent=" + parentName+ "]";

		// Control result
		assertEquals("Incorrect conversion to string.", expected, secondaryTag.toString());

			
	}



}
