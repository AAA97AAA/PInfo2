package dom.tags;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Specific topic tag implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "SECONDARY_TAGS")
public class ConcreteSecondaryTag extends ConcreteTag implements SecondaryTag, Serializable {
	
	// Serial version (auto-generated)
	private static final long serialVersionUID = -8774104979011662031L;

	@NotNull
	@ManyToOne(targetEntity = ConcreteMainTag.class)
	@JoinColumn(name = "PARENT")
	private MainTag parent;

	
	/***** Constructors *****/
	
	ConcreteSecondaryTag() {}

	ConcreteSecondaryTag(String name, MainTag parent) {
		super(name);
		this.parent = parent;
	}

	
	/***** Getters/Setters *****/
	
	@Override
	public MainTag getParent() {
		return parent;
	}

	void setParent(MainTag parent) {
		this.parent = parent;
	}

	
	/***** Utility *****/
	
	@Override
	protected ConcreteSecondaryTag clone() throws CloneNotSupportedException {
		return new ConcreteSecondaryTag(getName(), parent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ConcreteSecondaryTag)) {
			return false;
		}
		ConcreteSecondaryTag other = (ConcreteSecondaryTag) obj;
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ConcreteMainTag [id=" + getId() + ", name=" + getName() + "parent=" + getParent().getName() + "]";
	}

}
