package services.documentsManager;

import java.util.TimerTask;

/**
 * Implementation of the banner cyclying task.
 * Calls the advertisement services at given intervals to switch the shown banner.
 * 
 * @author kaikoveritch
 *
 */
public class BannersCycleTask extends TimerTask {
	
	AdvertisementService service;
	
	public BannersCycleTask(AdvertisementService service) {
		this.service = service;
	}

	@Override
	public void run() {
		service.nextAdvertisement();
	}

}
