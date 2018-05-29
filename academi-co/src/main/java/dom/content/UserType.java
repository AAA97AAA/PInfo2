package dom.content;

/**
 * Enumeration of different user types.
 * 
 * @author kaikoveritch
 *
 */
public enum UserType {
	BANNED("BANNED"),
	REGISTERED("REGISTERED"),
	MODERATOR("MODERATOR"),
	ADMINISTRATOR("ADMINISTRATOR");
	
	private String stringVal;
	
	private UserType(String stringVal) {
		this.stringVal = stringVal;
	}
	
	public String getStringVal() {
		return stringVal;
	}
}
