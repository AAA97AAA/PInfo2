package dom.inbox;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Inbox notification definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteNotification.class)
public interface Notification {

	public long getId();

	public String getBody();

	public void setBody(String body);
	
	public LocalDateTime getCreationDate();

	public boolean isWasRead();

	public void setWasRead(boolean wasRead);
}
