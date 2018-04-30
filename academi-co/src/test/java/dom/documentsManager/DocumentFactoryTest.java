package dom.documentsManager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

/**
 * Test class for DocumentFactory
 * 
 * @author Clasino
 *
 */
public class DocumentFactoryTest {
	
	/**
	 * Only instantiates the factory (unimportant)
	 */
	@Test
	public void testConstructor() {
		new DocumentFactory();
	}

	/**
	 * Tests the call creating a new document
	 */
	@Test
	public void testCreateDocument() {
		
		// Setup data to be put in the entity
		String name = "Name";
		int size = 128 * 128;
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Call the construction method
		ConcreteDocument document = (ConcreteDocument) DocumentFactory.createDocument(name, data);
		
		// Verify that the expected object was obtained
		assertEquals("Object wrongly instantiated.", new ConcreteDocument(name, data), document);
	}
	
	/**
	 * Tests the call for copying a document from another document
	 */
	@Test
	public void testCreateDocumentNoArgument() {
		
		// Call method with a mock
		Document document = mock(ConcreteDocument.class);
		DocumentFactory.createDocument(document);
		
		// Verify that the right follow-up method was called
		verify((ConcreteDocument) document, times(1)).clone();
	}
	
	/**
	 * Tests the call for copying a file into a document
	 */
	
	@Test
	public void testLoadDocument() throws IOException  {
		
		// Create expected result
		String name = "testLoadDocument.png";
		int size = 128 * 128;
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Create file
		String targetPath = "./" + name;
		File file = new File(targetPath);
		file.deleteOnExit();
		FileOutputStream stream = new FileOutputStream(file);
	    stream.write(data);
	    stream.close();
		
		// Apply method
		Document documentMock = new ConcreteDocument("interestingName", data);
		Document documentToTest = DocumentFactory.loadDocument(targetPath);
		
		// Verify that the right data were founded in the file	
		assertArrayEquals("Unexpected data in entity.", documentMock.getData(), documentToTest.getData());
		
		// Delete test file
		file.delete();
	}
	
}
