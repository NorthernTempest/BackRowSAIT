package managers;

import java.util.ArrayList;

import domain.Household;

/**
 * 
 * Class Description: 	Class that communicates with the HouseholdDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class HouseholdManager {
	
	/**
	 * Takes the Household object passed into the method and calls the insert method
	 * of the HouseholdDB, passing the Household.
	 * @param household Household to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Household household) {
		return false;
	}
	
	/**
	 * Takes the Household object passed into the method and calls the update method
	 * of the HouseholdDB, passing the Household.
	 * @param household Household in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Household household) {
		return false;
	}
	
	/**
	 * Takes the householdID passed into the method and calls the delete method
	 * of the HouseholdDB, passing the householdID.
	 * @param householdID householdID of the Household in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int householdID) {
		return false;
	}
	
	/**
	 * Takes the householdID passed into the method and calls the get method
	 * of the HouseholdDB, passing the householdID.
	 * @param householdID householdID of the Household in the database to retrieve
	 * @return Household containing the information of the requested Household
	 */
	public Household get(int householdID) {
		return null;
	}
	
	/**
	 * Calls the getAll method of the HouseholdDB.
	 * @return ArrayList<Household> containing all the Households in the database
	 */
	public ArrayList<Household> getAll() {
		return null;
	}
}
