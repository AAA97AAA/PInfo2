package services.tags;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;

@Default
@Stateless
public class ConcreteTagService implements TagService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -3158603306727516329L;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MainTag> getAllSubjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag getLanguageTag(long id) {
		// Creating criteria builder to create a criteria query
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		// Criteria query of return type QuestionThread
		CriteriaQuery<ConcreteTag> criteriaQuery = criteriaBuilder.createQuery(ConcreteTag.class);
		
		// Roots define the basis from which all joins, paths and attributes are available in the query -> c.f. table from
		Root<ConcreteTag> variableRoot = criteriaQuery.from(ConcreteTag.class);
		
		// Condition statement -> Where
		criteriaQuery.where(criteriaBuilder.equal(variableRoot.get("id"), id));
		
		// Creating typed query
		TypedQuery<ConcreteTag> query = entityManager.createQuery(criteriaQuery);
		
		// Return of single result. If we want a list of results, we use getResultList
		return query.getSingleResult();
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
		entityManager.persist(tag);
		return tag;
	}

	@Override
	public MainTag addTag(MainTag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecondaryTag addTag(long parentId, SecondaryTag tag) {
		// TODO Auto-generated method stub
		return null;
	}

}
