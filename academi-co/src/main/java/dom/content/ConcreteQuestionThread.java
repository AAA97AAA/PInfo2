package dom.content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

/**
 * Thread (question) implementation
 *
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "QUESTION_THREADS")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("THREAD")
public class ConcreteQuestionThread extends ConcretePost implements Serializable, QuestionThread {

	// Serial version (auto-generated
	private static final long serialVersionUID = 3042160450871988624L;

	@NotNull
	@Column(name = "TITLE")
	private String title;

	@OneToMany(targetEntity = ConcreteComment.class, mappedBy = "question")
	@MapKeyColumn(name = "ID")
	private Map<Long, Comment> answers;

	@NotNull
	@ManyToOne(targetEntity = ConcreteMainTag.class)
	@JoinColumn(name = "SUBJECT")
	private MainTag subject;

	@NotNull
	@ManyToOne(targetEntity = ConcreteTag.class)
	@JoinColumn(name = "LANGUAGE")
	private Tag language;

	@ManyToMany(targetEntity = ConcreteSecondaryTag.class)
	@JoinTable(name = "QUESTIONS_TOPICS", joinColumns = @JoinColumn(name = "QUESTION_ID"),
		inverseJoinColumns = @JoinColumn(name = "TOPIC_ID"))
	@MapKey(name = "id")
	private Map<Long, SecondaryTag> topics;


	/***** Constructors *****/

	ConcreteQuestionThread() {
		super();
		answers = new HashMap<Long, Comment>();
	}

	ConcreteQuestionThread(User author, String content, LocalDateTime creationDate, Map<Long, User> upvoters,
			Map<Long, User> downvoters, int score, boolean isBanned, String title, Map<Long, Comment> answers,
			MainTag subject, Tag languageTag, Map<Long, SecondaryTag> topics) {
		super(author, content, creationDate, upvoters, downvoters, score, isBanned);
		this.title = title;
		this.answers = answers;
		this.subject = subject;
		this.language = languageTag;
		this.topics = topics;
	}

	
	/***** Manipulation *****/

	/**
	 * Groups the tags from 'subject', 'language' and 'topics' in a list.
	 * 
	 * @return: Collection with: subject -> language -> topic_1 -> topic_2 -> ...
	 */
	@Override
	public List<Tag> getAllTags() {
		List<Tag> allTags = new ArrayList<Tag>();
		allTags.add(subject);
		allTags.add(language);
		allTags.addAll(topics.values());
		return allTags;
	}
	
	@Override
	public void addAnswer(Comment answer) {
		answers.put(answer.getId(), answer);
	}

	/***** Getters/Setters *****/

	@Override
	public String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Map<Long, Comment> getAnswers() {
		return answers;
	}

	void setAnswers(Map<Long, Comment> answers) {
		this.answers = answers;
	}

	@Override
	public MainTag getSubject() {
		return subject;
	}

	void setSubject(MainTag subject) {
		this.subject = subject;
	}

	@Override
	public Tag getLanguage() {
		return language;
	}

	void setLanguage(Tag language) {
		this.language = language;
	}

	@Override
	public Map<Long, SecondaryTag> getTopics() {
		return topics;
	}

	void setTopics(Map<Long, SecondaryTag> topics) {
		this.topics = topics;
	}


	/***** Utility *****/

	@Override
	protected ConcreteQuestionThread clone() {
		return new ConcreteQuestionThread(getAuthor(), getContent(), getCreationDate(), getUpvoters(),
			getDownvoters(), getScore(), isBanned(), title, getAnswers(), subject, language, topics);
	}

	@Override
	public String toString() {
		String answersText = answers.toString();
		String topicsText = topics.toString();
		String upvotersText = getUpvoters().toString();
		String downvotersText = getDownvoters().toString();
		return "ConcreteQuestionThread [title=" + title + ", answers={" + answersText.substring(1, answersText.length()-1)
				+ "}, subject=" + subject + ", language=" + language + ", topics={"
				+ topicsText.substring(1, topicsText.length()-1) + "}, id=" + getId() + ", author=" + getAuthor()
				+ ", content=" + getContent() + ", creationdate=" + getCreationDate() + ", upvoters={"
				+ upvotersText.substring(1, upvotersText.length()-1) + "}, downvoters={"
				+ downvotersText.substring(1, downvotersText.length()-1) + "}, score=" + getScore() + ", isbanned="
				+ isBanned() + "]";
	}
}
