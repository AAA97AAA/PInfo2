package dom.documentsManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
	/*
	@Test
	public void testLoadDocument() throws IOException  {
		
		// Create expected result
		String name = "testLoadDocument.png";
		int size = 128 * 128;
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Create file
		String targetPath = "./" + name;
		FileOutputStream stream = new FileOutputStream(targetPath);
		try {
		    stream.write(data);
		} finally {
		    stream.close();
		}
		
		// Apply method
		Document document = spy(DocumentFactory.loadDocument(targetPath));
		
		// Verify that the right follow-up method was called
		verify((ConcreteDocument) document, times(1)).download(targetPath);
	}
	*/
}
