package dom.inbox;

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

	public boolean isWasRead();

	public void setWasRead(boolean wasRead);
}
