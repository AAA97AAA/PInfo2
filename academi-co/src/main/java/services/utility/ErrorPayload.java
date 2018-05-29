package services.utility;

import java.io.Serializable;

/**
 * Serializable representation of an HTTP error response (because Angularjs...)
 * 
 * @author kaikoveritch
 *
 */
public class ErrorPayload implements Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = 2250069019948428540L;

	private String message;
	
	
	/***** Constructors *****/

	public ErrorPayload() {}

	public ErrorPayload(String message) {
		this.message = message;
	}
	
	
	/***** Getters/Setters *****/

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
