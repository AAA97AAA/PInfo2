package services.utility;

/**
 * Utility service for handling exceptions and validation constraints.
 * 
 * @author kaikoveritch
 *
 */
public interface ValidationHandling {

	/**
	 * Takes an exception and returns its root cause.
	 * @param e
	 * @return
	 */
	static public Throwable getExceptionRootCause(Throwable e) {
		Throwable result = e;
		Throwable cause = null;
		while ((cause = result.getCause()) != null) {
			result = cause;
		}
		return result;
	}
}
