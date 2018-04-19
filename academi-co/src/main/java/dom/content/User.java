package dom.content;

import java.util.Map;

import dom.documentsManager.Document;
import dom.inbox.Inbox;

/**
 * Registered user definition
 * 
 * @author kaikoveritch
 *
 */
public interface User {

	public long getId();

	public String getUsername();

	public void setUsername(String username);

	public String getEmail();

	public void setEmail(String email);

	public String getPassword();

	public void setPassword(String password);
	
	public Document getProfilePicture();

	public void setProfilePicture(Document profilePicture);

	public int getType();

	public void setType(int type);

	public String getBio();

	public void setBio(String bio);

	public boolean isCanBeModerator();

	public void setCanBeModerator(boolean canBeModerator);
	
	public Inbox getInbox();
	
	public Map<Long, Post> getPosts();

	public void addPost(Post post);

	public Map<Long, QuestionThread> getFollowedThreads();

	public void addFollowedThread(QuestionThread followedThread);
	
	public void removeFollowedThread(long id);
	
	public User clone();
}
