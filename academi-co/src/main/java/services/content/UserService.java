package services.content;

import java.io.Serializable;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import dom.content.User;

@Local
public interface UserService extends Serializable {
	
	public User getUser(long id);
	
	public User getUserByName(String username);
	
	public User addUser(@NotNull User user);
	
	public User modifyUser(long id, User newUser);
}
