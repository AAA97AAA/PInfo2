package services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import dom.tags.AllTagsTests;
import services.content.AllContentServiceTests;
import services.documentsManager.AllDocumentServiceTests;
import services.inbox.AllInboxServiceTests;
import services.tags.AllTagServiceTests;

/**
 * Test suit for services
 * @author petrbinko
 *
 */
@RunWith(Suite.class)
@SuiteClasses({AllContentServiceTests.class, AllDocumentServiceTests.class, AllInboxServiceTests.class, AllTagsTests.class,
	AllTagServiceTests.class})
public class AllServicesTests {

}
