package services.content;

import java.io.Serializable;

import javax.ejb.Local;
import javax.persistence.EntityManagerFactory;
import javax.validation.constraints.NotNull;

import dom.content.User;

@Local
public interface UserService extends Serializable {
	
	public User getUser(long id);
	
	public void addUser(@NotNull User user);
	
	public void modifyUser(User oldUser, User newUser);

}
