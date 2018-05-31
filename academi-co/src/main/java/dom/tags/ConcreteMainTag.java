package dom.tags;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import services.utility.View;

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
@NamedQuery(name = "MainTag.getAll", query = "SELECT m FROM ConcreteMainTag m")
public class ConcreteMainTag extends ConcreteTag implements MainTag, Serializable {
	
	// Serial version (auto-generated)
	private static final long serialVersionUID = 8446984008568752093L;
	
	@OneToMany(targetEntity = ConcreteSecondaryTag.class, mappedBy = "parent",
			cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy
	@JsonView(View.TagParentCentered.class)
	private Set<SecondaryTag> children;

	
	/***** Constructors *****/
	
	ConcreteMainTag() {
		super();
		children = new HashSet<SecondaryTag>();
	}

	ConcreteMainTag(String name, Set<SecondaryTag> children) {
		super(name);
		this.children = children;
	}
	
	
	/***** Manipulation *****/
	
	@Override
	public void addChild(SecondaryTag newChild) {
		children.add(newChild);
	}
	
	
	/***** Getters/Setters *****/

	@Override
	public Set<SecondaryTag> getChildren() {
		return children;
	}

	void setChildren(Set<SecondaryTag> children) {
		this.children = children;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteMainTag clone() {
		return new ConcreteMainTag(getName(), children);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
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
		if (getId() != other.getId()) {
			return false;
		}
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
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
