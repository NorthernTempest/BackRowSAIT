package domain;

import java.util.ArrayList;

/**
 * 
 * Singleton manager for TaxReturn objects.
 * 
 * @author Jesse Goerzen
 *
 */
public class TaxReturnManager {
	
	/**
	 * The list of all tax returns.
	 */
	private ArrayList<TaxReturn> taxReturnList;
	
	/**
	 * 
	 * Creates a new tax return with the current year, and with the given user.
	 * 
	 * @return The id of the newly created TaxReturn
	 */
	public String createTaxReturn() {
		String id = null;
		// todo
		return id;
	}
	
	/**
	 * 
	 * Views the tax return with the given id.
	 * 
	 * @param id The id of the tax return with the given id.
	 * @return String A formated list of properties of the tax return for display to the user.
	 */
	public String viewTaxReturn( String id ) {
		// todo
		return null;
	}
}