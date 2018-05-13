package dom.content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import services.utility.View;

/**
 * Generic post definition
 * (Thread or comment)
 * !!! NOT STORED AS IS -> unifies the QuestionThreads and the Comments !!!
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "POSTS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "POST_TYPE")
@DiscriminatorValue("POST")
public class ConcretePost implements Post, Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = 7134397135228261743L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.PostMinimal.class)
	private long id;
	
	@NotNull
	@ManyToOne(targetEntity = ConcreteUser.class)
	@JoinColumn(name = "AUTHOR")
	@JsonView(View.PostAuthor.class)
	private User author;
	
	@NotNull
	@Column(name = "CONTENT")
	@JsonView(View.PostBase.class)
	private String content;
	
	@NotNull
	@Column(name = "CREATION_DATE")
	@JsonView(View.PostBase.class)
	private LocalDateTime creationDate;
	
	@ManyToMany(targetEntity = ConcreteUser.class)
	@JoinTable(name = "UPVOTERS", joinColumns = @JoinColumn(name = "POST_ID"),
		inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	@MapKey(name = "id")
	@JsonView(View.PostBase.class)
	private Map<Long, User> upvoters;
	
	@ManyToMany(targetEntity = ConcreteUser.class)
	@JoinTable(name = "DOWNVOTERS", joinColumns = @JoinColumn(name = "POST_ID"),
		inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	@MapKey(name = "id")
	@JsonView(View.PostBase.class)
	private Map<Long, User> downvoters;
	
	@Column(name = "SCORE")
	@JsonView(View.PostBase.class)
	private int score;
	
	@Column(name = "BANNED")
	@JsonView(View.PostState.class)
	private boolean banned;
	
	
	/***** Constructors *****/

	ConcretePost() {
		score = 0;
		upvoters = new HashMap<Long, User>();
		downvoters = new HashMap<Long, User>();
		banned = false;
		creationDate = LocalDateTime.now();
	}
	
	ConcretePost(User author, String content, LocalDateTime creationDate, Map<Long, User> upvoters,
			Map<Long, User> downvoters, int score, boolean banned) {
		this.author = author;
		this.content = content;
		this.creationDate = creationDate;
		this.upvoters = upvoters;
		this.downvoters = downvoters;
		this.score = score;
		this.banned = banned;
	}

	
	/***** Manipulation *****/

	@Override
	public void addUpvoter(User upvoter) {
		upvoters.put(upvoter.getId(), upvoter);
		score += 1;
	}

	@Override
	public void removeUpvoter(long id) {
		upvoters.remove(id);
		score -= 1;
	}

	@Override
	public void addDownvoter(User downvoter) {
		downvoters.put(downvoter.getId(), downvoter);
		score -= 1;
	}

	@Override
	public void removeDownvoter(long id) {
		downvoters.remove(id);
		score += 1;
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
	public User getAuthor() {
		return author;
	}

	void setAuthor(User author) {
		this.author = author;
	}

	@Override
	public String getContent() {
		return content;
	}

	void setContent(String content) {
		this.content = content;
	}

	@Override
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public Map<Long, User> getUpvoters() {
		return upvoters;
	}

	void setUpvoters(Map<Long, User> upvoters) {
		this.upvoters = upvoters;
	}

	@Override
	public Map<Long, User> getDownvoters() {
		return downvoters;
	}

	void setDownvoters(Map<Long, User> downvoters) {
		this.downvoters = downvoters;
	}

	@Override
	public int getScore() {
		return score;
	}

	void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public boolean isBanned() {
		return banned;
	}

	@Override
	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	/***** Utility *****/

	@Override
	protected ConcretePost clone() {
		return new ConcretePost(author, content, creationDate, new HashMap<Long, User>(upvoters),
				new HashMap<Long, User> (downvoters), score, banned);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + author.hashCode();
		result = prime * result + content.hashCode();
		result = prime * result + creationDate.hashCode();
		result = prime * result + ((downvoters == null) ? 0 : downvoters.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (banned ? 1231 : 1237);
		result = prime * result + score;
		result = prime * result + ((upvoters == null) ? 0 : upvoters.hashCode());
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
		if (!(obj instanceof ConcretePost)) {
			return false;
		}
		ConcretePost other = (ConcretePost) obj;
		if (!author.equals(other.author)) {
			return false;
		}
		if (!content.equals(other.content)) {
			return false;
		}
		if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (downvoters == null) {
			if (other.downvoters != null) {
				return false;
			}
		} else if (!downvoters.equals(other.downvoters)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (banned != other.banned) {
			return false;
		}
		if (score != other.score) {
			return false;
		}
		if (upvoters == null) {
			if (other.upvoters != null) {
				return false;
			}
		} else if (!upvoters.equals(other.upvoters)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String upvotersText = upvoters.toString();
		String downvotersText = downvoters.toString();
		return "ConcretePost [id=" + id + ", author=" + author + ", content=" + content + ", creationDate="
				+ creationDate + ", upvoters={" + upvotersText.substring(1, upvotersText.length()-1)
				+ "}, downvoters={" + downvotersText.substring(1, downvotersText.length()-1) + "}, score="
				+ score + ", isBanned=" + banned + "]";
	}
}
