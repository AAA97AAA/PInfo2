package services.documentsManager;

import java.io.Serializable;

import javax.ejb.Local;

import dom.documentsManager.Document;

@Local
public interface AdvertisementBannerService extends Serializable {
	
	public Document getAdvertisementBanner(long id);
	
	public Document addAdvertisementBanner(Document advertisementBanner);
	
	public void removeAdvertisementBanner(long id);
	
}
