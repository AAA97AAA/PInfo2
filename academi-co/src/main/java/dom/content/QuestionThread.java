package dom.content;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

/**
 * Thread (question) definition
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteQuestionThread.class)
public interface QuestionThread extends Post {

	public String getTitle();

	public List<Comment> getAnswers();

	public MainTag getSubject();

	public Tag getLanguage();

	public Set<SecondaryTag> getTopics();
	
	public List<Tag> getAllTags();
	
	public void addAnswer(Comment answer);
}
