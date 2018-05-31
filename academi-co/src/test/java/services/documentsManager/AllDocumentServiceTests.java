package services.documentsManager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for all document service class
 * @author petrbinko
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ConcreteDocumentServiceTest.class, AdvertisementSchedulerTest.class, AdvertisementServiceRsTest.class })
public class AllDocumentServiceTests {

}
