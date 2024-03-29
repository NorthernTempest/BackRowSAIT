package manager;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Part;

import databaseAccess.DocumentDB;
import databaseAccess.ParcelDB;
import domain.Document;
import domain.NewReturnForm;
import domain.Parcel;
import domain.User;
import exception.ConfigException;
import service.ConfigService;
import service.EncryptionService;
import util.cesar.Debugger;

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
	 * Specifies whether or not the class has been initialized with values from the config.
	 */
	private static boolean init;

	/**
	 * Days until parcels expire.
	 */
	private static int expirationDays;

	/**
	 * Tax Preparer constant.
	 */
	private final static String TAX_PREPARER = "tax_preparer";

	/**
	 * Subject contents for new return.
	 */
	private static String NEW_RETURN_SUBJECT;

	/**
	 * Message contents for new return.
	 */
	private static String NEW_RETURN_MESSAGE;

	/**
	 * Subject contents of a signed document.
	 */
	private static String SIGNED_DOC_SUBJECT;

	/**
	 * Message contents of a signed document.
	 */
	private static String SIGNED_DOC_MESSAGE;

	/**
	 * Initializes this class from config file once upon first creation.
	 * 
	 * @throws ConfigException
	 */
	private static void init() throws ConfigException {
		if (!init) {
			expirationDays = Integer.parseInt(ConfigService.fetchFromConfig("PARCEL_EXPIRATION_DAYS:"));
			NEW_RETURN_SUBJECT = ConfigService.fetchContents(ConfigService.fetchFromConfig("NEW_RETURN_SUBJECT:"));
			NEW_RETURN_MESSAGE = ConfigService.fetchContents(ConfigService.fetchFromConfig("NEW_RETURN_MESSAGE:"));
			SIGNED_DOC_SUBJECT = ConfigService.fetchContents(ConfigService.fetchFromConfig("SIGNED_DOC_SUBJECT:"));
			SIGNED_DOC_MESSAGE = ConfigService.fetchContents(ConfigService.fetchFromConfig("SIGNED_DOC_MESSAGE:"));
			init = true;
		}
	}

	/**
	 * Checks to see if the user is an employee and then calls the parcel broker to retrieve parcels
	 * based on passed information.
	 * @param parcelID
	 * @param senderEmail
	 * @param receiverEmail
	 * @param dateSent
	 * @param year
	 * @return ArrayList<Parcel> parcels
	 * @throws ConfigException
	 */
	public static ArrayList<Parcel> getParcels(String parcelID, String senderEmail, String receiverEmail, Date dateSent,
			int year) throws ConfigException {
		init();

		if (receiverEmail != null) {
			if (UserManager.getUser(receiverEmail).getPermissionLevel() > User.USER) {
				receiverEmail = TAX_PREPARER;
			}
		}

		return ParcelDB.getParcelsByParameter(parcelID, senderEmail, receiverEmail, dateSent, year);
	}

	/**
	 * Checks to see if user is an employee and then retrieves parcels based on the year passed.
	 * @param receiverEmail
	 * @param year
	 * @return ArrayList<Parcel> parcels
	 * @throws ConfigException
	 */
	public static ArrayList<Parcel> getByYear(String receiverEmail, int year) throws ConfigException {
		init();

		if (UserManager.getUser(receiverEmail).getPermissionLevel() > User.USER) {
			receiverEmail = TAX_PREPARER;
		}

		return getParcels(null, null, receiverEmail, null, year);
	}

	/**
	 * Retrieves a specific parcel.
	 * @param parcelID
	 * @return parcel
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
	 * @param subject       the subject
	 * @param message       the message
	 * @param sender        the email of the sender
	 * @param receiver      the email of the receiver
	 * @param dateSent      the date parcel was created
	 * @param expiryDate    the date parcel will expire
	 * @param taxYear       the tax year this parcel is associated with
	 * @return true if parcel created in database successfully
	 * @throws NumberFormatException
	 * @throws ConfigException
	 */
	public static boolean createParcel(ArrayList<Document> documents, String subject, String message, String sender,
			String receiver, Date dateSent, Date expiryDate, int taxYear, boolean requiresSignature)
			throws NumberFormatException, ConfigException {
		init();

		boolean successfulInsert = true;

		//Set expiration date
		Calendar c = Calendar.getInstance();
		c.setTime(dateSent);
		c.add(Calendar.DAY_OF_MONTH, expirationDays);
		Date expDate = c.getTime();

		User u = UserManager.getUser(sender);

		if (u == null)
			return false;

		if (u.getPermissionLevel() == User.USER) {
			receiver = null;
		}

		Parcel parcel = new Parcel(subject, message, sender, receiver, dateSent, expDate, taxYear, documents,
				requiresSignature);

		if (ParcelDB.insert(parcel)) {
			for (Document document : documents) {
				if (!DocumentDB.insert(document, parcel.getParcelID())) {
					successfulInsert = false;
				}
			}
		} else {
			return false;
		}

		return successfulInsert;
	}

	/**
	 * Creates a parcel in the database.
	 * @param parts
	 * @param subject
	 * @param message
	 * @param sender
	 * @param receiver
	 * @param dateSent
	 * @param expiryDate
	 * @param taxYear
	 * @param requiresSignature
	 * @return boolean was operation successful
	 */
	public static boolean createParcel(Collection<Part> parts, String subject, String message, String sender,
			String receiver, Date dateSent, Date expiryDate, int taxYear, boolean requiresSignature) {
		Debugger.log("createParcel(parts)");
		boolean successfulInsert = true;

		try {
			init();

			String fileName;
			ArrayList<Document> documents = new ArrayList<>();
			
			
			for (Part part : parts) {
				fileName = part.getSubmittedFileName();
				Debugger.log(fileName);
				if (fileName != null && !fileName.equals("null") && !fileName.equals("") ) {
					documents.add(EncryptionService.encryptDocument(part.getInputStream(), fileName));
				}
			}

			//Set expiration date
			Calendar c = Calendar.getInstance();
			c.setTime(dateSent);
			c.add(Calendar.DAY_OF_MONTH, expirationDays);
			Date expDate = c.getTime();

			User u = UserManager.getUser(sender);

			if (u == null)
				return false;

			if (u.getPermissionLevel() == User.USER) {
				receiver = null;
			}

			Parcel parcel = new Parcel(subject, message, sender, receiver, dateSent, expDate, taxYear, documents,
					requiresSignature);

			if (ParcelDB.insert(parcel)) {
				for (Document document : documents) {
					if (!DocumentDB.insert(document, parcel.getParcelID())) {
						successfulInsert = false;
					}
				}
			} else {
				return false;
			}

		} catch (ConfigException e) {
			e.printStackTrace();
			return false;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return successfulInsert;
	}

	/**
	 * Creates a new return and associated parcels.
	 * @param documents
	 * @param fName
	 * @param lName
	 * @param email
	 * @param taxYear
	 * @return boolean was operation successful
	 * @throws ConfigException
	 */
	public static boolean createNewReturnParcels(ArrayList<Document> documents, NewReturnForm nrf, String email)
			throws ConfigException {
		init();

		boolean successfulInsert = true;

		//Set expiration date
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, expirationDays);
		Date expDate = c.getTime();

		User u = UserManager.getUser(email);

		if (u == null) {
			return false;
		}

		//create and insert parcel to user
		Parcel userParcel = new Parcel(NEW_RETURN_SUBJECT + nrf.getTaxYear(),
				nrf.getfName() + " " + nrf.getlName() + NEW_RETURN_MESSAGE, "preparer@test.com", email, new Date(),
				expDate, Integer.parseInt(nrf.getTaxYear()), documents, true);

		if (ParcelDB.insert(userParcel)) {
			for (Document document : documents) {
				if (!DocumentDB.insert(document, userParcel.getParcelID())) {
					Debugger.log("user parcel went bad");
					successfulInsert = false;
				}
			}
		} else {
			return false;
		}

		//create and insert parcel to tax preparers
		Parcel taxPrepParcel = new Parcel("Tax Information for " + nrf.getfName() + " " + nrf.getlName(),
				"this is a message with nicely formatted tax info", email /*sender*/, /*receiver*/ null, /*datesent*/ new Date(),
				/*expDate*/ expDate, /*taxYear*/ Integer.parseInt(nrf.getTaxYear()), null, false);

		if (!ParcelDB.insert(taxPrepParcel)) {
			Debugger.log("tax prep parcel went bad");
			successfulInsert = false;
		}

		return successfulInsert;
	}

	/**
	 * Creates a signed document/parcel in the database.
	 * @param signedPDF
	 * @param sender
	 * @param taxYear
	 * @return boolean was operation successful
	 */
	public static boolean createSignedDocParcel(Document signedPDF, String sender, int taxYear) {
		ArrayList<Document> documents = new ArrayList<Document>();
		documents.add(signedPDF);

		boolean successfulInsert = true;

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, expirationDays);
		Date expDate = c.getTime();

		Parcel parcel = new Parcel(SIGNED_DOC_SUBJECT + sender, sender + SIGNED_DOC_MESSAGE + taxYear, sender,
				null, new Date(), expDate, taxYear, documents, false);

		if (ParcelDB.insert(parcel)) {
			for (Document document : documents) {
				if (!DocumentDB.insert(document, parcel.getParcelID())) {
					successfulInsert = false;
				}
			}
		} else {
			return false;
		}

		return successfulInsert;
	}

	/**
	 * Deletes the parcel and attached documents from the database.
	 * @param parcelID parcelID to delete
	 * @return boolean was operation succesful
	 */
	public static boolean delete(String parcelID) {
		DocumentManager.delete(parcelID);
		ParcelDB.delete(parcelID);
		return true;
	}
}
