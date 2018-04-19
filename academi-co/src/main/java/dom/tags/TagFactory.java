package dom.tags;

import java.util.HashMap;

/**
 * Instantiator for Tag, MainTag and SecondaryTag
 * 
 * @author kaikoveritch
 *
 */
public class TagFactory {

	static public Tag createTag(String name) {
		return new ConcreteTag(name);
	}
	
	static public MainTag createMainTag(String name) {
		return new ConcreteMainTag(name, new HashMap<Long, SecondaryTag>());
	}
	
	static public SecondaryTag createSecondaryTag(String name, MainTag parent) {
		
		// Instantiate the tag (with its specified parent
		SecondaryTag newTag = new ConcreteSecondaryTag(name, parent);
		
		// Add it to its parent's children
		parent.addChild(newTag);
		
		// Return the newly generated tag
		return newTag;
	}
}
