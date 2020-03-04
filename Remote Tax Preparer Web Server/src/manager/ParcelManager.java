package manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;

import databaseAccess.ParcelDB;
import domain.Document;
import domain.Parcel;
import exception.ConfigException;
import service.ConfigService;
import service.EncryptionService;

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
	 * @param year
	 * @return
	 * @throws ConfigException
	 */
	public static ArrayList<Parcel> getParcels(int parcelID, String senderEmail, String receiverEmail, Date dateSent,
			int year) throws ConfigException {
		return ParcelDB.getParcelsByParameter(parcelID, senderEmail, receiverEmail, dateSent, year);
	}

	/**
	 * @param recieverEmail
	 * @param year
	 * @return
	 * @throws ConfigException
	 */
	public static ArrayList<Parcel> getByYear(String recieverEmail, int year) throws ConfigException {
		return getParcels(-1, null, recieverEmail, null, year);
	}

	/**
	 * @param parcelID
	 * @return
	 * @throws ConfigException
	 */
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
	 * @param fileItemsList the List of FileItems to include with the Parcel
	 * @param subject the subject
	 * @param message the message
	 * @param sender the email of the sender
	 * @param receiver the email of the receiver
	 * @param dateSent the date parcel was created
	 * @param expiryDate the date parcel will expire
	 * @param taxYear the tax year this parcel is associated with
	 * @return true if parcel created in database successfully
	 * @throws NumberFormatException
	 * @throws ConfigException
	 */
	public static boolean createParcel(ArrayList<Document> documents, String subject, String message, String sender,
			String receiver, Date dateSent, Date expiryDate, int taxYear) throws NumberFormatException, ConfigException {
		init();
		
		//Set expiration date
		Calendar c = Calendar.getInstance();
		c.setTime(dateSent);
		c.add(Calendar.DAY_OF_MONTH, expirationDays);  
		Date expDate = c.getTime();
		
		Parcel parcel = new Parcel(0, subject, message, sender, receiver, dateSent, expDate, taxYear, documents);
		
		if(ParcelDB.insert(parcel)){
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * Initializes this class from config file once upon first creation.
	 * @throws ConfigException
	 */
	private static void init() throws ConfigException {
		if (!init) {
			expirationDays = Integer.parseInt(ConfigService.fetchFromConfig("PARCEL_EXPIRATION_DAYS:"));
			init = true;
		}
	}

}
