package services.content;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import dom.content.Post;
import dom.content.User;

@Local
public interface UserService extends Serializable {
	
	public User getUser(long id);
	
	public User getUser(String username);
	
	public User addUser(@NotNull User user);
	
	public User modifyUser(long id, User newUser);
	
	public List<Post> getUserPosts(long id, String order);
}
