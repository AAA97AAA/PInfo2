package dom.inbox;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Inbox notification implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "NOTIFICATIONS")
public class ConcreteNotification implements Notification, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -345606121230271789L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(name = "BODY")
	private String body;
	
	@NotNull
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Column(name = "WAS_READ")
	private boolean wasRead;
	
	
	// Hidden attribute for database (not to be used)
	@ManyToOne(targetEntity = ConcreteInbox.class)
	@JoinColumn(name = "PARENT")
	private Inbox parent;
	Inbox getParent() {return parent;}
	void setParent(Inbox parent) {this.parent = parent;}
	
	
	/***** Constructors *****/

	ConcreteNotification() {}

	ConcreteNotification(String body) {
		this(body, LocalDateTime.now(), false);
	}

	ConcreteNotification(String body, LocalDateTime creationDate, boolean wasRead) {
		this.body = body;
		this.creationDate = creationDate;
		this.wasRead = wasRead;
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
	public String getBody() {
		return body;
	}

	@Override
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	
	void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	@Override
	public boolean isWasRead() {
		return wasRead;
	}

	@Override
	public void setWasRead(boolean wasRead) {
		this.wasRead = wasRead;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteNotification clone() throws CloneNotSupportedException {
		return new ConcreteNotification(body, creationDate, wasRead);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + (wasRead ? 1231 : 1237);
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
		if (!(obj instanceof ConcreteNotification)) {
			return false;
		}
		ConcreteNotification other = (ConcreteNotification) obj;
		if (body == null) {
			if (other.body != null) {
				return false;
			}
		} else if (!body.equals(other.body)) {
			return false;
		}
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		if (wasRead != other.wasRead) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "ConcreteNotification [id=" + id + ", body=" + body + ", wasRead=" + wasRead + "]";
	}
}
