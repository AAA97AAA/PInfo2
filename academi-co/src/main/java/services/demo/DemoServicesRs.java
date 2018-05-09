package services.demo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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

	
	@GET
	@Path("/caca")
	public void putUserIntoDb() {
		
		MainTag tag1 = TagFactory.createMainTag("main");
		service.storeMainTag(tag1);
		
		System.out.println(tag1);
		
		SecondaryTag tag2 = TagFactory.createSecondaryTag("second", tag1);
		service.storeSecondaryTag(tag2);
		
		System.out.println(tag2);
	}
}
