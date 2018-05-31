package dom.content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceCharFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import services.utility.View;

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
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name = "keyword_analyzer", // Hibernate search analyzer definition
		charFilters = { // remove any punctuation (non-alphanumeric characters)
				@CharFilterDef(factory = PatternReplaceCharFilterFactory.class, params = {
						@Parameter(name = "pattern", value = "[^A-Za-z0-9]+"),
						@Parameter(name = "replacement", value = " ")
				})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), // standard tokenizer
		filters = {
				@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class), // turn all characters to ASCII
				@TokenFilterDef(factory = LowerCaseFilterFactory.class), // turn all characters to lowercase
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = { // take the keywords' radical
						@Parameter(name = "language", value = "English")
				})
		}
	),
	@AnalyzerDef(name = "base_analyzer", // Hibernate search analyzer definition
		charFilters = { // remove any punctuation (non-alphanumeric characters)
				@CharFilterDef(factory = PatternReplaceCharFilterFactory.class, params = {
						@Parameter(name = "pattern", value = "[^A-Za-z0-9]+"),
						@Parameter(name = "replacement", value = " ")
				})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), // standard tokenizer
		filters = {
				@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class), // turn all characters to ASCII
				@TokenFilterDef(factory = LowerCaseFilterFactory.class) // turn all characters to lowercase
		}
	)
})
public class ConcreteQuestionThread extends ConcretePost implements Serializable, QuestionThread {

	// Serial version (auto-generated
	private static final long serialVersionUID = 3042160450871988624L;

	@NotNull
	@Column(name = "TITLE")
	@JsonView(View.PostBase.class)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Analyzer(definition = "keyword_analyzer")
	private String title;

	@OneToMany(targetEntity = ConcreteComment.class, mappedBy = "question", fetch = FetchType.EAGER)
	@OrderBy("creationDate ASC, id ASC")
	@JsonView(View.PostParentCentered.class)
	private Set<Comment> answers;

	@NotNull
	@ManyToOne(targetEntity = ConcreteMainTag.class)
	@JoinColumn(name = "SUBJECT")
	@JsonView(View.PostBase.class)
	@IndexedEmbedded(targetElement = ConcreteMainTag.class)
	private MainTag subject;

	@NotNull
	@ManyToOne(targetEntity = ConcreteTag.class)
	@JoinColumn(name = "LANGUAGE")
	@JsonView(View.PostBase.class)
	@IndexedEmbedded(targetElement = ConcreteTag.class)
	private Tag language;

	@ManyToMany(targetEntity = ConcreteSecondaryTag.class, fetch = FetchType.EAGER)
	@JoinTable(name = "QUESTIONS_TOPICS", joinColumns = @JoinColumn(name = "QUESTION_ID"),
		inverseJoinColumns = @JoinColumn(name = "TOPIC_ID"))
	@OrderBy
	@JsonView(View.PostBase.class)
	@IndexedEmbedded(targetElement = ConcreteSecondaryTag.class)
	private Set<SecondaryTag> topics;


	/***** Constructors *****/

	ConcreteQuestionThread() {
		super();
		answers = new LinkedHashSet<Comment>();
	}

	ConcreteQuestionThread(User author, String content, LocalDateTime creationDate, Set<User> upvoters,
			Set<User> downvoters, int score, boolean isBanned, String title, Set<Comment> answers,
			MainTag subject, Tag languageTag, Set<SecondaryTag> topics) {
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
	@JsonIgnore
	@Override
	public List<Tag> getAllTags() {
		List<Tag> allTags = new ArrayList<Tag>();
		allTags.add(subject);
		allTags.add(language);
		allTags.addAll(topics);
		return allTags;
	}
	
	@Override
	public void addAnswer(Comment answer) {
		answers.add(answer);
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
	public Set<Comment> getAnswers() {
		return answers;
	}

	void setAnswers(Set<Comment> answers) {
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
	public Set<SecondaryTag> getTopics() {
		return topics;
	}

	void setTopics(Set<SecondaryTag> topics) {
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
		Collector<User, ?, Map<Long, String>> collector = Collectors.toMap(User::getId, User::getUsername);
		String upvotersText = getUpvoters().stream().collect(collector).toString();
		String downvotersText = getDownvoters().stream().collect(collector).toString();
		return "ConcreteQuestionThread [title=" + title + ", answers={" + answersText.substring(1, answersText.length()-1)
				+ "}, subject=" + subject.getId() + ", language=" + language.getId() + ", topics={"
				+ topicsText.substring(1, topicsText.length()-1) + "}, id=" + getId() + ", author=" + getAuthor()
				+ ", content=" + getContent() + ", creationdate=" + getCreationDate() + ", upvoters={"
				+ upvotersText.substring(1, upvotersText.length()-1) + "}, downvoters={"
				+ downvotersText.substring(1, downvotersText.length()-1) + "}, score=" + getScore() + ", isbanned="
				+ isBanned() + "]";
	}
}
