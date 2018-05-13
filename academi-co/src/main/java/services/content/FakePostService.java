package services.content;

import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import dom.content.Comment;
import dom.content.Post;
import dom.content.PostFactory;
import dom.content.QuestionThread;
import dom.content.User;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;
import services.tags.FakeTagService;

/**
 * Fake service class for testing purposes
 * 
 * @author petrbinko
 *
 */
@Alternative
@Stateless
public class FakePostService implements PostService {

	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = 4946901399590648039L;
	
	private FakeUserService userProvider = new FakeUserService();
	private FakeTagService tagProvider = new FakeTagService();
	
	
	/******************** Services ***********************/
	
	@Override
	public QuestionThread getQuestionThread(long id) {
		
		User author = userProvider.getUser(0);
		
		Tag languageTag = TagFactory.createTag("language");
		MainTag mainTag = tagProvider.getMainTag(0);
		Map<Long, SecondaryTag> topics = mainTag.getChildren();
		
		QuestionThread thread = PostFactory.createQuestionThread(author, "content", "title", mainTag, languageTag, topics);
		
		for (long i = 5; i >= 0; i--) {
			User author2 = userProvider.getUserByName("commenter" + i);
			Comment comment = PostFactory.createComment(author2, "comment" + i, thread);
			thread.getAnswers().put(i, comment);
		}
		
		return thread;
	}

	@Override
	public QuestionThread addQuestionThread(QuestionThread questionThread) {
		return questionThread;
	}

	@Override
	public Comment addComment(long questionId, Comment comment) {
		return comment;
	}

	@Override
	public Post setBan(long id, boolean banned) {
		Post post = PostFactory.createComment(null, "content", null);
		post.setBanned(banned);
		return post;
	}
}
