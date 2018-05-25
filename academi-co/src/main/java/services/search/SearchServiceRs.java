package services.search;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonView;

import dom.content.QuestionThread;
import services.utility.View;

/**
 * REST service class for searching threads on the website.
 * 
 * @author kaikoveritch
 *
 */
@Path("/search")
public class SearchServiceRs {
	
	@Inject
	private SearchService service;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(View.PostParentCentered.class)
	public Response search(SearchInput input, @QueryParam("from") @DefaultValue("0") int from,
			@QueryParam("len") @DefaultValue("0") int size) {
		SearchResult<QuestionThread> results = service.search(input, from, size);
		return Response.ok(results).build();
	}
}
