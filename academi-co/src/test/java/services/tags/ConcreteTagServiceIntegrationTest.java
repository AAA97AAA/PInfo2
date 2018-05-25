package services.tags;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.logging.log4j.LogManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lmax.disruptor.ExceptionHandler;

import dom.content.QuestionThread;
import dom.documentsManager.Document;
import dom.inbox.Inbox;
import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;
import services.utility.View;

/**
 * Integration tests for JPA on the ConcreteTagService class.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Arquillian.class)
public class ConcreteTagServiceIntegrationTest {
	
	/**
	 * Setting up the container (JVM) in which the tests will be run
	 * -> Here: the wildfly server (should be running).
	 * 
	 * @return
	 */
	@Deployment
	public static WebArchive deploy(){
		return ShrinkWrap.create(WebArchive.class, "test-academi-co-tags.war")
				.addClass(View.class)
				.addPackage(Tag.class.getPackage())
				.addClass(TagService.class)
				.addClass(ConcreteTagService.class)
				.addPackage(QuestionThread.class.getPackage())
				.addPackage(Inbox.class.getPackage())
				.addPackage(Document.class.getPackage())
				.addPackages(true, LogManager.class.getPackage())
				.addPackages(true, ExceptionHandler.class.getPackage())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("log4j2-test.xml", "log4j2.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private UserTransaction trx;
	
	@Inject
	private TagService service;
	

	@Test
	public void testGetAllSubjects() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Clear
		trx.begin();
		CriteriaDelete<ConcreteSecondaryTag> rmTopics = em.getCriteriaBuilder().createCriteriaDelete(ConcreteSecondaryTag.class);
		rmTopics.from(ConcreteSecondaryTag.class);
		em.createQuery(rmTopics).executeUpdate();
		trx.commit();
		trx.begin();
		CriteriaDelete<ConcreteMainTag> rmTags = em.getCriteriaBuilder().createCriteriaDelete(ConcreteMainTag.class);
		rmTags.from(ConcreteMainTag.class);
		em.createQuery(rmTags).executeUpdate();
		trx.commit();
		
		// Fill and set expectation
		List<MainTag> expected = new ArrayList<MainTag>();
		for (int i = 0; i < 5; i++) {
			MainTag subject = TagFactory.createMainTag("subject" + i);
			trx.begin();
			em.persist(subject);
			trx.commit();
			expected.add(subject);
			for (int j = 0; j < 3; j++) {
				SecondaryTag topic = TagFactory.createSecondaryTag("topic" + i + "-" + j, subject);
				trx.begin();
				em.persist(topic);
				trx.commit();
			}
		}
		
		// Get list
		List<MainTag> result = service.getAllSubjects();
		
		// Control expectation
		assertEquals("Wrong subject list.", new HashSet<>(expected), new HashSet<>(result));
	}
	
	@Test
	public void testLanguageTagStorgae() {
		
		// Add an entity to the DB
		Tag language = service.addTag(TagFactory.createTag("lol"));
		
		// Fetch an entity with the same id
		Tag output = service.getLanguageTag(language.getId());
		
		// Control that they are the same (successful persisting and fetching)
		assertEquals("Got the wrong tag.", language, output);
	}

	@Test
	public void testMainTagStorage() {
		
		// Add entity to the DB
		MainTag subject = service.addTag(TagFactory.createMainTag("pouloulou"));
		
		// Fetch an entity with the same id
		MainTag output = service.getMainTag(subject.getId());
		
		// Control that they are the same (successful persisting and fetching)
		assertEquals("Got the wrong tag.", subject, output);
	}
	
	@Test
	public void testSecondaryTagStorage() {

		// Add parent to DB
		MainTag subject = service.addTag(TagFactory.createMainTag("parent"));
		
		// Create a topic linked to the parent and store it
		SecondaryTag topic = service.addTag(subject.getId(), TagFactory.createSecondaryTag("huhu", subject));
		
		// Fetch an entity with the same id
		SecondaryTag output = service.getSecondaryTag(topic.getId());
		
		// Control that they are the same (successful persisting and fetching)
		assertEquals("Got the wrong tag.", topic, output);
	}
}
