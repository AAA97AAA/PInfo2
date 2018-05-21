package services.tags;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;

@Default
@Stateless
public class ConcreteTagService implements TagService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -3158603306727516329L;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MainTag> getAllSubjects() {
		return entityManager.createNamedQuery("MainTag.getAll", MainTag.class).getResultList();
	}

	@Override
	public Tag getLanguageTag(long id) {
		return entityManager.find(ConcreteTag.class, id);
	}

	@Override
	public MainTag getMainTag(long id) {
		return entityManager.find(ConcreteMainTag.class, id);
	}

	@Override
	public SecondaryTag getSecondaryTag(long id) {
		return entityManager.find(ConcreteSecondaryTag.class, id);
	}

	@Override
	public Tag addTag(Tag tag) {
		entityManager.persist(tag);
		return tag;
	}

	@Override
	public MainTag addTag(MainTag tag) {
		entityManager.persist(tag);
		return tag;
	}

	@Override
	public SecondaryTag addTag(long id, SecondaryTag tag) {
		
		// Fetch and detach parent (if exists)
		MainTag parent = getMainTag(id);
		if (parent == null) {
			return null;
		}
		entityManager.detach(parent);
		
		// Create and store new dependency
		SecondaryTag entity = TagFactory.createSecondaryTag(tag.getName(), parent);
		entityManager.persist(entity);
		
		return entity;
	}

}
