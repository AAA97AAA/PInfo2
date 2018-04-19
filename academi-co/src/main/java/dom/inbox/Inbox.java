package dom.inbox;

import java.util.Map;

/**
 * User personal inbox definition
 * 
 * @author kaikoveritch
 *
 */
public interface Inbox {
	
	public long getId();

	public Map<Long, Notification> getContent();

	public Notification getNotification(long id);
	
	public void addNotification(Notification notification);
	
	public void removeNotification(long id);
	
	public void markNotificationAsRead(long id);
}
