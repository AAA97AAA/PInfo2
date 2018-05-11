package services.utility;

/**
 * Class for customizing JSON marshalling dynamically.
 * 
 * @author kaikoveritch
 *
 */
public class View {
	
	// To be always shown
	public static interface Base {};
	
	// Shows full parent and partial children
	public static interface ParentCentered extends Base {};
	
	// Shows full child and partial parent
	public static interface ChildCentered extends Base {};
}
