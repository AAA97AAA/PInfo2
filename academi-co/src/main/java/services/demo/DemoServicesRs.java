package services.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import dom.content.UserType;
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
	public Response fillDB() throws Throwable {
		
		// Parameters
		int nUsers = 5;
		int nSubjects = 5;
		int nTopics = 5;
		int nPosts = 20;
		
		// Add users
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < nUsers; i++) {
			users.add(userService.addUser(
					UserFactory.createUser("user" + i, "mail" + i + "@mail.com", "secret",
							UserType.REGISTERED.getStringVal())));
		}
		
		// Add tags
		List<MainTag> subjects = new ArrayList<MainTag>();
		for (int i = 0; i < nSubjects; i++) {
			MainTag tmpSubject = tagService.addTag(TagFactory.createMainTag("subject" + i));
			for (int j = 0; j < nTopics; j++) {
				tagService.addTag(tmpSubject.getId(), TagFactory.createSecondaryTag("topic" + i + "-" + j, tmpSubject));
			}
			subjects.add(tagService.getMainTag(tmpSubject.getId()));
			int it = 0;
			Set<Long> keys = new HashSet<Long>();
			for (SecondaryTag topic: subjects.get(i).getChildren()) {
				if (it < 3) {
					keys.add(topic.getId());
				}
				it++;
			}
			subjects.get(i).getChildren().stream()
				.filter(topic -> keys.stream().anyMatch(k -> k == topic.getId())).collect(Collectors.toList());
		}
		Tag lang = tagService.addTag(TagFactory.createTag("english"));
		
		// Add threads
		List<QuestionThread> threads = new ArrayList<QuestionThread>();
		for (int i = 0; i < nPosts; i++) {
			int u = i % nUsers;
			int s = i % nSubjects;
			threads.add(postService.addPost(
					PostFactory.createQuestionThread(users.get(u), "text" + i, "thread" + i,
							subjects.get(s), lang, subjects.get(s).getChildren()))
					);
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Finalize
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
