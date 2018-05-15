package services.tags;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

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
	public static Archive<?> deploy(){
		return ShrinkWrap.create(WebArchive.class, "test-academi-co-tags.war")
				.addPackages(true, View.class.getPackage())
				.addPackages(true, Tag.class.getPackage())
				.addClass(TagService.class)
				.addClass(ConcreteTagService.class)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	// Persistence context to be given to the service
	@PersistenceContext
	private EntityManager entityManager;
	
	// Transaction to simulate the JTA automatic transactions
	@Inject
	UserTransaction transaction;

	@Test
	public void testWithAStupidName() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NotSupportedException, SystemException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException {
		
		// Insert the persistence context in the service
		TagService service = new ConcreteTagService();
		Field manager = service.getClass().getDeclaredField("entityManager");
		manager.setAccessible(true);
		manager.set(service, entityManager);
		
		// Add an entity to the DB
		transaction.begin();
		Tag language = service.addTag(TagFactory.createTag("lol"));
		transaction.commit();
		
		// Extract an entity with the same id
		transaction.begin();
		Tag output = service.getLanguageTag(language.getId());
		transaction.commit();
		
		// Control that they are the same (successful persisting and fetching)
		assertEquals("Got the wrong tag.", language, output);
	}

}
