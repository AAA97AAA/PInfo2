package services.search;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test class for SearchResult
 * 
 * @author kaikoveritch
 *
 */
public class SearchResultTest {

	@Test
	public void testEntity() {
		
		// Test parameters
		int length = 42;
		List<Object> results = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			results.add(new Object());
		}
		
		// Test full constructor
		SearchResult<Object> search = new SearchResult<>(length, results);
		assertEquals("Unexpected length", length, search.getLength());
		assertEquals("Unexpected results", results, search.getResults());
		
		// Test empty constructor
		SearchResult<Object> search2 = new SearchResult<Object>();
		search2.setLength(length);
		search2.setResults(results);
		assertEquals("Unexpected length", length, search2.getLength());
		assertEquals("Unexpected results", results, search2.getResults());
	}

}
