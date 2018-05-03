package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import dom.AllDomTests;
import jaxrsConfig.JAXRSConfigurationTest;
import services.AllServicesTests;

/**
 * Main test suite: Launches all the tests in the project.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(Suite.class)
@SuiteClasses({AllDomTests.class, AllServicesTests.class, JAXRSConfigurationTest.class})
public class AllTests {

}
