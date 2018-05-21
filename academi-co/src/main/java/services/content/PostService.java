package services.content;

import java.io.Serializable;

import javax.ejb.Local;

import dom.content.Comment;
import dom.content.Post;
import dom.content.QuestionThread;
import dom.content.Vote;

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
	
	public QuestionThread addPost(QuestionThread questionThread);
	
	public Comment addPost(long parentId, Comment comment);
	
	public Post setBan(long id, boolean banned);
	
	public Post vote(long id, Vote vote);
}
