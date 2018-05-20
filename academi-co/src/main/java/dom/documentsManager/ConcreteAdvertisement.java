package dom.documentsManager;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Advertisement banner implementation.
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "ADVERTISEMENTS")
public class ConcreteAdvertisement implements Advertisement, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -4473295455778789499L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@OneToOne(targetEntity = ConcreteDocument.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HORIZONTAL_IMAGE")
	private Document horizontalImage;
	
	@NotNull
	@OneToOne(targetEntity = ConcreteDocument.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "VERTICAL_IMAGE")
	private Document verticalImage;

	
	/***** Constructors *****/
	
	public ConcreteAdvertisement() {}

	public ConcreteAdvertisement(Document horizontalImage, Document verticalImage) {
		this.horizontalImage = horizontalImage;
		this.verticalImage = verticalImage;
	}
	
	
	/***** Getters/Setters *****/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Document getHorizontalImage() {
		return horizontalImage;
	}

	public void setHorizontalImage(Document horizontalImage) {
		this.horizontalImage = horizontalImage;
	}

	public Document getVerticalImage() {
		return verticalImage;
	}

	public void setVerticalImage(Document verticalImage) {
		this.verticalImage = verticalImage;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteAdvertisement clone() {
		return new ConcreteAdvertisement(((ConcreteDocument) horizontalImage).clone(),
				((ConcreteDocument) verticalImage).clone());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + horizontalImage.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + verticalImage.hashCode();
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
		if (!(obj instanceof ConcreteAdvertisement)) {
			return false;
		}
		ConcreteAdvertisement other = (ConcreteAdvertisement) obj;
		if (!horizontalImage.equals(other.horizontalImage)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (!verticalImage.equals(other.verticalImage)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ConcreteAdvertisement [id=" + id + ", horizontalImage=" + horizontalImage + ", verticalImage="
				+ verticalImage + "]";
	}
}
