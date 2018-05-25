package dom.tags;

import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Main academic field tag definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteMainTag.class)
public interface MainTag extends Tag {

	public Set<SecondaryTag> getChildren();

	public void addChild(SecondaryTag newChild);
}
