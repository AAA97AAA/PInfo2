package dom.content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import services.utility.View;
import services.utility.VotersMarshaller;

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
@NamedQueries({
	@NamedQuery(name = "Post.fromAuthorByDate",
			query = "SELECT p FROM ConcretePost p WHERE p.author = :author ORDER BY p.creationDate DESC, p.id ASC"),
	@NamedQuery(name = "Post.fromAuthorByScore",
			query = "SELECT p FROM ConcretePost p WHERE p.author = :author ORDER BY p.score DESC, p.creationDate DESC, p.id ASC")
})
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
	@IndexedEmbedded(targetElement = ConcreteUser.class)
	private User author;
	
	@NotNull
	@Column(name = "CONTENT")
	@JsonView(View.PostBase.class)
	private String content;
	
	@NotNull
	@Column(name = "CREATION_DATE")
	@JsonView(View.PostBase.class)
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
	@SortableField
	private LocalDateTime creationDate;
	
	@ManyToMany(targetEntity = ConcreteUser.class, fetch = FetchType.EAGER)
	@JoinTable(name = "UPVOTERS", joinColumns = @JoinColumn(name = "POST_ID"),
		inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	@JsonView(View.PostVote.class)
	@JsonSerialize(using = VotersMarshaller.class)
	private Set<User> upvoters;
	
	@ManyToMany(targetEntity = ConcreteUser.class, fetch = FetchType.EAGER)
	@JoinTable(name = "DOWNVOTERS", joinColumns = @JoinColumn(name = "POST_ID"),
		inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	@JsonView(View.PostVote.class)
	@JsonSerialize(using = VotersMarshaller.class)
	private Set<User> downvoters;
	
	@Column(name = "SCORE")
	@JsonView(View.PostVote.class)
	@Field(index = Index.NO, analyze = Analyze.NO, store = Store.NO)
	@SortableField
	private int score;
	
	@Column(name = "BANNED")
	@JsonView(View.PostState.class)
	private boolean banned;
	
	
	/***** Constructors *****/

	ConcretePost() {
		score = 0;
		upvoters = new HashSet<User>();
		downvoters = new HashSet<User>();
		banned = false;
		creationDate = LocalDateTime.now();
	}
	
	ConcretePost(User author, String content, LocalDateTime creationDate, Set<User> upvoters,
			Set<User> downvoters, int score, boolean banned) {
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
		upvoters.add(upvoter);
		score += 1;
	}

	@Override
	public void removeUpvoter(User upvoter) {
		upvoters.remove(upvoter);
		score -= 1;
	}

	@Override
	public void addDownvoter(User downvoter) {
		downvoters.add(downvoter);
		score -= 1;
	}

	@Override
	public void removeDownvoter(User downvoter) {
		downvoters.remove(downvoter);
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
	public Set<User> getUpvoters() {
		return upvoters;
	}

	void setUpvoters(Set<User> upvoters) {
		this.upvoters = upvoters;
	}

	@Override
	public Set<User> getDownvoters() {
		return downvoters;
	}

	void setDownvoters(Set<User> downvoters) {
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
		return new ConcretePost(author, content, creationDate, new HashSet<User>(upvoters),
				new HashSet<User>(downvoters), score, banned);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + content.hashCode();
		result = prime * result + creationDate.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (banned ? 1231 : 1237);
		result = prime * result + score;
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
		if (!content.equals(other.content)) {
			return false;
		}
		if (!creationDate.equals(other.creationDate)) {
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
		return true;
	}

	@Override
	public String toString() {
		Collector<User, ?, Map<Long, String>> collector = Collectors.toMap(User::getId, User::getUsername);
		String upvotersText = getUpvoters().stream().collect(collector).toString();
		String downvotersText = getDownvoters().stream().collect(collector).toString();
		return "ConcretePost [id=" + id + ", author=" + author + ", content=" + content + ", creationDate="
				+ creationDate + ", upvoters={" + upvotersText.substring(1, upvotersText.length()-1)
				+ "}, downvoters={" + downvotersText.substring(1, downvotersText.length()-1) + "}, score="
				+ score + ", isBanned=" + banned + "]";
	}
}
