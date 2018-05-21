package dom.documentsManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for AdvertisementPointer.
 * 
 * @author kaikoveritch
 *
 */
public class AdvertisementPointerTest {

	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test 
	public void TestEntity() {
		
		// Setup data to be put in the entity
		Advertisement fakeAd = mock(Advertisement.class);

		// Construct entity with full constructor call
		AdvertisementPointer pointer = new AdvertisementPointer(fakeAd);
		assertEquals("Unexpected vertical image in entity.", fakeAd, pointer.getCurrent());
		
		// Construct entity with empty constructor call and setters 
		AdvertisementPointer pointer2 = new AdvertisementPointer();
		pointer2.setCurrent(fakeAd);
		assertEquals("Unexpected horizontal image in entity.", fakeAd, pointer2.getCurrent());
	}
	
	
	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(AdvertisementPointer.class).verify();
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity
		Advertisement fakeAd = mock(Advertisement.class);
		when(fakeAd.getId()).thenReturn(0L);

		// Construct entity with full constructor call
		AdvertisementPointer pointer = new AdvertisementPointer(fakeAd);
		
		// Create expected result
		String expected = "AdvertisementPointer [current=" + fakeAd.getId() + "]";
		assertEquals("Wrong string created.", expected, pointer.toString());
	}
}
