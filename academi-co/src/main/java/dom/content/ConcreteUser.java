package dom.content;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonView;

import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;
import dom.inbox.ConcreteInbox;
import dom.inbox.Inbox;
import services.utility.View;

/**
 * Registered user implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "USERS")
public class ConcreteUser implements User, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -854685288099973922L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.UserMinimal.class)
	private long id;
	
	@NotNull
	@Column(name = "USERNAME", unique = true)
	@JsonView(View.UserName.class)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Analyzer(definition = "base_analyzer")
	private String username;
	
	@NotNull
	@Column(name = "EMAIL", unique = true)
	@JsonView(View.UserContact.class)
	private String email;
	
	@NotNull
	@Column(name = "PASSWORD_HASH")
	@JsonView(View.UserSecret.class)
	private String password;
	
	@NotNull
	@OneToOne(targetEntity = ConcreteDocument.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PROFILE_PICTURE")
	@JsonView(View.UserVisuals.class)
	private Document profilePicture;
	
	@NotNull
	@Column(name = "USER_TYPE")
	@JsonView(View.UserType.class)
	private String type;
	
	@NotNull
	@Column(name = "BIO")
	@JsonView(View.UserProfileModifiable.class)
	private String bio;
	
	@Column(name = "CAN_BE_MODERATOR")
	@JsonView(View.UserSessionModifiable.class)
	private boolean canBeModerator;
	
	@NotNull
	@OneToOne(targetEntity = ConcreteInbox.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "INBOX")
	@JsonView(View.UserSession.class)
	private Inbox inbox;
	
	@OneToMany(targetEntity = ConcretePost.class, mappedBy = "author")
	@MapKey(name = "id")
	@JsonView(View.UserExcessive.class)
	private Map<Long, Post> posts;
	
	@ManyToMany(targetEntity = ConcreteQuestionThread.class)
	@JoinTable(name = "USERS_THREADS", joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "THREAD_ID"))
	@MapKey(name = "id")
	@JsonView(View.UserExcessive.class)
	private Map<Long, QuestionThread> followedThreads;
	
	
	/***** Constructors *****/

	ConcreteUser() {
		posts = new HashMap<Long, Post>();
		followedThreads = new HashMap<Long, QuestionThread>();
	}


	ConcreteUser(String username, String email, String password, Document profilePicture, String type, String bio,
			boolean canBeModerator, Inbox inbox, Map<Long, Post> posts, Map<Long, QuestionThread> followedThreads) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
		this.type = type;
		this.bio = bio;
		this.canBeModerator = canBeModerator;
		this.inbox = inbox;
		this.posts = posts;
		this.followedThreads = followedThreads;
	}


	/***** Manipulation *****/
	
	@Override
	public void addPost(Post post) {
		posts.put(post.getId(), post);
	}

	@Override
	public void addFollowedThread(QuestionThread followedThread) {
		followedThreads.put(followedThread.getId(), followedThread);
	}

	@Override
	public void removeFollowedThread(long id) {
		followedThreads.remove(id);
	}


	/***** Getters/Setters *****/

	@Override
	public long getId() {
		return id;
	}

	void setId(long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Document getProfilePicture() {
		return profilePicture;
	}

	@Override
	public void setProfilePicture(Document profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getBio() {
		return bio;
	}

	@Override
	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public boolean isCanBeModerator() {
		return canBeModerator;
	}

	@Override
	public void setCanBeModerator(boolean canBeModerator) {
		this.canBeModerator = canBeModerator;
	}
	
	@Override
	public Inbox getInbox() {
		return inbox;
	}

	void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}

	@Override
	public Map<Long, Post> getPosts() {
		return posts;
	}

	void setPosts(Map<Long, Post> posts) {
		this.posts = posts;
	}

	@Override
	public Map<Long, QuestionThread> getFollowedThreads() {
		return followedThreads;
	}

	void setFollowedThreads(Map<Long, QuestionThread> followedThreads) {
		this.followedThreads = followedThreads;
	}

	
	/***** Utility *****/

	@Override
	protected ConcreteUser clone() {
		return new ConcreteUser(username, email, password,DocumentFactory.createDocument(profilePicture),
				type, bio, canBeModerator, inbox, new HashMap<Long, Post>(posts),
				new HashMap<Long, QuestionThread>(followedThreads));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bio.hashCode();
		result = prime * result + (canBeModerator ? 1231 : 1237);
		result = prime * result + email.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + password.hashCode();
		result = prime * result + type.hashCode();
		result = prime * result + username.hashCode();
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ConcreteUser)) {
			return false;
		}
		ConcreteUser other = (ConcreteUser) obj;
		if (!bio.equals(other.bio)) {
			return false;
		}
		if (canBeModerator != other.canBeModerator) {
			return false;
		}
		if (!email.equals(other.email)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (!password.equals(other.password)) {
			return false;
		}
		if (!type.equals(other.type)) {
			return false;
		}
		if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		String postsText = posts.keySet().toString();
		String followedThreadsText = followedThreads.keySet().toString();
		return "ConcreteUser [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", profilePicture=" + profilePicture.getId() + ", type=" + type + ", bio=" + bio + ", canBeModerator="
				+ canBeModerator + ", inbox=" + inbox.getId() + ", posts={" + postsText.substring(1, postsText.length()-1)
				+ "}, followedThreads={" + followedThreadsText.substring(1, followedThreadsText.length()-1) + "}]";
	}
	
	// This is a ninja comment. You have found it and it has thus failed and will return to its master for training.
}
