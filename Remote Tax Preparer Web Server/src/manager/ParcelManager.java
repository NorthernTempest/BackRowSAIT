package manager;

import java.util.ArrayList;
import java.util.Date;

import databaseAccess.ParcelDB;
import domain.Parcel;
import domain.User;
import exception.ConfigException;

/**
 * 
 * Class Description: Class that communicates with the ParcelDB class as a proxy
 * to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public final class ParcelManager {

	/**
	 * @param parcelID
	 * @param senderEmail
	 * @param receiverEmail
	 * @param dateSent
	 * @param taxReturnID
	 * @return
	 * @throws ConfigException 
	 */
	public static ArrayList<Parcel> getParcels(int parcelID, String senderEmail, String receiverEmail, Date dateSent, int taxReturnID) throws ConfigException {
		return ParcelDB.getParcelsByParameter(parcelID, senderEmail, receiverEmail, dateSent, taxReturnID);
	}

	public static ArrayList<Parcel> getByYear(String recieverEmail, int year) throws ConfigException {
		return getParcels(-1, null, recieverEmail, null, TaxReturnManager.getID(recieverEmail, year));
	}

}
