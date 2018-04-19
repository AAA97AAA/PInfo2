package dom.tags;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Generic tag implementation
 * (Used for language tags)
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "LANGUAGE_TAGS")
public class ConcreteTag implements Tag, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -3946424231525389110L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(name = "NAME")
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
	protected ConcreteTag clone() throws CloneNotSupportedException {
		return new ConcreteTag(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ConcreteTag other = (ConcreteTag) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ConcreteTag [id=" + id + ", name=" + name + "]";
	}
}
