package services.documentsManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
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
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lmax.disruptor.ExceptionHandler;

import dom.documentsManager.Advertisement;
import dom.documentsManager.AdvertisementPointer;
import dom.documentsManager.ConcreteAdvertisement;
import dom.documentsManager.DocumentFactory;
import services.utility.View;

/**
 * Integration tests for ConcreteAdvertisementService.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Arquillian.class)
public class ConcreteAdvertisementServiceIntegrationTest {
	
	@Deployment
	static public Archive<?> deploy() {
		return ShrinkWrap.create(WebArchive.class, "test-academi-co-ads.war")
				.addClass(View.class)
				.addPackages(true, Advertisement.class.getPackage())
				.addClass(AdvertisementService.class)
				.addClass(ConcreteAdvertisementService.class)
				.addPackages(true, LogManager.class.getPackage())
				.addPackages(true, ExceptionHandler.class.getPackage())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("log4j2-test.xml", "log4j2.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	UserTransaction trx;
	
	@Inject
	AdvertisementService service;
	
	@Before
	public void setup() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		trx.begin();
		AdvertisementPointer pointer = em.find(AdvertisementPointer.class, AdvertisementPointer.ADDRESS);
		if (pointer != null) {
			pointer.setCurrent(null);
			em.remove(pointer);
		}
		trx.commit();
		trx.begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<ConcreteAdvertisement> delete = cb.createCriteriaDelete(ConcreteAdvertisement.class);
		delete.from(ConcreteAdvertisement.class);
		em.createQuery(delete).executeUpdate();
		trx.commit();
	}

	@Test
	public void testGetAllAdvertisements() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Create test data
		int n = 3;
		List<Advertisement> all = new ArrayList<Advertisement>();
		for (int i = 0; i < n; i++) {
			all.add(DocumentFactory.createAdvertisement("h" + i, new byte[] {(byte) i, (byte) -i},
					"v" + i, new byte[] {(byte) -i, (byte) i}));
		}
		
		// Fill table
		trx.begin();
		for (int i = 0; i < n; i++) {
			em.persist(all.get(i));
		}
		trx.commit();
		
		// Set expectation
		all.sort((a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		
		// Call method under test and control output
		List<Advertisement> result = service.getAllAdvertisements();
		assertEquals("Wrong list fetched.", all, result);
	}

	@Test
	public void testGetCurrentAdvertisement() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Try to fetch on a non-existent pointer
		Advertisement ad = service.getCurrentAdvertisement();
		assertNull("Advertisement generated despite no pointer.", ad);
		
		// Generate and store pointer
		AdvertisementPointer pointer = new AdvertisementPointer(null);
		trx.begin();
		em.persist(pointer);
		trx.commit();
		
		// Try to fetch a non-existent banner
		ad = service.getCurrentAdvertisement();
		assertNull("Advertisement generated despite empty roster.", ad);
		
		// Add banners and set pointer
		Advertisement testAd1 = DocumentFactory.createAdvertisement("h1", new byte[] {1, -20, -3},
				"v1", new byte[] {42, 0, 3});
		Advertisement testAd2 = DocumentFactory.createAdvertisement("h2", new byte[] {10, -2, -30},
				"v2", new byte[] {2, 10, 30});
		trx.begin();
		em.persist(testAd1);
		em.persist(testAd2);
		trx.commit();
		trx.begin();
		AdvertisementPointer realPointer = em.find(AdvertisementPointer.class, AdvertisementPointer.ADDRESS);
		realPointer.setCurrent(testAd2);
		trx.commit();
		
		// Control that the correct banner is being fetched
		Advertisement result = service.getCurrentAdvertisement();
		assertEquals("Wrong banner fetched.", testAd2, result);
	}

	@Test
	public void testNextAdvertisement() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Control that a pointer is generated if none exist
		long out = service.nextAdvertisement();
		trx.begin();
		AdvertisementPointer pointer = em.find(AdvertisementPointer.class, AdvertisementPointer.ADDRESS);
		trx.commit();
		assertEquals("Wrong output for null pointer.", -1, out);
		assertNotNull("No pointer generated.", pointer);
		
		// Add banners to the database
		Advertisement testAd1 = DocumentFactory.createAdvertisement("h1", new byte[] {1, -20, -3},
				"v1", new byte[] {42, 0, 3});
		Advertisement testAd2 = DocumentFactory.createAdvertisement("h2", new byte[] {10, -2, -30},
				"v2", new byte[] {2, 10, 30});
		Advertisement testAd3 = DocumentFactory.createAdvertisement("h3", new byte[] {12, -22, -33},
				"v3", new byte[] {12, 110, 10});
		trx.begin();
		em.persist(testAd1);
		em.persist(testAd2);
		em.persist(testAd3);
		trx.commit();
		List<Advertisement> all = new ArrayList<Advertisement>();
		all.add(testAd1); all.add(testAd2); all.add(testAd3);
		all.sort((a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		
		// Control cycle
		assertEquals("Cycle is wrong.", all.get(0).getId(), service.nextAdvertisement());
		assertEquals("Cycle is wrong.", all.get(1).getId(), service.nextAdvertisement());
		assertEquals("Cycle is wrong.", all.get(2).getId(), service.nextAdvertisement());
		assertEquals("Cycle is wrong.", all.get(0).getId(), service.nextAdvertisement());
		
		// Control resetting
		service.nextAdvertisement(); // points to all[1]
		trx.begin();
		Advertisement toRemove1 = em.find(ConcreteAdvertisement.class, all.get(0).getId());
		Advertisement toRemove2 = em.find(ConcreteAdvertisement.class, all.get(2).getId());
		em.remove(toRemove1);
		em.remove(toRemove2);
		trx.commit();
		assertEquals("Was resetted to the wrong value.", all.get(1).getId(), service.nextAdvertisement());
	}

	@Test
	public void testAddAdvertisement() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Add a banner to the databse
		Advertisement testAd = DocumentFactory.createAdvertisement("h", new byte[] {1, 1}, "h", new byte[] {2, 2});
		Advertisement result = service.addAdvertisement(testAd);
		
		// Control that the result holds the same data
		assertEquals("Unexpected values in storage result.", testAd.getHorizontalImage(), result.getHorizontalImage());
		assertEquals("Unexpected values in storage result.", testAd.getVerticalImage(), result.getVerticalImage());
		
		// Control that the data is consistent with what is in the database
		trx.begin();
		Advertisement inMemory = em.find(ConcreteAdvertisement.class, result.getId());
		trx.commit();
		assertEquals("Wrong data in the database.", result, inMemory);
	}

	@Test
	public void testRemoveAdvertisement() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		// Add a banner to the database
		Advertisement testAd = DocumentFactory.createAdvertisement("h", new byte[] {1, 1}, "h", new byte[] {2, 2});
		trx.begin();
		em.persist(testAd);
		trx.commit();
		
		// Attempt to remove it
		service.removeAdvertisement(testAd.getId());
		
		// Control that it is indeed not present in the database anymore
		trx.begin();
		Advertisement inMemory = em.find(ConcreteAdvertisement.class, testAd.getId());
		trx.commit();
		assertNull("Banner should not be present in the database anymore.", inMemory);
	}

}
