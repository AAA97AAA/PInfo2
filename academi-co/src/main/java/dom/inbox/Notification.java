package dom.inbox;

import java.time.LocalDateTime;

/**
 * Inbox notification definition
 * 
 * @author kaikoveritch
 *
 */
public interface Notification {

	public long getId();

	public String getBody();

	public void setBody(String body);
	
	public LocalDateTime getCreationDate();

	public boolean isWasRead();

	public void setWasRead(boolean wasRead);
}
