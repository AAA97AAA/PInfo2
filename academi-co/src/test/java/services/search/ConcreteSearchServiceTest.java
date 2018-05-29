package services.search;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.AllContext;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.RangeContext;
import org.hibernate.search.query.dsl.RangeMatchingContext;
import org.hibernate.search.query.dsl.RangeMatchingContext.FromRangeContext;
import org.hibernate.search.query.dsl.RangeTerminationExcludable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class for ConcreteSearchService.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcreteSearchServiceTest {
	
	@Mock
	private EntityManager entityManager;
	
	@InjectMocks
	private ConcreteSearchService search;
	
	@Mock
	private QueryBuilder builder;
	
	@Mock
	private Query query;
	
	@Mock
	private FromRangeContext<LocalDateTime> fromRange;
	
	@Mock
	private RangeTerminationExcludable toRange;
	
	
	@Before
	public void setup() {
		AllContext all = mock(AllContext.class);
		when(builder.all()).thenReturn(all);
		when(all.createQuery()).thenReturn(query);
	}
	

	@Test
	public void testInitIndex() throws InterruptedException {
		// Not testable yet
	}

	@Test
	public void testSearch() {
		// Not testable yet
	}
	
	// TODO More methods to test
	
	@Test
	public void testSearchByDate() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		
		// Test parameters
		LocalDateTime from = LocalDateTime.now().minusDays(2);
		LocalDateTime to = LocalDateTime.now();
		
		// Mocks
		RangeContext rangeContext = mock(RangeContext.class);
		RangeMatchingContext matchingRange = mock(RangeMatchingContext.class);
		when(builder.range()).thenReturn(rangeContext);
		when(rangeContext.onField(anyString())).thenReturn(matchingRange);
		when(matchingRange.from(any(LocalDateTime.class))).thenReturn(fromRange);
		when(fromRange.to(any(LocalDateTime.class))).thenReturn(toRange);
		when(toRange.createQuery()).thenReturn(query);
		
		// Access method
		Method searchByDate = search.getClass()
				.getDeclaredMethod("searchByDate", LocalDateTime.class, LocalDateTime.class, QueryBuilder.class);
		searchByDate.setAccessible(true);
		
		// Test both bounds null
		Query result = (Query) searchByDate.invoke(search, null, null, builder);
		assertSame("Wrong query returned", query, result);
		
		// Test from null
		result = (Query) searchByDate.invoke(search, null, to, builder);
		verify(matchingRange, times(1)).from(LocalDateTime.of(0, 1, 1, 0, 0));
		verify(fromRange, times(1)).to(to);
		assertSame("Wrong query returned", query, result);
		
		// Test to null
		result = (Query) searchByDate.invoke(search, from, null, builder);
		verify(matchingRange, times(1)).from(from);
		verify(fromRange, times(1)).to(argThat(date -> ((LocalDateTime) date).isAfter(LocalDateTime.now())));
		assertSame("Wrong query returned", query, result);
		
		// Test bounds set
		result = (Query) searchByDate.invoke(search, from, to, builder);
		verify(matchingRange, times(2)).from(from);
		verify(fromRange, times(2)).to(to);
		assertSame("Wrong query returned", query, result);
	}

	@Test
	public void testIsInvalid() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		
		// Access method
		Method isInvalid = search.getClass().getDeclaredMethod("isInvalid", String.class);
		isInvalid.setAccessible(true);
		
		// Test valid query
		boolean result = (Boolean) isInvalid.invoke(search, "Akj hahsh,, dsl???");
		assertFalse("Valid query not recognized.", result);
		
		// Test empty query
		result = (Boolean) isInvalid.invoke(search, ". .??+");
		assertTrue("Empty query accepted.", result);
		
		// Test null query
		result = (Boolean) isInvalid.invoke(search, (Object) null);
		assertTrue("Null query accepted.", result);
	}
}
