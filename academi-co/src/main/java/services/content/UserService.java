package services.content;

import java.io.Serializable;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import dom.content.User;
import dom.documentsManager.Document;

@Local
public interface UserService extends Serializable {
	
	public User getUser(long id);
	
	public void addUser(@NotNull User user);
	
	public void modifyUser(long id, User newUser);

	public void modifyUserProfilePicture(long id, Document newProfilePicture);

}
