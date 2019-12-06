package managers;

import java.util.ArrayList;

import domain.Parcel;

/**
 * 
 * Class Description: 	Class that communicates with the ParcelDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class ParcelManager {

	/**
	 * Takes the Parcel object passed into the method and calls the insert method
	 * of the ParcelDB, passing the Parcel.
	 * @param parcel Parcel to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Parcel parcel) {
		return false;
	}
	
	/**
	 * Takes the Parcel object passed into the method and calls the update method
	 * of the ParcelDB, passing the Parcel.
	 * @param parcel Parcel in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Parcel parcel) {
		return false;
	}
	
	/**
	 * Takes the parcelID passed into the method and calls the delete method
	 * of the ParcelDB, passing the parcelID.
	 * @param parcelID parcelID of the Parcel in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int parcelID) {
		return false;
	}
	
	/**
	 * Takes the parcelID passed into the method and calls the get method
	 * of the ParcelDB, passing the parcelID.
	 * @param parcelID parcelID of the Parcel in the database to retrieve
	 * @return Parcel containing the information of the requested Parcel
	 */
	public Parcel get(int parcelID) {
		return null;
	}
	
	/**
	 * Calls the getAll method of the ParcelDB.
	 * @return ArrayList<Parcel> containing all the Parcels in the database
	 */
	public ArrayList<Parcel> getAll() {
		return null;
	}
}
