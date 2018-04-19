package dom.tags;

import java.util.Map;

/**
 * Main academic field tag definition
 * 
 * @author kaikoveritch
 *
 */
public interface MainTag extends Tag {

	public Map<Long, SecondaryTag> getChildren();

	public void addChild(SecondaryTag newChild);
}
