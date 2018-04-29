package dom.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteMainTag
 * @author Aslam CADER
 *
 */
public class ConcreteMainTagTest {
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		String name = "Bio";
		long id = 42;
		Map<Long, SecondaryTag> children = new HashMap<Long, SecondaryTag>();
		long min = 1;
		long max = 50;
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			children.put(i, mock(SecondaryTag.class));
		}
		
		// Construct entity with full constructor call
		ConcreteMainTag mainTag = new ConcreteMainTag(name, children);
		mainTag.setId(id);
		assertEquals("Unexepected id in entity.", id, mainTag.getId());
		assertEquals("Unexepected name in entity.", name, mainTag.getName());
		assertEquals("Unexepected children in entity.", children, mainTag.getChildren());
		
		// Construct entity with empty constructor call and setters
		ConcreteMainTag mainTag2 = new ConcreteMainTag();
		assertNotNull("Unexpected Null in entity.", mainTag2.getChildren());
		mainTag2.setChildren(children);
		mainTag2.setId(id);
		mainTag2.setName(name);
		assertEquals("Unexepected id in entity.", id, mainTag2.getId());
		assertEquals("Unexepected name in entity.", name, mainTag2.getName());
		assertEquals("Unexepected children in entity.", children, mainTag2.getChildren());
	
	}
	

	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteMainTag.class)
			.withRedefinedSuperclass()
			.verify();
	}

	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity
		String name = "Astronomy";
		long newId = 43;
		Map<Long, SecondaryTag> children = new HashMap<Long, SecondaryTag>();
		long min = 1;
		long max = 50;
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			children.put(i, mock(SecondaryTag.class));
		}
		
		// Construct entity
		ConcreteMainTag mainTag = new ConcreteMainTag(name, children);
		
		// Clone entity
		ConcreteMainTag mainTagClone = mainTag.clone();		
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", mainTag, mainTagClone);
		assertEquals("Clone has different values than the original.", mainTag, mainTagClone);
		
		
		// Same test with a defined id
		mainTag.setId(newId);
		mainTagClone = mainTag.clone();
		assertNotEquals("Id was copied (should not be the case).", mainTag, mainTagClone);

		
	}
	
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity (and mock 'toString' values)
		String name = "Economy";
		long id = 42;
		Map<Long, SecondaryTag> children = new HashMap<Long, SecondaryTag>();
		long min = 1;
		long max = 50;
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			children.put(i, mock(SecondaryTag.class));
		}
		when(children.toString()).thenReturn("ok");
		
		
		// Construct entity
		ConcreteMainTag mainTag = new ConcreteMainTag(name, children);
		mainTag.setId(id);
		
		// Create expected result
		String allChildrenAsText = children.toString();
		String expected = "ConcreteMainTag [id=" + id + ", name="
				+ name + ", children={"
				+  allChildrenAsText.substring(1, allChildrenAsText.length()-1) + "}]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, mainTag.toString());

	}
	

	@Test
	public void testAddChild() {
		
		// Setup data to be put in the entity
		String name = "Economy";
		Map<Long, SecondaryTag> children = new HashMap<Long, SecondaryTag>();
		long min = 1;
		long max = 50;
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			children.put(i, mock(SecondaryTag.class));
		}
		when(children.toString()).thenReturn("ok");
		
		
		// Construct entity
		ConcreteMainTag mainTag = new ConcreteMainTag(name, children);

		
		// mock
		ConcreteSecondaryTag newChild = mock(ConcreteSecondaryTag.class);
		
		// adding to the entity and to our children
		mainTag.addChild(newChild);
		children.put(null, newChild);
		
		// Control result
		assertEquals("Unexpected added Children.", mainTag.getChildren(), children);
		
		
	}

}
