package dom.content;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Generic post definition
 * 
 * @author kaikoveritch
 *
 */
public interface Post {

	public long getId();

	public User getAuthor();

	public String getContent();

	public LocalDateTime getCreationDate();

	public Map<Long, User> getUpvoters();

	public void addUpvoter(User upvoter);
	
	public void removeUpvoter(long id);

	public Map<Long, User> getDownvoters();

	public void addDownvoter(User downvoter);
	
	public void removeDownvoter(long id);

	public int getScore();

	public boolean getIsBanned();

	public void setIsBanned(boolean isBanned);
}
