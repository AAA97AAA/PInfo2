package dom.tags;

import org.junit.Test;

public class TagFactoryTest {

	/**
	 * Spoof test (can be deleted).
	 */
	@Test
	public void test() {
		Tag tag = TagFactory.createTag("lol");
		Tag tug = TagFactory.createTag("lol");
		System.out.println(tag == tug);
		System.out.println(tag.equals(tug));
	}

}
