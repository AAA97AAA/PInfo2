package services.documentsManager;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dom.documentsManager.Document;

/**
 * JAX RS annotated class for REST services, using AdvertisementBannerService (@Inject annotation)
 * 
 * @author petrbinko
 *
 */

@Path("/advertisementBanners")
public class AdvertisementBannerServiceRs {
	
	@Inject
	private AdvertisementBannerService advertisementBannerService;
	
	
	@GET
	@Path("/getById/{id}")
	@Produces("application/json")
	public Response getAdvertisementBanner(@PathParam("id") long id) {
		
		Document advertisementBanner = advertisementBannerService.getAdvertisementBanner(id);
		
		return Response.ok().entity(advertisementBanner).build();
		
	}
	
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addAdvertisementBanner(Document advertisementBanner) throws URISyntaxException {
		
		advertisementBannerService.addAdvertisementBanner(advertisementBanner);
		return Response.status(201).contentLocation(new URI("advertisementBanners/getById/" + advertisementBanner.getId())).build();
		
	}
	
	
	@DELETE
	@Path("/delete")
	@Consumes("application/json")
	public void removeAdvertisementBanner(long id) {
		
		advertisementBannerService.removeAdvertisementBanner(id);
		
	}
	
	
}
