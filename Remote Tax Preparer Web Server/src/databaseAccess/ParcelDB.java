package databaseAccess;

import java.util.ArrayList;
import java.util.Date;

import domain.Parcel;
import domain.User;

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
	public static boolean insert(Parcel parcel) {
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
	public static boolean update(Parcel parcel) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the Parcel from the
	 * parcel table that has a Primary Key matching the parcelID being passed
	 * into this method.
	 * @param parcelID parcelID of the Parcel to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(int parcelID) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the Parcel in the
	 * parcel table that has a Primary Key matching the parcelID being passed
	 * into this method.
	 * @param parcelID parcelID of the Parcel to retrieve from the database
	 * @return Parcel that contains the information of the requested Parcel
	 */
	public static Parcel get(int parcelID) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Parcels
	 * within the parcel table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Parcel> containing all of the Parcels from the database
	 */
	public static ArrayList<Parcel> getAll() {
		return null;
	}
	

	/**
	 * Method that, using the supplied parameters, retrieves all
	 * parcels in the database with that information and returns a list
	 * to the calling program.
	 * @param parcelID
	 * @param sender
	 * @param receiver
	 * @param dateSent
	 * @param taxReturnID
	 * @return ArrayList<Parcel> containing all relevant Parcels from the database
	 */
	public static ArrayList<Parcel> getParcelsByParameter(int parcelID, User sender, User receiver, Date dateSent, int taxReturnID) {
		//TODO
		return new ArrayList<Parcel>();
	}
}
