package dom.content;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for Vote entity.
 * 
 * @author kaikoveritch
 *
 */
public class VoteTest {

	@Test
	public void testEntity() {
		
		// Test parameters
		long voterId = 42;
		
		// Test full constructor
		Vote vote = new Vote(true, voterId);
		assertTrue("Wrong vote.", vote.isUp());
		assertEquals("Wrong vote.", voterId, vote.getVoterId());
		
		// Test empty constructor
		Vote vote2 = new Vote();
		vote2.setUp(false);
		vote2.setVoter(voterId);
		assertFalse("Wrong vote.", vote2.isUp());
		assertEquals("Wrong vote.", voterId, vote2.getVoterId());
	}

}
