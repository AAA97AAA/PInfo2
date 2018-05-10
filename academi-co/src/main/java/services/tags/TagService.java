package services.tags;

import java.io.Serializable;

import javax.ejb.Local;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

/**
 * Tag handling service definition.
 * 
 * @author kaikoveritch
 *
 */
@Local
public interface TagService  extends Serializable {

	public Tag getLanguageTag(long id);
	
	public MainTag getMainTag(long id);
	
	public SecondaryTag getSecondaryTag(long id);
	
	public Tag addTag(Tag tag);
	
	public MainTag addTag(MainTag tag);
	
	public SecondaryTag addTag(SecondaryTag tag);
}
