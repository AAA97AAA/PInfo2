package dom.content;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Comment (answer) attached to a thread (question) definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcretePost.class)
public interface Comment extends Post {

	public QuestionThread getQuestion();
}
