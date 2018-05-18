package services.tags;

import java.net.URI;
import java.util.List;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonView;

import dom.tags.ConcreteMainTag;
import dom.tags.ConcreteSecondaryTag;
import dom.tags.ConcreteTag;
import dom.tags.MainTag;
import dom.tags.SecondaryTag;
import dom.tags.Tag;
import dom.tags.TagFactory;
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
	
	/**
	 * Get the list of subjects (MainTag).
	 * 
	 * @return List of all MainTag's in the DB
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.TagParentCentered.class)
	public Response getAllSubjects() {
		List<MainTag> result = service.getAllSubjects();
		return Response.ok(result).build();
	}
	
	/**
	 * Get a single language tag (Tag) by id.
	 * 
	 * @param id
	 * @return The fetched tag
	 */
	@GET
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.TagBase.class)
	public Response getLanguageTag(@PathParam("id") long id) {
		Tag tag = service.getLanguageTag(id);
		if (tag == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(tag).build();
	}
	
	/**
	 * Get a single subject (MainTag) by id.
	 * 
	 * @param id
	 * @return The fetched tag
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.TagParentCentered.class)
	public Response getMainTag(@PathParam("id") long id) {
		MainTag tag = service.getMainTag(id);
		if (tag == null) {
			Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(tag).build();
	}
	
	/**
	 * Get a single topic (SecondaryTag) by id.
	 * 
	 * @param id
	 * @return The fetched tag
	 */
	@GET
	@Path("/{parentId}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.TagChildCentered.class)
	public Response getSecondaryTag(@PathParam("id") long id) {
		SecondaryTag tag = service.getSecondaryTag(id);
		if (tag == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(tag).build();
	}
	
	/**
	 * Add a language tag (Tag) to the DB.
	 * 
	 * @param id
	 * @return The created tag
	 */
	@POST
	@Path("/languages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.TagBase.class)
	public Response addLanguageTag(ConcreteTag tag, @Context UriInfo uriInfo) {
		Tag result = service.addTag(tag);
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
	
	/**
	 * Add a subject (MainTag) to the DB.
	 * 
	 * @param id
	 * @return The created tag
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.TagBase.class)
	public Response addMainTag(ConcreteMainTag tag, @Context UriInfo uriInfo) {
		MainTag result = service.addTag(tag);
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
	
	/**
	 * Add a topic (SecondaryTag) to the DB.
	 * 
	 * @param id
	 * @return The created tag
	 */
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.TagBase.class)
	public Response addSecondaryTag(@PathParam("id") long parentId, ConcreteSecondaryTag tag, @Context UriInfo uriInfo) {
		MainTag parent = service.getMainTag(parentId);
		SecondaryTag result = service.addTag(TagFactory.createSecondaryTag(tag.getName(), parent));
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
}
