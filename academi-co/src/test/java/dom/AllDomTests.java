package dom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import dom.content.AllContentTests;
import dom.documentsManager.AllDocumentTests;
import dom.inbox.AllInboxTests;
import dom.moderatorsManager.AllModeratorsManagerTests;
import dom.tags.AllTagsTests;

/**
 * Test suite for the whole dom package
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({AllContentTests.class, AllDocumentTests.class, AllInboxTests.class, AllTagsTests.class, AllModeratorsManagerTests.class})
public class AllDomTests {

}
