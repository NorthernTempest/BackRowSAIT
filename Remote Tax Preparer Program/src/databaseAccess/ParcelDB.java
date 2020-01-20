package databaseAccess;

import java.util.ArrayList;

import domain.Parcel;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the parcel table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class ParcelDB {

	/**
	 * Establishes a connection with the database and inserts the Parcel object passed
	 * into this method into the parcel table.
	 * @param parcel Parcel to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Parcel parcel) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and updates the Parcel in the parcel
	 * table that shares a Primary Key with the Parcel being passed into this method.
	 * All values of the Parcel in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param parcel Parcel to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Parcel parcel) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the Parcel from the
	 * parcel table that has a Primary Key matching the parcelID being passed
	 * into this method.
	 * @param parcelID parcelID of the Parcel to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int parcelID) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the Parcel in the
	 * parcel table that has a Primary Key matching the parcelID being passed
	 * into this method.
	 * @param parcelID parcelID of the Parcel to retrieve from the database
	 * @return Parcel that contains the information of the requested Parcel
	 */
	public Parcel get(int parcelID) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Parcels
	 * within the parcel table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Parcel> containing all of the Parcels from the database
	 */
	public ArrayList<Parcel> getAll() {
		return null;
	}
	
	/**
	 * Method that, using the supplied taxReturnID and email, retrieves all
	 * parcels in the database with that information and returns a list
	 * to the calling program.
	 * @param taxReturnID taxReturnID
	 * @param email email
	 * @return ArrayList<Parcel> containing related parcels
	 */
	public ArrayList<Parcel> getAllParcels(String taxReturnID, String email) {
		return null;
	}
}
