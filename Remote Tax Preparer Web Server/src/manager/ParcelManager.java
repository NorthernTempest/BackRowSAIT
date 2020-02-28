package manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;

import databaseAccess.ParcelDB;
import domain.Parcel;
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
	public static ArrayList<Parcel> getParcels(int parcelID, String senderEmail, String receiverEmail, Date dateSent,
			int taxReturnID) throws ConfigException {
		return ParcelDB.getParcelsByParameter(parcelID, senderEmail, receiverEmail, dateSent, taxReturnID);
	}

	public static ArrayList<Parcel> getByYear(String recieverEmail, int year) throws ConfigException {
		return getParcels(-1, null, recieverEmail, null, TaxReturnManager.getID(recieverEmail, year));
	}

	public static Parcel get(int parcelID) throws ConfigException {
		return ParcelDB.get(parcelID);
	}

	/**
	 * Returns true if email is allowed to view this parcel
	 * 
	 * @param email    the email to check against
	 * @param parcelID the parcel ID to check
	 * @return true if email is allowed to view this parcel. false if not.
	 * @throws ConfigException
	 */
	public static boolean isVisibleToUser(String email, int parcelID) throws ConfigException {
		return ParcelDB.isVisibleToUser(email, parcelID);
	}

	public static boolean createParcel(List<FileItem> fileItemsList, String subject, String message, String email,
			Object object, Date date, Object object2, int taxYear) {
		// TODO Auto-generated method stub
		return false;

	}

}
