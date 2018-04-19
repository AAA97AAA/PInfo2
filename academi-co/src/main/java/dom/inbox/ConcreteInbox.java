package dom.inbox;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Personal user inbox implementation
 * (stores notifications)
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "INBOXES")
public class ConcreteInbox implements Inbox, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -6870279337721002465L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(targetEntity = ConcreteNotification.class,
			cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
	@MapKeyColumn(name = "ID")
	private Map<Long, Notification> content;
	
	
	/***** Constructors *****/

	ConcreteInbox() {
		content = new HashMap<Long, Notification>();
	}

	ConcreteInbox(Map<Long, Notification> content) {
		this.content = content;
	}
	
	
	/***** Manipulation *****/
	
	@Override
	public Notification getNotification(long id) {
		return content.get(id);
	}

	@Override
	public void addNotification(Notification notification) {
		content.put(notification.getId(), notification);
	}

	@Override
	public void removeNotification(long id) {
		content.remove(id);
	}

	@Override
	public void markNotificationAsRead(long id) {
		content.get(id).setWasRead(true);
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
	public Map<Long, Notification> getContent() {
		return content;
	}

	void setContent(Map<Long, Notification> content) {
		this.content = content;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteInbox clone() throws CloneNotSupportedException {
		return new ConcreteInbox(content);
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
		ConcreteInbox other = (ConcreteInbox) obj;
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String contentTxt = content.toString();
		return "ConcreteInbox [id=" + id + ", content={" + contentTxt.substring(1, contentTxt.length()-1) + "}]";
	}
}
