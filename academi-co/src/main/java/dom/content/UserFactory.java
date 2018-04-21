package dom.content;

import java.util.HashMap;

import dom.documentsManager.DocumentFactory;
import dom.inbox.InboxFactory;

/**
 * Instantiator for registered users
 * 
 * @author kaikoveritch
 *
 */
public class UserFactory {

	static public User createUser(String username, String email, String password, int type) {
		String defaultPath = "???"; //TODO: Define default path for profile pic
		return new ConcreteUser(username, email, password, DocumentFactory.loadDocument(defaultPath),
				type, "", true, InboxFactory.createInbox(), new HashMap<Long, Post>(),
				new HashMap<Long, QuestionThread>());
	}
	
	static public User createUser(User user) {
		return ((ConcreteUser) user).clone();
	}
}
