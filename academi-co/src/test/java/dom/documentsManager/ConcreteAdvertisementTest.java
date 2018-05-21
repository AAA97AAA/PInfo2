package dom.documentsManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteAdvertisement.
 * 
 * @author kaikoveritch
 *
 */
public class ConcreteAdvertisementTest {

	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test 
	public void TestEntity() {
		
		// Setup data to be put in the entity
		long id = 5;
		Document horizontalImage = mock(Document.class);
		Document verticalImage = mock(Document.class);

		// Construct entity with full constructor call
		ConcreteAdvertisement ad = new ConcreteAdvertisement(horizontalImage, verticalImage);
		assertEquals("Unexpected horizontal image in entity.", horizontalImage, ad.getHorizontalImage());
		assertEquals("Unexpected vertical image in entity.", verticalImage, ad.getVerticalImage());
		
		// Construct entity with empty constructor call and setters 
		ConcreteAdvertisement ad2 = new ConcreteAdvertisement();
		ad2.setId(id);
		ad2.setHorizontalImage(horizontalImage);
		ad2.setVerticalImage(verticalImage);
		assertEquals("Unexpected horizontal image in entity.", horizontalImage, ad2.getHorizontalImage());
		assertEquals("Unexpected vertical image in entity.", verticalImage, ad2.getVerticalImage());
	}
	
	
	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteAdvertisement.class).verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity
		long id = 5;
		ConcreteDocument horizontalImage = mock(ConcreteDocument.class);
		when(horizontalImage.clone()).thenReturn(horizontalImage);
		ConcreteDocument verticalImage = mock(ConcreteDocument.class);
		when(verticalImage.clone()).thenReturn(verticalImage);
		
		// Construct entity
		ConcreteAdvertisement ad = new ConcreteAdvertisement(horizontalImage, verticalImage);
		
		// Clone entity
		ConcreteAdvertisement adClone = ad.clone();
		
		assertNotSame("Same instance instead of clone.", ad, adClone);
		assertEquals("Clone has differen values than the original.", ad, adClone);
	
		// Same test with a defined id
		ad.setId(id);
		adClone = ad.clone();
		assertNotEquals("Id was copied (should not be the case).", ad, adClone);
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity
		long id = 5;
		Document horizontalImage = mock(Document.class);
		when(horizontalImage.toString()).thenReturn("horizontal");
		Document verticalImage = mock(Document.class);
		when(verticalImage.toString()).thenReturn("vertical");
		
		// Construct entity
		ConcreteAdvertisement ad = new ConcreteAdvertisement(horizontalImage, verticalImage);
		ad.setId(id);
		
		// Create expected result
		String expected = "ConcreteAdvertisement [id=" + id + ", horizontalImage=" + horizontalImage
				+ ", verticalImage=" + verticalImage + "]";
		assertEquals("Wrong string created.", expected, ad.toString());
	}
}
