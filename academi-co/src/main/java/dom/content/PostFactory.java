package dom.content;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

/**
 * Instantiator of every kind of post (QuestionThread, Comment)
 * 
 * @author kaikoveritch
 *
 */
public class PostFactory {

	static public QuestionThread createQuestionThread(User author, String content, String title,
										MainTag subject, Tag languageTag, Map<Long, SecondaryTag> topics) {
		QuestionThread thread = new ConcreteQuestionThread(author, content, LocalDateTime.now(), new HashMap<Long, User>(),
				new HashMap<Long, User>(), 0, false, title, new HashMap<Long, Comment>(),
				subject, languageTag, topics);
		author.addPost(thread);
		return thread;
	}
	
	static public QuestionThread createQuestionThread(QuestionThread questionThread) {
		return ((ConcreteQuestionThread) questionThread).clone();
	}
	
	static public Comment createComment(User author, String content, QuestionThread question) {
		Comment comment = new ConcreteComment(author, content, LocalDateTime.now(), new HashMap<Long, User>(),
				new HashMap<Long, User>(), 0, false, question);
		author.addPost(comment);
		return comment;
	}
	
	static public Comment createComment(Comment comment) {
		return ((ConcreteComment) comment).clone();
	}
}
