package dom.inbox;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * User personal inbox definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteInbox.class)
public interface Inbox {
	
	public long getId();

	public Map<Long, Notification> getContent();

	public Notification getNotification(long id);
	
	public void addNotification(Notification notification);
	
	public void removeNotification(long id);
	
	public void markNotificationAsRead(long id);
}
