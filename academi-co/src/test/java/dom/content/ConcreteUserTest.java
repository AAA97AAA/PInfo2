package dom.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import dom.inbox.ConcreteInbox;
import dom.inbox.Inbox;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test class for ConcreteUser
 * 
 * @author kaikoveritch
 *
 */
public class ConcreteUserTest {

	/**
	 * Tests that the constructors and getters/setters work as intended
	 */
	@Test
	public void testEntity() {
		
		// Setup data to be put in the entity
		long id = 2;
		String username = "Patrik Brunschwick";
		String email = "email@domain.com";
		String password = "supersecret";
		Document profilePicture = mock(ConcreteDocument.class);
		int type = ConcreteUser.REGISTERED;
		String bio = "Interesting life or whatever...";
		boolean canBeModerator = true;
		Inbox inbox = mock(ConcreteInbox.class);
		long min = 1; long max = 100;
		Map<Long, Post> posts = new HashMap<Long, Post>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			posts.put(i, mock(ConcretePost.class));
		}
		Map<Long, QuestionThread> followedThreads = new HashMap<Long, QuestionThread>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			followedThreads.put(i, mock(ConcreteQuestionThread.class));
		}
		
		// Construct entity with full constructor call
		ConcreteUser user = new ConcreteUser(username, email, password, profilePicture, type, bio,
				canBeModerator, inbox, posts, followedThreads);
		assertEquals("Unexpected username in entity.", username, user.getUsername());
		assertEquals("Unexpected email in entity.", email, user.getEmail());
		assertEquals("Unexpected password in entity.", password, user.getPassword());
		assertEquals("Unexpected profile picture in entity.", profilePicture, user.getProfilePicture());
		assertEquals("Unexpected user type in entity.", type, user.getType());
		assertEquals("Unexpected bio in entity.", bio, user.getBio());
		assertEquals("Unexpected 'canBeModerator' status in entity.", canBeModerator, user.isCanBeModerator());
		assertEquals("Unexpected inbox in entity.", inbox, user.getInbox());
		assertEquals("Unexpected posts in entity.", posts, user.getPosts());
		assertEquals("Unexpected followed threads in entity.", followedThreads, user.getFollowedThreads());
		
		// Construct entity with empty constructor call and setters
		ConcreteUser user2 = new ConcreteUser();
		user2.setId(id);
		user2.setUsername(username);
		user2.setEmail(email);
		user2.setPassword(password);
		user2.setProfilePicture(profilePicture);
		user2.setType(type);
		user2.setBio(bio);
		user2.setCanBeModerator(canBeModerator);
		user2.setInbox(inbox);
		user2.setPosts(posts);
		user2.setFollowedThreads(followedThreads);
		assertEquals("Unexpected id in entity.", id, user2.getId());
		assertEquals("Unexpected username in entity.", username, user2.getUsername());
		assertEquals("Unexpected email in entity.", email, user2.getEmail());
		assertEquals("Unexpected password in entity.", password, user2.getPassword());
		assertEquals("Unexpected profile picture in entity.", profilePicture, user2.getProfilePicture());
		assertEquals("Unexpected user type in entity.", type, user2.getType());
		assertEquals("Unexpected bio in entity.", bio, user2.getBio());
		assertEquals("Unexpected 'canBeModerator' status in entity.", canBeModerator, user2.isCanBeModerator());
		assertEquals("Unexpected inbox in entity.", inbox, user2.getInbox());
		assertEquals("Unexpected posts in entity.", posts, user2.getPosts());
		assertEquals("Unexpected followed threads in entity.", followedThreads, user2.getFollowedThreads());
	}
	
	/**
	 * Tests the 'addPost' method
	 */
	@Test
	public void testAddPost() {
		
		// Bounds for randomization
		int min = 1; int max = 100;
		
		// Construct entity
		ConcreteUser user = new ConcreteUser();

		// Try to add posts
		int postsSize = ThreadLocalRandom.current().nextInt(min, max);
		for (long i = 0; i < postsSize; i++) {
			Post post = mock(ConcretePost.class);
			when(post.getId()).thenReturn(i);
			user.addPost(post);
		}
		assertEquals("Wrong number of posts added.", postsSize, user.getPosts().size());
	}
	
	@Test
	public void testFollowedThreadsManipulation() {
		
		// Bounds for randomization
		int min = 1; int max = 100;
		
		// Construct entity
		ConcreteUser user = new ConcreteUser();

		// Try to add followed threads
		int addedThreadsSize = ThreadLocalRandom.current().nextInt(min, max);
		for (long i = 0; i < addedThreadsSize; i++) {
			QuestionThread thread = mock(ConcreteQuestionThread.class);
			when(thread.getId()).thenReturn(i);
			user.addFollowedThread(thread);
		}
		assertEquals("Wrong number of upvoters added.", addedThreadsSize, user.getFollowedThreads().size());
		
		// Try to remove followed threads
		int removedThreadsSize = ThreadLocalRandom.current().nextInt(min, addedThreadsSize);
		for (long i = 0; i < removedThreadsSize; i++) {
			user.removeFollowedThread(i);
		}
		assertEquals("Wrong number of followed threads removed.",
				addedThreadsSize - removedThreadsSize, user.getFollowedThreads().size());
	}

	/**
	 * Tests the 'equals' and 'hashCode' methods
	 */
	@Test
	public void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConcreteUser.class).verify();
	}
	
	/**
	 * Tests the 'clone' method
	 */
	@Test
	public void testClone() {
		
		// Setup data to be put in the entity
		long id = 2;
		String username = "Patrik Brunschwick";
		String email = "email@domain.com";
		String password = "supersecret";
		ConcreteDocument profilePicture = mock(ConcreteDocument.class);
		int type = ConcreteUser.REGISTERED;
		String bio = "Interesting life or whatever...";
		boolean canBeModerator = true;
		Inbox inbox = mock(ConcreteInbox.class);
		long min = 1; long max = 100;
		Map<Long, Post> posts = new HashMap<Long, Post>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			posts.put(i, mock(ConcretePost.class));
		}
		Map<Long, QuestionThread> followedThreads = new HashMap<Long, QuestionThread>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			followedThreads.put(i, mock(ConcreteQuestionThread.class));
		}
		
		// Construct entity
		ConcreteUser user = new ConcreteUser(username, email, password, profilePicture, type, bio,
				canBeModerator, inbox, posts, followedThreads);
		
		// Clone entity
		ConcreteUser userClone = user.clone();
		userClone.setProfilePicture(profilePicture); // override the profile picture loader
		
		// Verify that it is a new entity with the same values (without id)
		assertNotSame("Same instance instead of clone.", userClone, user);
		assertEquals("Clone has different values than the original.", user, userClone);
		
		// Same test with a defined id
		user.setId(id);
		userClone = user.clone();
		assertNotEquals("Id was copied (should not be the case).", user, userClone);
	}
	
	/**
	 * Tests the 'toString' method
	 */
	@Test
	public void testToString() {
		
		// Setup data to be put in the entity (and mock 'toString' values)
		long id = 2;
		String username = "Patrik Brunschwick";
		String email = "email@domain.com";
		String password = "supersecret";
		Document profilePicture = mock(ConcreteDocument.class);
		when(profilePicture.toString()).thenReturn("picture");
		int type = ConcreteUser.REGISTERED;
		String bio = "Interesting life or whatever...";
		boolean canBeModerator = true;
		Inbox inbox = mock(ConcreteInbox.class);
		when(inbox.toString()).thenReturn("inbox");
		long min = 1; long max = 100;
		Map<Long, Post> posts = new HashMap<Long, Post>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			posts.put(i, mock(ConcretePost.class));
		}
		Map<Long, QuestionThread> followedThreads = new HashMap<Long, QuestionThread>();
		for (long i = 0; i < ThreadLocalRandom.current().nextLong(min, max); i++) {
			followedThreads.put(i, mock(ConcreteQuestionThread.class));
		}
		
		// Construct entity
		ConcreteUser user = new ConcreteUser(username, email, password, profilePicture, type, bio,
				canBeModerator, inbox, posts, followedThreads);
		user.setId(id);
		
		// Create expected result
		String postsText = posts.toString();
		String followedThreadsText = followedThreads.toString();
		String expected = "ConcreteUser [id=" + id + ", username=" + username + ", email=" + email + ", password="
				+ password + ", profilePicture=" + profilePicture + ", type=" + type + ", bio=" + bio
				+ ", canBeModerator=" + canBeModerator + ", inbox=" + inbox + ", posts={"
				+ postsText.substring(1, postsText.length()-1) + "}, followedThreads={"
				+ followedThreadsText.substring(1, followedThreadsText.length()-1) + "}]";
		
		// Control result
		assertEquals("Incorrect conversion to string.", expected, user.toString());
	}
}
