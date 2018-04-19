package dom.content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Comment (answer) attached to a thread (question) implementation
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "COMMENTS")
public class ConcreteComment extends ConcretePost implements Serializable, Comment {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -3004662737981639332L;

	@NotNull
	@ManyToOne(targetEntity = ConcreteQuestionThread.class)
	@JoinColumn(name = "QUESTION")
	private QuestionThread question;
	
	
	/***** Constructors *****/

	ConcreteComment() {
		super();
	}

	ConcreteComment(User author, String content, LocalDateTime creationDate, Map<Long, User> upvoters,
			Map<Long, User> downvoters, int score, boolean isBanned, QuestionThread question) {
		super(author, content, creationDate, upvoters, downvoters, score, isBanned);
		this.question = question;
	}
	
	
	/***** Getters/Setters *****/

	@Override
	public QuestionThread getQuestion() {
		return question;
	}

	void setQuestion(QuestionThread question) {
		this.question = question;
	}
	
	
	/***** Utility *****/

	@Override
	public ConcreteComment clone() {
		return new ConcreteComment(getAuthor(), getContent(), getCreationDate(), getUpvoters(),
				getDownvoters(), getScore(), getIsBanned(), question);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ConcreteComment other = (ConcreteComment) obj;
		if (question == null) {
			if (other.question != null) {
				return false;
			}
		} else if (!question.equals(other.question)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String upvotersText = getUpvoters().toString();
		String downvotersText = getDownvoters().toString();
		return "ConcreteComment [question=" + question + ", id=" + getId()	+ ", author=" + getAuthor() + ", content=" + getContent()
				+ ", creationDate=" + getCreationDate() + ", upvoters={" + upvotersText.substring(1, upvotersText.length()-1)
				+ "}, downvoters={" + downvotersText.substring(1, downvotersText.length()-1) + "}, score=" + getScore()
				+ ", isBanned=" + getIsBanned() + "]";
	}
}
