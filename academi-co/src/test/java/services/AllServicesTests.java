package services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import services.content.AllContentServiceTests;
import services.documentsManager.AllDocumentServiceTests;

/**
 * Test suit for services
 * @author petrbinko
 *
 */
@RunWith(Suite.class)
@SuiteClasses({AllContentServiceTests.class, AllDocumentServiceTests.class})
public class AllServicesTests {

}
