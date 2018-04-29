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
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
		byte[] data = new byte[ThreadLocalRandom.current().nextInt(1, 100)];
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
		
		// Setup expectation
		long id = 4;
		String filename = "testFile.txt";
		int size = ThreadLocalRandom.current().nextInt(1, 1024);
		byte[] data = new byte[size];
		new Random().nextBytes(data);
		
		// Construct comparison entity
		ConcreteDocument document = new ConcreteDocument(filename, data);
		document.setId(id);
		
		// Write temporary file to be read
//		String targetPath = "./" + name2;
		File tmpfile = new File(filename);
		tmpfile.deleteOnExit();
		FileOutputStream stream = new FileOutputStream(filename);
	    stream.write(data);
	    stream.close();
		
		// Call the method and fill the test entity
		document.download(filename);

		// Control result
		assertEquals("Unexpected id in entity.", id, document.getId());
		assertEquals("Unexpected name in entity.", filename, document.getName());
		assertArrayEquals("Unexpected data in entity.", data, document.getData());
		
		// Destroy test file
		tmpfile.delete();
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
		
		// Set test parameter (current class file)
		String fileName = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
		
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
		
		// Set test parameter (current class file)
		File file = new File(getClass().getResource(getClass().getSimpleName() + ".class").getPath());
		
		// Compute the expected result
		byte[] expected = new byte[(int) file.length()];
		FileInputStream stream = new FileInputStream(file);
		stream.read(expected);
		stream.close();
		
		// Make private method accessible and call it
		ConcreteDocument testDocument = new ConcreteDocument();
		Method getStream = testDocument.getClass().getDeclaredMethod("getStream", File.class);
		getStream.setAccessible(true);
		FileInputStream testedStream = (FileInputStream) getStream.invoke(testDocument, file);
		byte[] actual = new byte[(int) file.length()];
		testedStream.read(actual);
		testedStream.close();
		
		// Compare outputs
		assertArrayEquals("Wrong stream object generated.", expected, actual);
		
		// Control illegal call
		assertNull("Managed to read non-existant file (somehow).", getStream.invoke(testDocument, new File("nonexistant.lol")));
	}
	
	@Test
	public void testGetBytes() throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		// Generate test data
		int size = ThreadLocalRandom.current().nextInt(1, 100);
		byte[] byteArray = new byte[size];
		FileInputStream fakeStream = mock(FileInputStream.class);
		
		// Set stream behavior
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				byte[] inputArray = invocation.getArgument(0);
				for (int i = 0; i < size; i++) {
					inputArray[i] = byteArray[i];
				}
				return null;
			}
		}).when(fakeStream).read(any(byte[].class));
		doThrow(IOException.class).when(fakeStream).close(); // Make 'close' fail "fo' sh*t'n'giggles
		
		// Make private method accessible and call it
		ConcreteDocument testDocument = new ConcreteDocument();
		Method getBytes = testDocument.getClass().getDeclaredMethod("getBytes", FileInputStream.class, int.class);
		getBytes.setAccessible(true);
		byte[] result = (byte[]) getBytes.invoke(testDocument, fakeStream, size);
		
		// Control result
		assertArrayEquals("Wrong byte array read.", byteArray, result);
		
		// Verify follow-up calls
		InOrder order = inOrder(fakeStream);
		order.verify(fakeStream, times(1)).read(any());
		order.verify(fakeStream, times(1)).close();
		
		// Control that failure to open returns null
		doThrow(IOException.class).when(fakeStream).read(any(byte[].class));
		assertNull("Returned non-null value with invalid file.", getBytes.invoke(testDocument, fakeStream, size));
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
