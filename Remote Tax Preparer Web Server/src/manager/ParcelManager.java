package manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;

import databaseAccess.DocumentDB;
import databaseAccess.ParcelDB;
import domain.Document;
import domain.Parcel;
import domain.User;
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
	
	private final static String TAX_PREPARER = "tax_preparer";

	/**
	 * @param parcelID
	 * @param senderEmail
	 * @param receiverEmail
	 * @param dateSent
	 * @param year
	 * @return
	 * @throws ConfigException
	 */
	public static ArrayList<Parcel> getParcels(String parcelID, String senderEmail, String receiverEmail, Date dateSent,
			int year) throws ConfigException {
		init();
		
		if(UserManager.getUser(receiverEmail).getPermissionLevel() > User.USER) {
			receiverEmail = TAX_PREPARER;
		}
		
		return ParcelDB.getParcelsByParameter(parcelID, senderEmail, receiverEmail, dateSent, year);
	}

	/**
	 * @param recieverEmail
	 * @param year
	 * @return
	 * @throws ConfigException
	 */
	public static ArrayList<Parcel> getByYear(String receiverEmail, int year) throws ConfigException {
		init();
		
		if(UserManager.getUser(receiverEmail).getPermissionLevel() > User.USER) {
			receiverEmail = TAX_PREPARER;
		}
		
		return getParcels(null, null, receiverEmail, null, year);
	}

	/**
	 * @param parcelID
	 * @return
	 * @throws ConfigException
	 */
	public static Parcel get(String parcelID) throws ConfigException {
		init();
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
	public static boolean isVisibleToUser(String email, String parcelID) throws ConfigException {
		init();
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
			String receiver, Date dateSent, Date expiryDate, int taxYear, boolean requiresSignature) throws NumberFormatException, ConfigException {
		init();
		
		boolean successfulInsert = true;
		
		//Set expiration date
		Calendar c = Calendar.getInstance();
		c.setTime(dateSent);
		c.add(Calendar.DAY_OF_MONTH, expirationDays);  
		Date expDate = c.getTime();
		
		if(UserManager.getUser(sender).getPermissionLevel() == User.USER) {
			receiver = null;
		}
		
		Parcel parcel = new Parcel(subject, message, sender, receiver, dateSent, expDate, taxYear, documents, requiresSignature);
		
		if(ParcelDB.insert(parcel)){
			for(Document document : documents) {
				if(!DocumentDB.insert(document, parcel.getParcelID())) {
					successfulInsert = false;
				}
			}
		} else {
			return false;
		}
		
		return successfulInsert;

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
