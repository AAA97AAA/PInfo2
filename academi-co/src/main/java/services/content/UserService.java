package services.content;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import dom.content.Post;
import dom.content.User;

/**
 * User services definition.
 * 
 * @author kaikoveritch
 *
 */
@Local
public interface UserService extends Serializable {
	
	public User getUser(long id);
	
	public User getUser(String username);
	
	public User addUser(User user) throws Throwable;
	
	public User modifyUser(long id, User newUser) throws Throwable;
	
	public List<Post> getUserPosts(long id, String order, int from, int length);
}
