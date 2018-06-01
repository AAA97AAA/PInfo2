package services.utility;

/**
 * Interface for customizing JSON marshalling dynamically.
 * 
 * @author kaikoveritch
 *
 */
public interface View {
	
	/***** Document views *****/
	
	// Minimal discriminant data (id)
	public static interface DocumentMinimal {};
	
	// Main information about the entity
	public static interface DocumentBase {};
	
	// Main information about an advertisement
	public static interface AdvertisementBase extends DocumentBase {};
	
	
	/***** Tag views *****/
	
	// Minimal discriminant data (id)
	public static interface TagMinimal {};
	
	// Main information about the entity
	public static interface TagBase {};
	
	// Shows full parent and partial children
	public static interface TagParentCentered extends TagBase, TagMinimal {};
	
	// Shows full child and partial parent
	public static interface TagChildCentered extends TagBase, TagMinimal {};
	
	
	/***** Post views *****/
	
	// Minimal discriminant data (id)
	public static interface PostMinimal {};
	
	// Information about the state of the post
	public static interface PostState {}
	
	// A post's score
	public static interface PostScore {};
	
	// Information about the post's votes
	public static interface PostVote extends PostScore, UserMinimal {};
	
	// Base non-discriminant data on the post
	public static interface PostBase {};
	
//	// View for listing generic posts
//	public static interface PostGeneric extends PostBase, PostScore, PostMinimal {};
	
	// Main information about the post
	public static interface PostCore extends PostMinimal, PostBase, PostVote, PostState, TagBase {};
	
	// Information about the author
	public static interface PostAuthor extends UserMinimal {};
	
	// Data required for creating a new post
	public static interface PostNew extends PostBase, PostAuthor, UserMinimal, TagMinimal {};
	
	// Shows full parent and partial children
	public static interface PostParentCentered extends PostCore, PostAuthor, UserPost {};
	
	// Shows full child and partial parent
	public static interface PostChildCentered extends PostCore, PostAuthor, UserPost {};
	
	
	/***** User views *****/
	
	// Information not to be shown
	public static interface UserNope {};
	
	// Minimal user discriminant data (id)
	public static interface UserMinimal {};
	
	// User visual representation
	public static interface UserVisuals extends DocumentBase {};
	
	// User chosen identifier
	public static interface UserName {};
	
	// User contact information
	public static interface UserContact {};
	
	// User data that should not be sent on GET
	public static interface UserSecret {};
	
	// User role identifiers
	public static interface UserType {};
	
	// User session data that can be modified
	public static interface UserSessionModifiable {};
	
	// User profile page data that can be modified
	public static interface UserProfileModifiable {};
		
	// User data shown on a post
	public static interface UserPost extends UserMinimal, UserVisuals, UserName {};
	
	// Data required for creating a user
	public static interface UserNew extends UserName, UserContact, UserSecret, UserType {};
	
	// User data that can be modified through a client-side request
	public static interface UserModifiable extends UserSecret, UserVisuals, UserType, UserSessionModifiable, UserProfileModifiable {};
	
	// Information about a user for sessions
	public static interface UserSession extends UserPost, UserType, UserSessionModifiable {};
	
	// Information about a user for profile pages
	public static interface UserProfile extends UserPost, PostCore, UserProfileModifiable, UserType {};
	
	// Information that will (normally) not be sent with the user
	public static interface UserExcessive {};
}
