package dom.documentsManager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteDocument
 * 
 * @author Clasino
 * @author kaikoveritch (rework)
 *
 */
public class ConcreteDocumentTest {
	
	// Data to be infused in the test files
	private static byte[] randomData;
	
	/**
	 * Generates the data array for the test files
	 */
	@BeforeClass
	static public void generateRandomData() {
		int size = 128;
		randomData = new byte[size];
		new Random().nextBytes(randomData);
	}
	
	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		long id = 4;
		String name = "Name";
		
		// Construct entity with full constructor call
		ConcreteDocument document = new ConcreteDocument(name, randomData);
		assertEquals("Unexpected name in entity.", name, document.getName());
		assertEquals("Unexpected data in entity.", randomData, document.getData());
		
		// Construct entity with empty constructor call and setters
		ConcreteDocument document2 = new ConcreteDocument();
		document2.setId(id);
		document2.setName(name);
		document2.setData(randomData);
		assertEquals("Unexpected id in entity.", id, document2.getId());
		assertEquals("Unexpected name in entity.", name, document2.getName());
		assertEquals("Unexpected data in entity.", randomData, document2.getData());
	}

	/**
	 * Tests the 'download' method
	 */
	@Test
	public void testDownload() throws IOException {
		
		// Setup expectation
		long id = 4;
		String filename = "testFile.txt";
		
		// Construct comparison entity
		ConcreteDocument document = new ConcreteDocument(filename, randomData);
		document.setId(id);
		
		// Write temporary file to be read
		File tmpfile = new File(filename);
		tmpfile.deleteOnExit();
		FileOutputStream stream = new FileOutputStream(filename);
	    stream.write(randomData);
	    stream.close();
		
		// Call the method and fill the test entity
		document.download(filename);

		// Control result
		assertEquals("Unexpected id in entity.", id, document.getId());
		assertEquals("Unexpected name in entity.", filename, document.getName());
		assertArrayEquals("Unexpected data in entity.", randomData, document.getData());
		
		// Destroy test file
		tmpfile.delete();
		
		// Call invalid file
		document.download("doesNotExist.file");
	}
	
	/**
	 * Tests the 'getFile' method
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetFile() throws NoSuchMethodException, SecurityException, IllegalAccessException,
		IllegalArgumentException, InvocationTargetException {
		
		// Set test parameter
		String fileName = "someFileName.txt";
		
		// Compute expected result
		File expected = new File(fileName);
		
		// Make private method accessible and call it
		ConcreteDocument testDocument = new ConcreteDocument();
		Method getFile = testDocument.getClass().getDeclaredMethod("getFile", String.class);
		getFile.setAccessible(true);
		File actual = (File) getFile.invoke(testDocument, fileName);
		
		// Compare outputs
		assertEquals("Wrong file object generated.", expected, actual);
	}
	
	/**
	 * Tests the 'getStream' method
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	@Test
	public void testGetStream() throws NoSuchMethodException, SecurityException, IllegalAccessException,
		IllegalArgumentException, InvocationTargetException, IOException {
		
		// Set test expectation
		String filename = "spoofFile.txt";
		
		// Write temporary file to be read
		File tmpfile = new File(filename);
		tmpfile.deleteOnExit();
		FileOutputStream outStream = new FileOutputStream(filename);
		outStream.write(randomData);
		outStream.close();
		
		// Compute the expected result
		byte[] expected = new byte[(int) tmpfile.length()];
		FileInputStream stream = new FileInputStream(tmpfile);
		stream.read(expected);
		stream.close();
		
		// Make private method accessible and call it
		ConcreteDocument testDocument = new ConcreteDocument();
		Method getStream = testDocument.getClass().getDeclaredMethod("getStream", File.class);
		getStream.setAccessible(true);
		FileInputStream testedStream = (FileInputStream) getStream.invoke(testDocument, tmpfile);
		byte[] actual = new byte[(int) tmpfile.length()];
		testedStream.read(actual);
		testedStream.close();
		
		// Destroy test file
		tmpfile.delete();
		
		// Compare outputs
		assertArrayEquals("Wrong stream object generated.", expected, actual);
		
		// Control illegal call
		assertNull("Managed to read non-existant file (somehow).", getStream.invoke(testDocument, new File("nonexistant.lol")));
	}
	
	@Test
	public void testGetBytes() throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		// Generate test data
		FileInputStream fakeStream = mock(FileInputStream.class);
		
		// Set stream behavior
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				byte[] inputArray = invocation.getArgument(0);
				for (int i = 0; i < randomData.length; i++) {
					inputArray[i] = randomData[i];
				}
				return null;
			}
		}).when(fakeStream).read(any(byte[].class));
		doThrow(IOException.class).when(fakeStream).close(); // Make 'close' fail "fo' sh*t'n'giggles
		
		// Make private method accessible and call it
		ConcreteDocument testDocument = new ConcreteDocument();
		Method getBytes = testDocument.getClass().getDeclaredMethod("getBytes", FileInputStream.class, int.class);
		getBytes.setAccessible(true);
		byte[] result = (byte[]) getBytes.invoke(testDocument, fakeStream, randomData.length);
		
		// Control result
		assertArrayEquals("Wrong byte array read.", randomData, result);
		
		// Verify follow-up calls
		InOrder order = inOrder(fakeStream);
		order.verify(fakeStream, times(1)).read(any());
		order.verify(fakeStream, times(1)).close();
		
		// Control that failure to open returns null
		doThrow(IOException.class).when(fakeStream).read(any(byte[].class));
		assertNull("Returned non-null value with invalid file.", getBytes.invoke(testDocument, fakeStream, randomData.length));
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
		
		// Construct entity
		ConcreteDocument document = new ConcreteDocument(name, randomData);
		
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
		
		// Construct entity
		ConcreteDocument document = new ConcreteDocument(name, randomData);
		document.setId(id);
		
		// Create expected result
		String expected = "Document [id=" + id + ", name=" + name + "]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, document.toString());
	}
}
