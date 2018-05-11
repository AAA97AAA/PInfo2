package services.tags;

import java.net.URI;
import java.net.URISyntaxException;

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

import com.fasterxml.jackson.annotation.JsonView;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import services.utility.View;

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
	@JsonView(View.Base.class)
	public Response getLanguageTag(@PathParam("id") long id) {
		Tag tag = service.getLanguageTag(id);
		return Response.ok(tag).build();
	}
	
	@GET
	@Path("/subjects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMainTag(@PathParam("id") long id) {
		MainTag tag = service.getMainTag(id);
		return Response.ok(tag).build();
	}
	
	@GET
	@Path("/topics/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSecondaryTag(@PathParam("id") long id) {
		SecondaryTag tag = service.getSecondaryTag(id);
		return Response.ok(tag).build();
	}
	
	@POST
	@Path("/languages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLanguageTag(ConcreteTag tag, @Context UriInfo uriInfo) throws URISyntaxException {
		Tag result = service.addTag(tag);
		return Response.created(new URI(uriInfo.getPath() + "/" + result.getId())).entity(result).build();
	}
	
	@POST
	@Path("/subjects")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMainTag(ConcreteMainTag tag, @Context UriInfo uriInfo) throws URISyntaxException {
		MainTag result = service.addTag(tag);
		return Response.created(new URI(uriInfo.getPath() + "/" + result.getId())).entity(result).build();
	}
	
	@POST
	@Path("/topics")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSecondaryTag(ConcreteSecondaryTag tag, @Context UriInfo uriInfo) throws URISyntaxException {
		SecondaryTag result = service.addTag(tag);
		return Response.created(new URI(uriInfo.getPath() + "/" + result.getId())).entity(result).build();
	}
}
