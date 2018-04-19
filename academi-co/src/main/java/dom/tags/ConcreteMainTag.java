package dom.tags;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Main academic field tag implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "MAIN_TAGS")
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
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
		return true;
	}

	@Override
	protected ConcreteMainTag clone() throws CloneNotSupportedException {
		return new ConcreteMainTag(getName(), children);
	}

	@Override
	public String toString() {
		String allChildrenAsText = children.toString();
		return "ConcreteMainTag [id=" + getId() + ", name="
				+ getName() + "children={"
				+ allChildrenAsText.substring(1, allChildrenAsText.length()-1) + "}]";
	}
}
