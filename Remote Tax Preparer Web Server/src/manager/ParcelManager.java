package manager;

import java.util.ArrayList;
import java.util.Date;

import databaseAccess.ParcelDB;
import domain.Parcel;
import domain.User;

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
	 */
	public static ArrayList<Parcel> getParcels(int parcelID, String senderEmail, String receiverEmail, Date dateSent, int taxReturnID) {
		User userSender = UserManager.getUser(senderEmail);
		User userReceiver = UserManager.getUser(receiverEmail);
		return ParcelDB.getParcelsByParameter(parcelID, userSender, userReceiver, dateSent, taxReturnID);
	}

	public static ArrayList<Parcel> getByYear(String recieverEmail, int year) {
		return getParcels(-1, null, recieverEmail, null, TaxReturnManager.getID(recieverEmail, year));
	}

}
