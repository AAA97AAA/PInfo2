package services.tags;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

@Default
@Stateless
public class ConcreteTagService implements TagService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -3158603306727516329L;

	@Override
	public List<MainTag> getAllSubjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag getLanguageTag(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MainTag getMainTag(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecondaryTag getSecondaryTag(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag addTag(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MainTag addTag(MainTag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecondaryTag addTag(SecondaryTag tag) {
		// TODO Auto-generated method stub
		return null;
	}

}
