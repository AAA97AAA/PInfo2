package dom.moderatorsManager;

import dom.content.User;

/**
 * Instatiator for users' requests to become moderators.
 * 
 * @author kaikoveritch
 *
 */
public class ModeratorPromotionRequestFactory {

	static public ModeratorPromotionRequest createRequest(User originator) {
		
		// Prevents unauthorized users from applying
		if (!originator.isCanBeModerator()) {
			return null;
		}
		
		// If authorized, prevent further additional requests and create a new one.
		originator.setCanBeModerator(false);
		return new ConcreteModeratorPromotionRequest(originator);
	}
}
