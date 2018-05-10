package dom.tags;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Main academic field tag definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteMainTag.class)
public interface MainTag extends Tag {

	public Map<Long, SecondaryTag> getChildren();

	public void addChild(SecondaryTag newChild);
}
