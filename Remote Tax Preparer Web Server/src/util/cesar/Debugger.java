package util.cesar;
/**
 * Class description: Debugger class for printing debug messages to console
 *
 * @author Cesar Guzman
 *  
 * Mar 26, 2019
 */
public class Debugger {
	public static void log(Object o){
	    if(Debugger.isEnabled()) {
	    	if(o != null)
	    		System.out.println("DEBUG: " + o.toString());
	    	else
	    		System.out.println("DEBUG: null");
	    }           
	}

	/**
	 * Set to true to enable debug messages globally
	 * @return debugger enabledness
	 */
	private static boolean isEnabled() {
		return true;
	}
}
