package dom.content;

/**
 * Comment (answer) attached to a thread (question) definition
 * 
 * @author kaikoveritch
 *
 */
public interface Comment extends Post {

	public QuestionThread getQuestion();
}
