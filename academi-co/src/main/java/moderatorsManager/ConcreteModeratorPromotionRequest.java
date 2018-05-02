package moderatorsManager;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import dom.content.ConcreteUser;
import dom.content.User;

/**
 * User request to become a moderator implementation.
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "MODERATOR_PROMOTION_REQUESTS")
public class ConcreteModeratorPromotionRequest implements ModeratorPromotionRequest, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = 2139764326001295048L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@OneToOne(targetEntity = ConcreteUser.class)
	@JoinColumn(name = "ORIGINATOR")
	private User originator;
	
	
	/***** Constructors *****/

	public ConcreteModeratorPromotionRequest() {}

	public ConcreteModeratorPromotionRequest(User originator) {
		this.originator = originator;
	}
	
	
	/***** Manipulation *****/
	
	/**
	 * Accepts a user request to be a moderator by changing his/her status.
	 * Note: Whether it is accepted or refused (no change in that case), the request must
	 * 		be deleted afterward.
	 */
	@Override
	public void accept() {
		originator.setType(User.MODERATOR);
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
	public User getOriginator() {
		return originator;
	}

	void setOriginator(User originator) {
		this.originator = originator;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteModeratorPromotionRequest clone() {
		return new ConcreteModeratorPromotionRequest(originator);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + originator.hashCode();
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
		if (!(obj instanceof ConcreteModeratorPromotionRequest)) {
			return false;
		}
		ConcreteModeratorPromotionRequest other = (ConcreteModeratorPromotionRequest) obj;
		if (id != other.id) {
			return false;
		}
		if (!originator.equals(other.originator)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ConcreteModeratorPromotionRequest [id=" + id + ", originator=" + originator + "]";
	}
}
