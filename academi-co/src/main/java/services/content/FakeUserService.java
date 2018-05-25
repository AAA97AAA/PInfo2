package services.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import dom.content.Post;
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
		return getUser("username");
	}
	
	@Override
	public User getUser(String username) {
		User user = UserFactory.createUser(username, "email", "password", User.REGISTERED);
		user.setBio("I'm such a cool guy...");
		for (long i = 0; i < 20; i+=2) {
			MainTag subject = TagFactory.createMainTag("subject" + i);
			Tag language = TagFactory.createTag("EnglishMotherFucker");
			Set<SecondaryTag> topics = new HashSet<SecondaryTag>();
			for (long j = 0; j < 3; j++) {
				topics.add(TagFactory.createSecondaryTag("topic" + i + "-" + j, subject));
			}
			user.getPosts().put(i, PostFactory.createQuestionThread(user, "text", "question" + i, subject, language, topics));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (long i = 1; i < 20; i+=2) {
			user.getPosts().put(i, PostFactory.createComment(user, "text", (QuestionThread) user.getPosts().get(2L)));
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

	@Override
	public List<Post> getUserPosts(long id, String order, int from, int length) {
		User user = getUser("username");
		Comparator<Post> comparator;
		if (order == "byDate") {
			comparator = new Comparator<Post>() {
				@Override
				public int compare(Post p1, Post p2) {
					int diffenrence = p2.getCreationDate().compareTo(p1.getCreationDate());
					if (diffenrence != 0) {
						return diffenrence;
					}
					return Long.compare(p1.getId(), p2.getId());
				}
			};
		} else if (order == "byScore") {
			comparator = new Comparator<Post>() {
				@Override
				public int compare(Post p1, Post p2) {
					int difference = Integer.compare(p2.getScore(), p1.getScore());
					if (difference != 0) {
						return difference;
					}
					difference = p2.getCreationDate().compareTo(p1.getCreationDate());
					if (difference != 0) {
						return difference;
					}
					return Long.compare(p1.getId(), p2.getId());
				}
			};
		} else {
			comparator = new Comparator<Post>() {
				@Override
				public int compare(Post p1, Post p2) {
					return Long.compare(p1.getId(), p2.getId());
				}
			};
		}
		List<Post> posts = new ArrayList<Post>(user.getPosts().values());
		Collections.sort(posts, comparator);
		return posts;
	}
}
