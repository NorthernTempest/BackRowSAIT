package databaseAccess;

import java.util.ArrayList;

import domain.TaxReturn;
import domain.User;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the tax_return table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class TaxReturnDB {

	/**
	 * Establishes a connection with the database and inserts the TaxReturn object passed
	 * into this method into the tax_return table.
	 * @param taxReturn TaxReturn to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(TaxReturn taxReturn) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and updates the TaxReturn in the tax_return
	 * table that shares a Primary Key with the TaxReturn being passed into this method.
	 * All values of the TaxReturn in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param taxReturn TaxReturn to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean update(TaxReturn taxReturn) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the TaxReturn from the
	 * tax_return table that has a Primary Key matching the taxReturnID being passed
	 * into this method.
	 * @param taxReturnID taxReturnID of the TaxReturn to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(int taxReturnID) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the TaxReturn in the
	 * tax_return table that has a Primary Key matching the taxReturnID being passed
	 * into this method.
	 * @param taxReturnID taxReturnID of the TaxReturn to retrieve from the database
	 * @return TaxReturn that contains the information of the requested TaxReturn
	 */
	public static TaxReturn get(int taxReturnID) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects the TaxReturns in the
	 * tax_return table that have a user matching the User being passed
	 * into this method.
	 * @param user the User of the TaxReturn to retrieve from the database
	 * @return TaxReturn that contains the information of the requested TaxReturn
	 */
	public static ArrayList<TaxReturn> getByUser(User user) {
		return new ArrayList<TaxReturn>();
	}
	
	/**
	 * Establishes a connection with the database and selects all the TaxReturns
	 * within the tax_return table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<TaxReturn> containing all of the TaxReturns from the database
	 */
	public static ArrayList<TaxReturn> getAll() {
		return null;
	}
}
