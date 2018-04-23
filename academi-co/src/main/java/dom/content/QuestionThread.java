package dom.content;

import java.util.Collection;
import java.util.Map;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

/**
 * Thread (question) definition
 * 
 * @author kaikoveritch
 *
 */
public interface QuestionThread extends Post {

	public String getTitle();

	public Map<Long, Comment> getAnswers();

	public MainTag getSubject();

	public Tag getLanguage();

	public Map<Long, SecondaryTag> getTopics();
	
	public Collection<Tag> getAllTags();
}