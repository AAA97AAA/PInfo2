package dom.content;

import java.util.HashMap;

import dom.documentsManager.DocumentFactory;
import dom.inbox.InboxFactory;
import services.content.UserServiceRs;

/**
 * Instantiator for registered users
 * 
 * @author kaikoveritch
 *
 */
public class UserFactory {

	static public String DEFAULT_PATH = UserServiceRs.context.getRealPath("WEB-INF/classes/defaultPP.png");
	
	static public User createUser(String username, String email, String password, int type) {
		System.out.println(DEFAULT_PATH); //TODO remove when tests over
		return new ConcreteUser(username, email, password, DocumentFactory.loadDocument(DEFAULT_PATH),
				type, "", true, InboxFactory.createInbox(), new HashMap<Long, Post>(),
				new HashMap<Long, QuestionThread>());
	}
	
	static public User createUser(User user) {
		return ((ConcreteUser) user).clone();
	}
}
