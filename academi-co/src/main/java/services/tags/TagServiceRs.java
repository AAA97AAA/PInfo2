package services.tags;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.Tag;

/**
 * REST API of the TagService.
 * 
 * @author kaikoveritch
 *
 */
@Path("/tags")
public class TagServiceRs {

	@Inject
	private TagService service;
	
	@GET
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tag getLanguageTag(@PathParam("id") long id) {
		return service.getLanguageTag(id);
	}
	
	@GET
	@Path("/subjects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tag getMainTag(@PathParam("id") long id) {
		return service.getMainTag(id);
	}
	
	@GET
	@Path("/topics/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tag getSecondaryTag(@PathParam("id") long id) {
		return service.getSecondaryTag(id);
	}
	
	@POST
	@Path("/languages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLanguageTag(ConcreteTag tag, @Context UriInfo uriInfo) {
		return Response.created(uriInfo.getAbsolutePath()).entity(service.addTag(tag)).build();
	}
	
	@POST
	@Path("/subjects")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMainTag(ConcreteMainTag tag, @Context UriInfo uriInfo) {
		return Response.created(uriInfo.getAbsolutePath()).entity(service.addTag(tag)).build();
	}
	
	@POST
	@Path("/topics")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSecondaryTag(ConcreteSecondaryTag tag, @Context UriInfo uriInfo) {
		return Response.created(uriInfo.getAbsolutePath()).entity(service.addTag(tag)).build();
	}
}
