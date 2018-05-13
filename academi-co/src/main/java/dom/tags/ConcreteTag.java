package dom.tags;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import services.utility.View;

/**
 * Generic tag implementation
 * (Used for language tags)
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "LANGUAGE_TAGS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TAG_TYPE")
@DiscriminatorValue("LANGUAGE")
public class ConcreteTag implements Tag, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -3946424231525389110L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.TagMinimal.class)
	private long id;
	
	@NotNull
	@Column(name = "NAME")
	@JsonView(View.TagBase.class)
	private String name;


	/***** Constructors *****/
	
	ConcreteTag() {}

	ConcreteTag(String name) {
		this.name = name;
	}
	
	
	/***** Getters/Setters *****/

	@Override
	public long getId() {
		return id;
	}

	void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteTag clone() {
		return new ConcreteTag(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + name.hashCode();
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
		ConcreteTag other = (ConcreteTag) obj;
		if (id != other.id) {
			return false;
		}
		if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ConcreteTag [id=" + id + ", name=" + name + "]";
	}
}
