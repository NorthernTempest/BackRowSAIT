package exception;

/**
 * An exception thrown by a problen with fetching information from the config file.
 * 
 * @author Jesse Goerzen
 */
public class ConfigException extends Exception {
	private static final long serialVersionUID = 3473600331951384682L;
	
	/**
	 * @see Exception#Exception()
	 */
	public ConfigException() {
		super();
	}
	
	/**
	 * @see Exception#Exception(String)
	 */
	public ConfigException(String arg0) {
		super(arg0);
	}
	
	/**
	 * @see Exception#Exception(Throwable)
	 */
	public ConfigException(Throwable arg0) {
		super(arg0);
	}
	
	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public ConfigException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * @see Exception#Exception(String, Throwable, boolean, boolean)
	 */
	public ConfigException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
