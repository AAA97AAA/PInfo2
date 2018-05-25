package services.search;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import dom.content.ConcreteQuestionThread;
import dom.content.QuestionThread;

/**
 * Search services implementation.
 * 
 * @author kaikoveritch
 *
 */
@Default
@Stateless
public class ConcreteSearchService implements SearchService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -3219177626572123359L;
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Initializes the Lucene index for pre-existing database entries
	 */
	@Override
	public void initIndex() throws InterruptedException {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
	}

	/**
	 * Receives an SearchInput object, routes each individual request contained in it to
	 * a custom request generating method and then combines each produced request and
	 * returns a list of search results in the given range.
	 */
	@Override
	public SearchResult<QuestionThread> search(SearchInput input, int from, int size) {
		
		// Generate an entity manager and a query builder for fulltext search
		FullTextEntityManager fullText = Search.getFullTextEntityManager(entityManager);
		QueryBuilder builder = fullText.getSearchFactory()
				.buildQueryBuilder().forEntity(ConcreteQuestionThread.class).get();
		
		// Build the main query as an AND of each subquery
		Query query = builder.bool()
				.must(searchByTitle(input.getTitle(), builder))
				.must(searchBySubject(input.getSubject(), builder))
				.must(searchByTopics(input.getTopics(), builder))
				.must(searchByAuthor(input.getAuthor(), builder))
				.must(searchByDate(input.getFrom(), input.getTo(), builder))
				.createQuery();
		
		// Convert to JPA query in the given range and order
		FullTextQuery jpaquery = fullText.createFullTextQuery(query, ConcreteQuestionThread.class);
		Sort order = new Sort(
				SortField.FIELD_SCORE,
				new SortField("score", SortField.Type.INT, true),
				new SortField("creationDate", SortField.Type.STRING, false),
				SortField.FIELD_DOC);
		jpaquery.setSort(order);
		jpaquery.setFirstResult(from);
		if (size > 0) {
			jpaquery.setMaxResults(size);
		}
		
		// Fetch and return result list and its length
		List<QuestionThread> typedList = new ArrayList<QuestionThread>();
		for (Object thread: jpaquery.getResultList()) {
			typedList.add((QuestionThread) thread);
		}
		return new SearchResult<QuestionThread>(jpaquery.getResultSize(), typedList);
	}

	/**
	 * Given a query builder and space-separated keywords to find in a thread's title, returns
	 * a fuzzy query on threads' titles
	 * @param title
	 * @param builder
	 * @return fuzzy query
	 */
	private Query searchByTitle(String title, QueryBuilder builder) {
		if (isInvalid(title)) {
			return builder.all().createQuery();
		}
		String[] keywords = title.split(" ");
		BooleanJunction<?> junction = builder.bool().boostedTo(1.5f);
		for (String keyword: keywords) {
			junction = junction.should(builder.keyword()
					.fuzzy().withEditDistanceUpTo(2).withPrefixLength(1)
					.onField("title").matching(keyword).createQuery());
		}
		return junction.createQuery();
	}
	
	/**
	 * Given a query builder and the name of a MainTag, returns a query strictly matching threads
	 * tagged with the given subject
	 * @param subject
	 * @param builder
	 * @return strict query
	 */
	private Query searchBySubject(String subject, QueryBuilder builder) {
		if (isInvalid(subject)) {
			return builder.all().createQuery();
		}
		return builder.phrase()
				.onField("subject.name").sentence(subject).createQuery();
	}
	
	// TODO language search queries not implemented yet
	
	/**
	 * Given a query builder and a space-separated list of SecondaryTag names, returns a query
	 * strictly matching threads with the given topics
	 * @param topics
	 * @param builder
	 * @return strict AND query
	 */
	private Query searchByTopics(String topics, QueryBuilder builder) {
		if (isInvalid(topics)) {
			return builder.all().createQuery();
		}
		BooleanJunction<?> junction = builder.bool();
		String[] keywords = topics.split(" ");
		for (String keyword: keywords) {
			junction = junction.must(builder.keyword()
					.onField("topics.name").matching(keyword).createQuery());
		}
		return junction.createQuery();
	}
	
	/**
	 * Given a query builder and a username, returns a query strictly matching threads posted by
	 * the user designated by the username
	 * @param author
	 * @param builder
	 * @return strict exact query
	 */
	private Query searchByAuthor(String author, QueryBuilder builder) {
		if (isInvalid(author)) {
			return builder.all().createQuery();
		}
		return builder.phrase().boostedTo(2f)
				.onField("author.username").sentence(author).createQuery();
	}
	
	/**
	 * Given two dates and a query builder, returns a query matching only threads in the range of
	 * dates between the two given
	 * @param from
	 * @param to
	 * @param builder
	 * @return range query
	 */
	private Query searchByDate(LocalDateTime from, LocalDateTime to, QueryBuilder builder) {
		if (from == null && to == null) {
			return builder.all().createQuery();
		}
		if (from == null) {
			from = LocalDateTime.of(0, 1, 1, 0, 0); // Jesus-time
		}
		if (to == null) {
			to = LocalDateTime.now().plusDays(1); // tomorrow
		}
		return builder.range().onField("creationDate").from(from).to(to).createQuery();
	}
	
	/**
	 * Controls whether a fulltext query is valid or not i.e if it is not null and not empty
	 * not accounting for spaces and punctuation
	 * @param query
	 * @return
	 */
	private boolean isInvalid(String query) {
		if (query == null) {
			return true;
		}
		String cleanedQuery = query.replaceAll("([^A-Za-z0-9])+", "");
		return cleanedQuery.isEmpty();
	}
}
