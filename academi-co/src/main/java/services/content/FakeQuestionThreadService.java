package services.content;

import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import dom.content.PostFactory;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.UserFactory;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;

/**
 * Fake service class for testing purposes
 * 
 * @author petrbinko
 *
 */
@Alternative
@Stateless
public class FakeQuestionThreadService implements QuestionThreadService {

	
	
	/******************* Attributes **********************/

	// Serial version (auto-generated)
	private static final long serialVersionUID = 4946901399590648039L;
	
	
	/******************** Services ***********************/
	
	@Override
	public QuestionThread getQuestionThread(long id) {
		
		User author = UserFactory.createUser("username", "mail", "password", 0);
		Tag languageTag = TagFactory.createTag("language");
		
		MainTag mainTag = TagFactory.createMainTag("Subject");
		TagFactory.createSecondaryTag("topic1", mainTag);
		mainTag.getChildren().put((long) 1, mainTag.getChildren().get((long) 0));
		Map<Long, SecondaryTag> topics = mainTag.getChildren();
		
		return PostFactory.createQuestionThread(author, "content", "title", mainTag, languageTag, topics);
	}

	@Override
	public QuestionThread addQuestionThread(QuestionThread questionThread) {
		return questionThread;
	}

}
