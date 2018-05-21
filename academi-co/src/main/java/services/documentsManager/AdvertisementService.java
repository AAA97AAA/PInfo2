package services.documentsManager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import dom.documentsManager.Advertisement;

/**
 * Service definition for telling and changing which advertisement banner
 * is shown on the site.
 * 
 * @author kaikoveritch
 *
 */
@Local
public interface AdvertisementService extends Serializable {
	
	public List<Advertisement> getAllAdvertisements();

	public Advertisement getCurrentAdvertisement();
	
	public long nextAdvertisement();
	
	public Advertisement addAdvertisement(Advertisement advertisement);
	
	public boolean removeAdvertisement(long id);
}
