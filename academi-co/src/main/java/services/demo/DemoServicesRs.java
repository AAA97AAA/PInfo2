package services.demo;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.TagFactory;

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
