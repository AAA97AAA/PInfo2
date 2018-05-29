package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import services.content.ConcretePostServiceIntegrationTest;
import services.content.ConcreteUserServiceIntegrationTest;
import services.documentsManager.ConcreteAdvertisementServiceIntegrationTest;
import services.documentsManager.ConcreteDocumentServiceIntegrationTest;
import services.tags.ConcreteTagServiceIntegrationTest;

/**
 * Main integration test suite: Launches all the integration tests.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteAdvertisementServiceIntegrationTest.class, ConcreteDocumentServiceIntegrationTest.class,
	ConcreteUserServiceIntegrationTest.class, ConcretePostServiceIntegrationTest.class,
	ConcreteTagServiceIntegrationTest.class })
public class AllIntegrationTests {

}
