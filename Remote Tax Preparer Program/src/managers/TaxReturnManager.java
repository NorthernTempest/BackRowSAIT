package managers;

import java.util.ArrayList;

import domain.TaxReturn;

/**
 * 
 * Class Description: 	Class that communicates with the TaxReturnDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class TaxReturnManager {

	/**
	 * Takes the TaxReturn object passed into the method and calls the insert method
	 * of the TaxReturnDB, passing the TaxReturn.
	 * @param taxReturn TaxReturn to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(TaxReturn taxReturn) {
		return false;
	}
	
	/**
	 * Takes the TaxReturn object passed into the method and calls the update method
	 * of the TaxReturnDB, passing the TaxReturn.
	 * @param payment Payment in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(TaxReturn taxReturn) {
		return false;
	}
	
	/**
	 * Takes the taxReturnID passed into the method and calls the delete method
	 * of the TaxReturnDB, passing the taxReturnID.
	 * @param taxReturnID taxReturnID of the TaxReturn in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int taxReturnID) {
		return false;
	}
	
	/**
	 * Takes the taxReturnID passed into the method and calls the get method
	 * of the TaxReturnDB, passing the taxReturnID.
	 * @param taxReturnID taxReturnID of the TaxReturn in the database to retrieve
	 * @return TaxReturn containing the information of the requested TaxReturn
	 */
	public TaxReturn get(int taxReturnID) {
		return null;
	}
	
	/**
	 * Calls the getAll method of the TaxReturnDB.
	 * @return ArrayList<TaxReturn> containing all the TaxReturns in the database
	 */
	public ArrayList<TaxReturn> getAll() {
		return null;
	}
}
