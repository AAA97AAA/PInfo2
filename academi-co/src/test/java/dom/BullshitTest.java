package dom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * This class serves no purpose. It simply highlights
 * how to use loggers. =)
 * 
 * @author kaikoveritch
 *
 */
public class BullshitTest {
	
	static Logger logger = LogManager.getLogger(BullshitTest.class);

	@Test
	public void test() {
		logger.debug("salut lol !");
		logger.warn("#crafulgang");
		logger.error("Ohno !!!");
	}

}