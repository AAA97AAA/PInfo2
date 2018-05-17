package services.documentsManager;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lmax.disruptor.ExceptionHandler;

import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;
import services.utility.View;

/**
 * Integration tests for the ConcreteDocumentService class.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Arquillian.class)
public class ConcreteDocumentServiceIntegrationTest {
	
	@Deployment
	static public Archive<?> deploy() {
		return ShrinkWrap.create(WebArchive.class, "test-academi-co-docs.war")
				.addClass(View.class)
				.addPackages(true, Document.class.getPackage())
				.addClass(DocumentService.class)
				.addClass(ConcreteDocumentService.class)
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
	
	private Document testDocument;
	
	@Inject
	private DocumentService service;

	
	@Before
	public void setup() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		testDocument = DocumentFactory.createDocument("test.png", new byte[] {1, 2, -42, 81});
		trx.begin();
		em.persist(testDocument);
		trx.commit();
	}
	
	@After
	public void tearDown() throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		trx.begin();
		Document target = em.find(ConcreteDocument.class, testDocument.getId());
		em.remove(target);
		trx.commit();
	}

	@Test
	public void testFetching() {
		Document result = service.getDocument(testDocument.getId());
		assertEquals("Got the wrong document.", testDocument, result);
	}

	@Test
	public void testUpdating() {
		Document expected = DocumentFactory.createDocument("oolala.png", new byte[] {33, -66, 102});
		Document output = service.modifyProfilePicture(testDocument.getId(), expected);
		assertEquals("Wrong document returned.", expected.getName(), output.getName());
		assertEquals("Wrong document returned.", expected.getData(), output.getData());
		Document inMemoryDocument = service.getDocument(testDocument.getId());
		assertEquals("Document not correctly updated.", output, inMemoryDocument);
	}
}
