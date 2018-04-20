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

import dom.documentsManager.ConcreteDocument;
import dom.documentsManager.Document;
import dom.documentsManager.DocumentFactory;
import dom.inbox.ConcreteInbox;
import dom.inbox.Inbox;

/**
 * Registered user implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "USERS")
public class ConcreteUser implements User, Serializable {
	
	/***** Constants *****/
	static public int BANNED = -1;
	static public int REGISTERED = 0;
	static public int MODERATOR = 1;
	static public int ADMINISTRATOR = 2;

	// Serial version (auto-generated)
	private static final long serialVersionUID = -854685288099973922L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(name = "USERNAME", unique = true)
	private String username;
	
	@NotNull
	@Column(name = "EMAIL", unique = true)
	private String email;
	
	@NotNull
	@Column(name = "PASSWORD")
	private String password;
	
	@NotNull
	@OneToOne(targetEntity = ConcreteDocument.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PROFILE_PICTURE")
	private Document profilePicture;
	
	@Column(name = "TYPE")
	private int type;
	
	@NotNull
	@Column(name = "BIO")
	private String bio;
	
	@Column(name = "CAN_BE_MODERATOR")
	private boolean canBeModerator;
	
	@NotNull
	@OneToOne(targetEntity = ConcreteInbox.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "INBOX")
	private Inbox inbox;
	
	@OneToMany(targetEntity = ConcretePost.class, mappedBy = "author")
	@MapKey(name = "id")
	private Map<Long, Post> posts;
	
	@ManyToMany(targetEntity = ConcreteQuestionThread.class)
	@JoinTable(name = "USERS_THREADS", joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "THREAD_ID"))
	@MapKey(name = "id")
	private Map<Long, QuestionThread> followedThreads;
	
	
	/***** Constructors *****/

	ConcreteUser() {
		posts = new HashMap<Long, Post>();
		followedThreads = new HashMap<Long, QuestionThread>();
	}


	ConcreteUser(String username, String email, String password, Document profilePicture, int type, String bio,
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
	public int getType() {
		return type;
	}

	@Override
	public void setType(int type) {
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
	public ConcreteUser clone() {
		return new ConcreteUser(username, email, password,DocumentFactory.createDocument(profilePicture),
				type, bio, canBeModerator, inbox, new HashMap<Long, Post>(posts),
				new HashMap<Long, QuestionThread>(followedThreads));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + (canBeModerator ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((followedThreads == null) ? 0 : followedThreads.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((inbox == null) ? 0 : inbox.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((posts == null) ? 0 : posts.hashCode());
		result = prime * result + ((profilePicture == null) ? 0 : profilePicture.hashCode());
		result = prime * result + type;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		if (bio == null) {
			if (other.bio != null) {
				return false;
			}
		} else if (!bio.equals(other.bio)) {
			return false;
		}
		if (canBeModerator != other.canBeModerator) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (followedThreads == null) {
			if (other.followedThreads != null) {
				return false;
			}
		} else if (!followedThreads.equals(other.followedThreads)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (inbox == null) {
			if (other.inbox != null) {
				return false;
			}
		} else if (!inbox.equals(other.inbox)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (posts == null) {
			if (other.posts != null) {
				return false;
			}
		} else if (!posts.equals(other.posts)) {
			return false;
		}
		if (profilePicture == null) {
			if (other.profilePicture != null) {
				return false;
			}
		} else if (!profilePicture.equals(other.profilePicture)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String postsText = posts.toString();
		String followedThreadsText = followedThreads.toString();
		return "ConcreteUser [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", profilePicture=" + profilePicture + ", type=" + type + ", bio=" + bio + ", canBeModerator="
				+ canBeModerator + ", inbox=" + inbox + ", posts={" + postsText.substring(1, postsText.length()-1)
				+ "}, followedThreads={" + followedThreadsText.substring(1, followedThreadsText.length()-1) + "}]";
	}
	
	// This is a ninja comment. You have found it and it has thus failed and will return to its master for training.
}
