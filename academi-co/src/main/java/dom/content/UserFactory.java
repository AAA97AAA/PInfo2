package dom.content;

import java.util.HashMap;

import dom.documentsManager.DocumentFactory;
import dom.inbox.Inbox;

/**
 * Instantiator for registered users
 * 
 * @author kaikoveritch
 *
 */
public class UserFactory {

	static public User createUser(String username, String email, String password, int type, Inbox inbox) {
		String defaultPath = "???"; //TODO
		return new ConcreteUser(username, email, password, DocumentFactory.loadDocument(defaultPath),
				type, "", true, inbox, new HashMap<Long, Post>(), new HashMap<Long, QuestionThread>());
	}
	
	static public User createUser(User user) {
		return user.clone();
	}
}
