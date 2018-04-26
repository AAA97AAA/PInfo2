package dom.documentsManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteDocument
 * 
 * @author Clasino
 *
 */
public class ConcreteDocumentTest {
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		long id = 4;
		String name = "Name";
		int size = 128 * 128;
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Construct entity with full constructor call
		ConcreteDocument document = new ConcreteDocument(name, data);
		assertEquals("Unexpected name in entity.", name, document.getName());
		assertEquals("Unexpected data in entity.", data, document.getData());
		
		// Construct entity with empty constructor call and setters
		ConcreteDocument document2 = new ConcreteDocument();
		document2.setId(id);
		document2.setName(name);
		document2.setData(data);
		assertEquals("Unexpected id in entity.", id, document2.getId());
		assertEquals("Unexpected name in entity.", name, document2.getName());
		assertEquals("Unexpected data in entity.", data, document2.getData());
	}

	/**
	 * Tests the 'download' method
	 */
	@Test
	public void testDownload() throws IOException {
		
		// Setup data to be put in the entity
		long id = 4;
		String name = "Name";
		int size = 128 * 128;
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Construct entity
		ConcreteDocument document = new ConcreteDocument(name, data);
		document.setId(id);
		
		// Create expected result
		String name2 = "testDownload.png";
		int size2 = 128 * 128;
		byte[] data2 = new byte[size2];
		new Random().nextBytes(data2);
		
		// Create file
		String targetPath = "./" + name2;
		FileOutputStream stream = new FileOutputStream(targetPath);
		try {
		    stream.write(data2);
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		finally {
		    stream.close();
		}
		
		// Apply download and test
		document.download(targetPath);
		assertEquals("Unexpected id in entity.", id, document.getId());
		assertEquals("Unexpected name in entity.", name2, document.getName());
		// assertEquals("Unexpected data in entity.", data2, document.getData());
	}
	
	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteDocument.class).verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity
		long id = 4;
		String name = "Name";
		int size = 128 * 128;
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Construct entity
		ConcreteDocument document = new ConcreteDocument(name, data);
		
		// Clone entity
		ConcreteDocument documentClone = document.clone();
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", documentClone, document);
		assertEquals("Clone has different values than the original.", document, documentClone);
		
		// Same test with a defined id
		document.setId(id);
		documentClone = document.clone();
		assertNotEquals("Id was copied (should not be the case).", document, documentClone);
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity (and mock 'toString' values)
		long id = 4;
		String name = "Name";
		int size = 128 * 128;
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Construct entity
		ConcreteDocument document = new ConcreteDocument(name, data);
		document.setId(id);
		
		// Create expected result
		String expected = "Document [id=" + id + ", name=" + name + "]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, document.toString());
	}
}
