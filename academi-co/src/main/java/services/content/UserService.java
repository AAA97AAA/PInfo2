package services.content;

import java.io.Serializable;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import dom.content.User;
import dom.documentsManager.Document;

@Local
public interface UserService extends Serializable {
	
	public User getUser(long id);
	
	public User addUser(@NotNull User user);
	
	public User modifyUser(long id, User newUser);

	public User modifyUserProfilePicture(long id, Document newProfilePicture);

}
