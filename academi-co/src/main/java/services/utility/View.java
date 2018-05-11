package services.utility;

/**
 * Class for customizing JSON marshalling dynamically.
 * 
 * @author kaikoveritch
 *
 */
public class View {
	
	public static interface Base {};
	
	public static interface ParentCentered extends Base {};
	
	public static interface ChildCentered extends Base {};
}
