package dom.tags;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Generic tag definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteTag.class)
public interface Tag {

	public long getId();

	public String getName();

	public void setName(String name);
}
