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

	public static ArrayList<Parcel> getByParameters(int parcelID, String sender, String receiver, Date dateSent, int taxReturnID) {
		User userSender = UserManager.getUser(sender);
		User userReceiver = UserManager.getUser(receiver);
		return ParcelDB.getParcelsByParameter(parcelID, userSender, userReceiver, dateSent, taxReturnID);
	}

}
