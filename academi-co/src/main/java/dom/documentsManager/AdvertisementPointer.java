package dom.documentsManager;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity pointing to the current advertisement banner displayed on the site.
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "ADVERTISEMENT_POINTER")
public class AdvertisementPointer implements Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = 6473211312895630527L;
	
	static public final long ADDRESS = 0L;

	@Id
	@Column(name = "ID")
	private long id;
	
	@OneToOne(targetEntity = ConcreteAdvertisement.class)
	@JoinColumn(name = "CURRENT")
	private Advertisement current;

	
	/***** Constructors *****/
	
	public AdvertisementPointer() {}

	public AdvertisementPointer(Advertisement current) {
		this.id = ADDRESS; // there can be only one
		this.current = current;
	}
	
	
	/***** Getters/Setters *****/

	public Advertisement getCurrent() {
		return current;
	}

	public void setCurrent(Advertisement current) {
		this.current = current;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((current == null) ? 0 : current.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (!(obj instanceof AdvertisementPointer)) {
			return false;
		}
		AdvertisementPointer other = (AdvertisementPointer) obj;
		if (current == null) {
			if (other.current != null) {
				return false;
			}
		} else if (!current.equals(other.current)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AdvertisementPointer [current=" + current.getId() + "]";
	}
}
