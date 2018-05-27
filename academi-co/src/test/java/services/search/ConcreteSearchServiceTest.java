package services.search;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test class for ConcreteSearchService.
 * 
 * @author kaikoveritch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcreteSearchServiceTest {
	
	@Mock
	private EntityManager entityManager;
	
	@InjectMocks
	private ConcreteSearchService service;

	@Test
	public void testInitIndex() throws InterruptedException {
		// Not testable yet
	}

	@Test
	public void testSearch() {
		// TODO Not implemented yet
	}

}
