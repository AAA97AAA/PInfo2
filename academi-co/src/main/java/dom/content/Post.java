package dom.content;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Generic post definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcretePost.class)
public interface Post {

	public long getId();

	public User getAuthor();

	public String getContent();

	public LocalDateTime getCreationDate();

	public Set<User> getUpvoters();

	public void addUpvoter(User upvoter);
	
	public void removeUpvoter(User upvoter);

	public Set<User> getDownvoters();

	public void addDownvoter(User downvoter);
	
	public void removeDownvoter(User downvoter);

	public int getScore();

	public boolean isBanned();

	public void setBanned(boolean banned);
}
