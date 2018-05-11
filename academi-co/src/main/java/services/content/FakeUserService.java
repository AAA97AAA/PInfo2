package services.content;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import dom.content.User;
import dom.content.UserFactory;
import dom.documentsManager.Document;

/**
 * Fake service for testing purposes
 * 
 * @author petrbinko
 *
 */

@Alternative
@Stateless
public class FakeUserService implements UserService {

	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = 2974345918086349674L;

	
	/******************** Services ***********************/

	@Override
	public User getUser(long id) {
		
		return UserFactory.createUser("username", "email", "password", 0);
	
	}

	@Override
	public User addUser(User user) {
		return user;
	}

	@Override
	public User modifyUser(long id, User newUser) {
		return null;
	}

	@Override
	public User modifyUserProfilePicture(long id, Document newProfilePicture) {
		return null;
	}

}
