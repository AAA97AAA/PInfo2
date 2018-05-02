package moderatorsManager;

import dom.content.User;

/**
 * User request to become a moderator representation.
 * 
 * @author kaikoveritch
 *
 */
public interface ModeratorPromotionRequest {

	public void accept();
	
	public long getId();
	
	public User getOriginator();
}
