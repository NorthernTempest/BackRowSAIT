package parcel;

/**
 * 
 * Class Description: Class that encrypts or decrypts documents attached to a parcel object.
 *
 * @author Tristen Kreutz
 *
 */
public abstract class Document {

	/**
	 * The information for linking data communication.
	 */
	private String dataLink;
	
	/**
	 * Boolean for whether a signature has been provided or not.
	 */
	private Boolean signatureBool;
	
	/**
	 * Method to encrypt the contents within a file.
	 */
	public abstract void encryptFile();
	
	/**
	 * Method to decrypt the contents within a file.
	 */
	public abstract void decryptFile();
	
	/**
	 * Method to encrypt a data link for safe transit.
	 */
	public abstract void encryptLink();
	
	/**
	 * Method to decrypt a data link when received.
	 */
	public abstract void decryptLink();
}

