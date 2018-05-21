package services.documentsManager;

import java.net.URI;
import java.util.List;
import java.util.Timer;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import dom.documentsManager.Advertisement;

/**
 * REST services for fetching and updating advertisement banners
 * and their cycle.
 * 
 * @author kaikoveritch
 *
 */
@Path("advertisements")
public class AdvertisementServiceRs {
	
	@Inject
	private AdvertisementService service;

	/**
	 * Fetches all the available advertisement banners
	 * @return
	 */
	@GET
	@Path("all")
	public Response getAllAdvertisements() {
		List<Advertisement> allAds = service.getAllAdvertisements();
		return Response.ok(allAds).build();
	}
	
	/**
	 * Fetches the advertisement banner to be displayed
	 * @return
	 */
	@GET
	@Path("/")
	public Response getCurrentAdvertisement() {
		Advertisement ad = service.getCurrentAdvertisement();
		if (ad == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(ad).build();
	}
	
	/**
	 * Adds an advertisement banner to the available roster
	 * @param ad
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Path("/")
	public Response addAdvertisement(Advertisement ad, @Context UriInfo uriInfo) {
		Advertisement result = service.addAdvertisement(ad);
		URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.getId())).build();
		return Response.created(location).entity(result).build();
	}
	
	/**
	 * Removes an advertisement banner from the available roster
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	public Response removeAdvertisement(@PathParam("id") long id) {
		if (service.removeAdvertisement(id)) {
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	/**
	 * Updates the delay between the changes of advertisement banner
	 * @param period
	 * @return
	 */
	@PUT
	@Path("setPeriod")
	public Response changePeriod(Integer period, @Context ServletContext context) {
		// Fetch running timer
		Timer timer = (Timer) context.getAttribute("timer");
		
		// Stop if running
		if (timer != null) {
			timer.cancel();
		}
		
		// Redefine
		Timer newTimer = new Timer();
		BannersCycleTask newTask = new BannersCycleTask(service);
		newTimer.scheduleAtFixedRate(newTask, period, period);
		
		// Re-attach
		context.setAttribute("timer", newTimer);
		
		return Response.ok().build();
	}
}
