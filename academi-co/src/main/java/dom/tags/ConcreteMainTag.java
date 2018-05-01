package dom.tags;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Main academic field tag implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "MAIN_TAGS")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("MAIN")
public class ConcreteMainTag extends ConcreteTag implements MainTag, Serializable {
	
	// Serial version (auto-generated)
	private static final long serialVersionUID = 8446984008568752093L;
	
	@OneToMany(targetEntity = ConcreteSecondaryTag.class, mappedBy = "parent",
			cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKeyColumn(name = "ID")
	private Map<Long, SecondaryTag> children;

	
	/***** Constructors *****/
	
	ConcreteMainTag() {
		super();
		children = new HashMap<Long, SecondaryTag>();
	}

	ConcreteMainTag(String name, Map<Long, SecondaryTag> children) {
		super(name);
		this.children = children;
	}
	
	
	/***** Manipulation *****/
	
	@Override
	public void addChild(SecondaryTag newChild) {
		children.put(newChild.getId(), newChild);
	}
	
	
	/***** Getters/Setters *****/

	@Override
	public Map<Long, SecondaryTag> getChildren() {
		return children;
	}

	void setChildren(Map<Long, SecondaryTag> children) {
		this.children = children;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteMainTag clone() {
		return new ConcreteMainTag(getName(), children);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ConcreteMainTag)) {
			return false;
		}
		ConcreteMainTag other = (ConcreteMainTag) obj;
		if (children == null) {
			if (other.children != null) {
				return false;
			}
		} else if (!children.equals(other.children)) {
			return false;
		}
		if (getId() != other.getId()) {
			return false;
		}
		if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String allChildrenAsText = children.toString();
		return "ConcreteMainTag [id=" + getId() + ", name="
				+ getName() + ", children={"
				+  allChildrenAsText.substring(1, allChildrenAsText.length()-1) + "}]";
		
	}
}
