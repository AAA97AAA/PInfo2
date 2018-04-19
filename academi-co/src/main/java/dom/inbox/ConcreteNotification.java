package dom.inbox;

import java.io.Serializable;

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
		this(body, false);
	}

	ConcreteNotification(String body, boolean wasRead) {
		this.body = body;
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
		return new ConcreteNotification(body, wasRead);
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
		ConcreteNotification other = (ConcreteNotification) obj;
		if (body == null) {
			if (other.body != null) {
				return false;
			}
		} else if (!body.equals(other.body)) {
			return false;
		}
		if (id != other.id) {
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
