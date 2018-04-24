package dom.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import nl.jqno.equalsverifier.EqualsVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ConcretePostTest {
	
	@Mock
	ConcreteUser mockAuthor;
	@Mock
	ConcreteUser mockUserA;
	@Mock
	ConcreteUser mockUserB;
	
	@Test
	public void testDataCreation() {
		
		EqualsVerifier.forClass(ConcretePost.class).verify();
	}

}
