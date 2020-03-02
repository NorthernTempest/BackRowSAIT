package manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;

import databaseAccess.ParcelDB;
import domain.Parcel;
import exception.ConfigException;
import service.ConfigService;

/**
 * 
 * Class Description: Class that communicates with the ParcelDB class as a proxy
 * to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public final class ParcelManager {
	
	private static boolean init;
	
	private static int expirationDays;

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
			int year) throws ConfigException {
		return ParcelDB.getParcelsByParameter(parcelID, senderEmail, receiverEmail, dateSent, year);
	}

	public static ArrayList<Parcel> getByYear(String recieverEmail, int year) throws ConfigException {
		return getParcels(-1, null, recieverEmail, null, year);
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

	/**
	 * @param fileItemsList
	 * @param subject
	 * @param message
	 * @param email
	 * @param reciever
	 * @param dateSent
	 * @param expiryDate
	 * @param taxYear
	 * @return
	 * @throws NumberFormatException
	 * @throws ConfigException
	 */
	public static boolean createParcel(List<FileItem> fileItemsList, String subject, String message, String email,
			String reciever, Date dateSent, Date expiryDate, int taxYear) throws NumberFormatException, ConfigException {
		init();
		
		//Set expiration date
		Calendar c = Calendar.getInstance();
		c.setTime(dateSent);
		c.add(Calendar.DAY_OF_MONTH, expirationDays);  
		Date expDate = c.getTime();
		
		Parcel parcel = new Parcel(0, subject, message, email, null, dateSent, expDate, taxYear);
		
		if(ParcelDB.insert(parcel)){
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * @throws ConfigException
	 */
	private static void init() throws ConfigException {
		if (!init) {
			expirationDays = Integer.parseInt(ConfigService.fetchFromConfig("PARCEL_EXPIRATION_DAYS:"));
			init = true;
		}
	}

}
