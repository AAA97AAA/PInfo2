package dom.moderatorsManager;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dom.content.User;

/**
 * User request to become a moderator representation.
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteModeratorPromotionRequest.class)
public interface ModeratorPromotionRequest {

	public void accept();
	
	public long getId();
	
	public User getOriginator();
}
