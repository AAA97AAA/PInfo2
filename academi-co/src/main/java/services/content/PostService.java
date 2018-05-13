package services.content;

import java.io.Serializable;

import javax.ejb.Local;

import dom.content.Comment;
import dom.content.Post;
import dom.content.QuestionThread;

/**
 * Service definition for posts (threads and comments).
 * 
 * @author petrbinko
 * @author kaikoveritch (rework)
 *
 */
@Local
public interface PostService extends Serializable {
	
	public QuestionThread getQuestionThread(long id);
	
	public QuestionThread addQuestionThread(QuestionThread questionThread);
	
	public Comment addComment(long questionId, Comment comment);
	
	public Post setBan(long id, boolean banned);
}
