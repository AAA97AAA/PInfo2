package services.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dom.content.PostFactory;
import dom.content.QuestionThread;
import dom.content.User;
import dom.content.UserFactory;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;
import services.content.PostService;
import services.content.UserService;
import services.tags.TagService;

/**
 * 
 * @author petrbinko
 *
 */
@Path("/demo")
public class DemoServicesRs {
	
	@Inject
	private DemoServices service;
	
	@Context
	private ServletContext context;
	
	@Inject
	private UserService userService;
	
	@Inject
	private TagService tagService;
	
	@Inject
	private PostService postService;
	
	@POST
	@Path("/")
	public Response fillDB() {
		
		// Parameters
		int nUsers = 5;
		int nSubjects = 5;
		int nTopics = 5;
		int nPosts = 20;
		
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < nUsers; i++) {
			users.add(userService.addUser(UserFactory.createUser("user" + i, "mail" + i + "@mail.com", "secret", User.REGISTERED)));
		}
		List<MainTag> subjects = new ArrayList<MainTag>();
		for (int i = 0; i < nSubjects; i++) {
			subjects.add(tagService.addTag(TagFactory.createMainTag("subject" + i)));
			for (int j = 0; j < nTopics; j++) {
				tagService.addTag(subjects.get(i).getId(), TagFactory.createSecondaryTag("topic" + i + "-" + j, subjects.get(i)));
			}
		}
		Tag lang = tagService.addTag(TagFactory.createTag("english"));
		List<QuestionThread> threads = new ArrayList<QuestionThread>();
		for (int i = 0; i < nPosts; i++) {
			int u = i % nUsers;
			int s = i % nSubjects;
			Map<Long, SecondaryTag> topics = new HashMap<Long, SecondaryTag>();
			Iterator<SecondaryTag> it = subjects.get(s).getChildren().values().iterator();
			for (int t = 0; t < 3; t++) {
				
			}
//			threads.add(PostFactory.createQuestionThread(users.get(u), "text" + i, "thread" + i, subjects.get(s), languageTag, topics));
		}
		
		return Response.ok().build();
	}

	@GET
	@Path("/path")
	public String getPath() throws IOException {
		return getClass().getClassLoader().getResource("META-INF/defaultPP.png").getPath();
	}
	
	@GET
	@Path("/caca")
	@Produces(MediaType.APPLICATION_JSON)
	public MainTag putUserIntoDb() {
		
		MainTag tag1 = TagFactory.createMainTag("main");
		service.storeMainTag(tag1);
		
		System.out.println(tag1);
		long id = tag1.getId();
		
		SecondaryTag tag2 = TagFactory.createSecondaryTag("second", tag1);
		service.storeSecondaryTag(tag2);
		
		SecondaryTag tag3 = TagFactory.createSecondaryTag("poopoo", tag1);
		service.storeSecondaryTag(tag3);
		
		MainTag getShit = service.getTag(id);
		System.out.println(getShit);
		
		return getShit;
	}
}
