package services.documentsManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
	@Path("/{id}")
	@Produces("application/json")
	public Document getAdvertisementBanner(@PathParam("id") long id) {
		
		return advertisementBannerService.getAdvertisementBanner(id);
		
	}
	
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public void addAdvertisementBanner(Document advertisementBanner) {
		
		advertisementBannerService.addAdvertisementBanner(advertisementBanner);
		
	}
	
	
	@DELETE
	@Path("/delete")
	@Consumes("application/json")
	public void removeAdvertisementBanner(Document advertisementBanner) {
		
		advertisementBannerService.removeAdvertisementBanner(advertisementBanner);
		
	}
	
	
}
