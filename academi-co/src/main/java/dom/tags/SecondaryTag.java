package dom.tags;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Specific topic tag definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteSecondaryTag.class)
public interface SecondaryTag extends Tag {

	public MainTag getParent();
}
