package services.demo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;

/**
 * 
 * @author petrbinko
 *
 */
@Stateless
public class ConcreteDemoServices implements DemoServices {
	
	/******************* Attributes **********************/
	private static final long serialVersionUID = 3125140665269140715L;
	
	@PersistenceContext
	EntityManager entityManager;

	
	/******************** Services ***********************/
	
	@Override
	public MainTag storeMainTag(MainTag tag) {
		entityManager.persist(tag);
		return tag;
	}


	@Override
	public SecondaryTag storeSecondaryTag(SecondaryTag tag) {
		entityManager.persist(tag);
		return tag;
	}
	
	/**
	 * Add stuff to database
	 */
	@Override
	public void addStuffToDatabase() {
		
//		User user1 = UserFactory.createUser("Jean", "truc@mail.co", "seyxdcfvghbnjlm", 0);
//		User user2 = UserFactory.createUser("Bob", "machin@mail.co", "dfghnjk", 0);
//		entityManager.persist(user1);
//		entityManager.persist(user2);
		
		Tag language = TagFactory.createTag("Truc");
		entityManager.persist(language);
		MainTag mainTag = TagFactory.createMainTag("IT");
		entityManager.persist(mainTag);
		SecondaryTag secondaryTag = TagFactory.createSecondaryTag("Java pas bien", mainTag);
//		entityManager.persist(secondaryTag);
		System.out.println(secondaryTag);
		
//		Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
//		topics.put((long) 1, secondaryTag);
//		
//		System.out.println(topics);
//		
//		new PostFactory();
//		QuestionThread question = PostFactory.createQuestionThread(user1, "Truc machin bidule", "wowowo", mainTag, language, topics);
//		entityManager.persist(question);
		
			
	}
	
}
