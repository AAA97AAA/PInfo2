package services.content;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import dom.content.PostFactory;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.UserFactory;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;

/**
 * Fake service for testing purposes
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
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
		return getUserByName("username");
	}
	
	@Override
	public User getUserByName(String username) {
		User user = UserFactory.createUser(username, "email", "password", User.REGISTERED);
		user.setBio("I'm such a cool guy...");
		for (long i = 0; i < 20; i+=2) {
			MainTag subject = TagFactory.createMainTag("subject" + i);
			Tag language = TagFactory.createTag("EnglishMotherFucker");
			Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
			for (long j = 0; j < 3; j++) {
				topics.put(j, TagFactory.createSecondaryTag("topic" + i + "-" + j, subject));
			}
			user.getPosts().put(i, PostFactory.createQuestionThread(user, "text", "question" + i, subject, language, topics));
		}
		for (long i = 1; i < 20; i+=2) {
			user.getPosts().put(i, PostFactory.createComment(user, "text", (QuestionThread) user.getPosts().get((long) 0)));
		}
		return user;
	}

	@Override
	public User addUser(User user) {
		User initializedUser = UserFactory.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getType());
		return initializedUser;
	}

	@Override
	public User modifyUser(long id, User newUser) {
		return newUser;
	}
}
