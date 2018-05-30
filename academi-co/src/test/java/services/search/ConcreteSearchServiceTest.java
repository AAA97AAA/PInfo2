package services.search;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.AllContext;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.FuzzyContext;
import org.hibernate.search.query.dsl.MustJunction;
import org.hibernate.search.query.dsl.PhraseContext;
import org.hibernate.search.query.dsl.PhraseMatchingContext;
import org.hibernate.search.query.dsl.PhraseTermination;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.RangeContext;
import org.hibernate.search.query.dsl.RangeMatchingContext;
import org.hibernate.search.query.dsl.RangeMatchingContext.FromRangeContext;
import org.hibernate.search.query.dsl.RangeTerminationExcludable;
import org.hibernate.search.query.dsl.TermContext;
import org.hibernate.search.query.dsl.TermMatchingContext;
import org.hibernate.search.query.dsl.TermTermination;
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
	
	@Mock
	private AllContext all;
	
	@SuppressWarnings("rawtypes")
	@Mock
	private BooleanJunction<BooleanJunction> junction;
	
	
	@Before
	public void setup() {
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
	
	@Test
	public void testSearchByTitle() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		// Mocks
		TermContext context = mock(TermContext.class);
		FuzzyContext fuzzy = mock(FuzzyContext.class);
		TermMatchingContext matching = mock(TermMatchingContext.class);
		TermTermination termination = mock(TermTermination.class);
		when(builder.bool()).thenReturn(junction);
		when(junction.boostedTo(anyFloat())).thenReturn(junction);
		when(junction.should(any(Query.class))).thenReturn(junction);
		when(builder.keyword()).thenReturn(context);
		when(context.fuzzy()).thenReturn(fuzzy);
		when(fuzzy.withEditDistanceUpTo(anyInt())).thenReturn(fuzzy);
		when(fuzzy.withPrefixLength(anyInt())).thenReturn(fuzzy);
		when(fuzzy.onField("title")).thenReturn(matching);
		when(matching.matching(anyString())).thenReturn(termination);
		when(termination.createQuery()).thenReturn(query);
		when(junction.createQuery()).thenReturn(query);
		
		// Access method
		Method searchByTitle = search.getClass().getDeclaredMethod("searchByTitle", String.class, QueryBuilder.class);
		searchByTitle.setAccessible(true);
		
		// Test null topics
		Query result = (Query) searchByTitle.invoke(search, null, builder);
		verify(all, times(1)).createQuery();
		assertSame("Wrong query returned.", query, result);
		
		// Test named topics
		List<String> args = new ArrayList<String>();
		int length = 5;
		for (int i = 0; i < length; i++) {
			String arg = "term" + i;
			args.add(arg);
		}
		String terms = String.join(" ", args);
		result = (Query) searchByTitle.invoke(search, terms, builder);
		for (String arg: args) {
			verify(matching, times(1)).matching(arg);
		}
		assertSame("Wring query returned.", query, result);
	}
	
	@Test
	public void testSearchBySubject() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		
		// Mocks
		PhraseContext phraseContext = mock(PhraseContext.class);
		PhraseMatchingContext phraseMatch = mock(PhraseMatchingContext.class);
		PhraseTermination termination = mock(PhraseTermination.class);
		when(builder.phrase()).thenReturn(phraseContext);
		when(phraseContext.onField("subject.name")).thenReturn(phraseMatch);
		when(phraseMatch.sentence(anyString())).thenReturn(termination);
		when(termination.createQuery()).thenReturn(query);
		
		// Access method
		Method searchBySubject = search.getClass().getDeclaredMethod("searchBySubject", String.class, QueryBuilder.class);
		searchBySubject.setAccessible(true);
		
		// Test null subject
		Query result = (Query) searchBySubject.invoke(search, null, builder);
		verify(all, times(1)).createQuery();
		assertSame("Wrong query returned.", query, result);
		
		// Test named subject
		String subject = "SomeWhatever Subject";
		result = (Query) searchBySubject.invoke(search, subject, builder);
		verify(phraseMatch, times(1)).sentence(subject);
		verify(termination, times(1)).createQuery();
		assertSame("Wrong query returned.", query, result);
	}
	
	@Test
	public void testSearchByTopics() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		
		// Mocks
		MustJunction must = mock(MustJunction.class);
		TermContext context = mock(TermContext.class);
		TermMatchingContext matching = mock(TermMatchingContext.class);
		TermTermination termination = mock(TermTermination.class);
		when(builder.bool()).thenReturn(junction);
		when(junction.must(any(Query.class))).thenReturn(must);
		when(must.must(any(Query.class))).thenReturn(must);
		when(builder.keyword()).thenReturn(context);
		when(context.onField("topics.name")).thenReturn(matching);
		when(matching.matching(anyString())).thenReturn(termination);
		when(termination.createQuery()).thenReturn(query);
		when(must.createQuery()).thenReturn(query);
		
		// Access method
		Method searchByTopics = search.getClass().getDeclaredMethod("searchByTopics", String.class, QueryBuilder.class);
		searchByTopics.setAccessible(true);
		
		// Test null topics
		Query result = (Query) searchByTopics.invoke(search, null, builder);
		verify(all, times(1)).createQuery();
		assertSame("Wrong query returned.", query, result);
		
		// Test named topics
		List<String> args = new ArrayList<String>();
		int length = 5;
		for (int i = 0; i < length; i++) {
			String arg = "topic" + i;
			args.add(arg);
		}
		String topics = String.join(" ", args);
		result = (Query) searchByTopics.invoke(search, topics, builder);
		for (String arg: args) {
			verify(matching, times(1)).matching(arg);
		}
		assertSame("Wring query returned.", query, result);
	}
	
	@Test
	public void testSearchByAuthor() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		
		// Mocks
		PhraseContext phraseContext = mock(PhraseContext.class);
		PhraseMatchingContext phraseMatch = mock(PhraseMatchingContext.class);
		PhraseTermination termination = mock(PhraseTermination.class);
		when(builder.phrase()).thenReturn(phraseContext);
		when(phraseContext.boostedTo(anyFloat())).thenReturn(phraseContext);
		when(phraseContext.onField("author.username")).thenReturn(phraseMatch);
		when(phraseMatch.sentence(anyString())).thenReturn(termination);
		when(termination.createQuery()).thenReturn(query);
		
		// Access method
		Method searchByAuthor = search.getClass().getDeclaredMethod("searchByAuthor", String.class, QueryBuilder.class);
		searchByAuthor.setAccessible(true);
		
		// Test null author
		Query result = (Query) searchByAuthor.invoke(search, null, builder);
		verify(all, times(1)).createQuery();
		assertSame("Wrong query returned.", query, result);
		
		// Test named author
		String name = "Jean-Paul Gaultier";
		result = (Query) searchByAuthor.invoke(search, name, builder);
		verify(phraseMatch, times(1)).sentence(name);
		verify(termination, times(1)).createQuery();
		assertSame("Wrong query returned.", query, result);
	}
	
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
		verify(all, times(1)).createQuery();
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
