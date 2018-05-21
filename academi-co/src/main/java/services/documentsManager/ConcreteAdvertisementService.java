package services.documentsManager;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dom.documentsManager.Advertisement;
import dom.documentsManager.AdvertisementPointer;
import dom.documentsManager.ConcreteAdvertisement;

/**
 * Implementation of the service to fetch and update the advertisement
 * banner displayed on the site.
 * 
 * @author kaikoveritch
 *
 */
@Stateless
public class ConcreteAdvertisementService implements AdvertisementService {

	// Serial version (auto-generated)
	private static final long serialVersionUID = 8023340273808417389L;
	
	@PersistenceContext
	private EntityManager entitManager;

	/**
	 * Returns a list of all advertisement banners currently
	 * in the database.
	 */
	@Override
	public List<Advertisement> getAllAdvertisements() {
		return entitManager.createNamedQuery("Advertisement.fetchAll", Advertisement.class).getResultList();
	}

	/**
	 * Returns the advertisement banner to display at the moment.
	 */
	@Override
	public Advertisement getCurrentAdvertisement() {
		AdvertisementPointer pointer = getPointer();
		if (pointer == null) {
			return null;
		}
		return pointer.getCurrent();
	}

	/**
	 * Switches to the next advertisement banner in line to be
	 * displayed on the site.
	 */
	@Override
	public long nextAdvertisement() {
		System.out.println("called");
		
		// Fetch entries list
		List<Advertisement> allAds = getAllAdvertisements();
		
		// Default selection
		Advertisement defaultSelection;
		if (allAds.isEmpty()) {
			defaultSelection = null;
		} else {
			defaultSelection = allAds.get(0);
		}
		
		// Fetch the current pointer
		AdvertisementPointer pointer = getPointer();
		
		// Initialize or update
		if (pointer == null) { // no pointer
			pointer = new AdvertisementPointer(defaultSelection);
			entitManager.persist(pointer);
		} else if (pointer.getCurrent() == null) { // pointer to nothing
			pointer.setCurrent(defaultSelection);
		} else { // pointer to an ad
			int where = -1;
			int size = allAds.size();
			for (int i = 0; i < size; i++) {
				if (allAds.get(i).getId() == pointer.getCurrent().getId()) {
					where = i;
					break;
				}
			}
			if (where >= (size - 1) || where < 0) {
				pointer.setCurrent(defaultSelection);
			} else {
				pointer.setCurrent(allAds.get(where + 1));
			}
		}
		
		// Return new pointed id or -1
		if (pointer.getCurrent() == null) {
			return -1;
		}
		return pointer.getCurrent().getId();
	}

	/**
	 * Adds an advertisement banner to the available roster.
	 */
	@Override
	public Advertisement addAdvertisement(Advertisement advertisement) {
		entitManager.persist(advertisement);
		return advertisement;
	}

	/**
	 * Removes the target advertisement banner from the available roster.
	 */
	@Override
	public boolean removeAdvertisement(long id) {
		
		// Check for existence of the banner
		Advertisement target = entitManager.find(ConcreteAdvertisement.class, id);
		if (target == null) {
			return false;
		}
		
		// Check if the pointer is on this current banner
		AdvertisementPointer pointer = getPointer();
		boolean isPointed = false;
		if (pointer != null) {
			isPointed = pointer.getCurrent().getId() == id;
		}
		
		// Remove the target banner and reset the pointer if needed
		if (isPointed) {
			pointer.setCurrent(null);
		}
		entitManager.remove(target);
		if (isPointed) {
			nextAdvertisement();
		}
		
		return true;
	}
	
	private AdvertisementPointer getPointer() {
		return entitManager.find(AdvertisementPointer.class, AdvertisementPointer.ADDRESS);
	}

}
