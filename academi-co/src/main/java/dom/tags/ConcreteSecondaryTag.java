package dom.tags;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import services.utility.View;

/**
 * Specific topic tag implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "SECONDARY_TAGS")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("SECONDARY")
public class ConcreteSecondaryTag extends ConcreteTag implements SecondaryTag, Serializable {
	
	// Serial version (auto-generated)
	private static final long serialVersionUID = -8774104979011662031L;

	@NotNull
	@ManyToOne(targetEntity = ConcreteMainTag.class)
	@JoinColumn(name = "PARENT")
	@JsonView(View.TagChildCentered.class)
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
	protected ConcreteSecondaryTag clone() {
		return new ConcreteSecondaryTag(getName(), parent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + parent.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		ConcreteSecondaryTag other = (ConcreteSecondaryTag) obj;
		if (!parent.equals(other.parent)) {
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
		return "ConcreteMainTag [id=" + getId() + ", name=" + getName() + ", parent=" + getParent().getId() + "]";
	}

}
