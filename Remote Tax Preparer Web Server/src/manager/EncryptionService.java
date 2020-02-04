package manager;

import domain.Document;

/**
 * Class Description: 	Class that contains methods that hash passwords, encrypt/decrypt files, and
 * 						encrypt/decrypt String inputs for transit and security.
 *
 * @author Tristen Kreutz
 *
 */
public class EncryptionService {

	/**
	 * Takes the String passed into the method and hashes it for security purposes.
	 * @param toHash String to hash
	 * @return hashed String
	 */
	public String hash(String toHash) {
		return null;
	}
	
	/**
	 * Takes the filepath passed into the method and encrypts the file.
	 * @param filepath filepath of the file to encrypt
	 * @return encrypted file
	 */
	public Document encryptDocument(String filepath) {
		return null;
	}
	
	/**
	 * Takes the filepath of an encrypted file passed into the method and decrypts the file.
	 * @param filepath filepath of the file to decrypt
	 * @return decrypted file
	 */
	public Document decryptDocument(String filepath) {
		return null;
	}
	
	/**
	 * Takes the String passed into the method and encrypts the String.
	 * @param string String to encrypt
	 * @return encrypted String
	 */
	public String encryptString(String string) {
		return null;
	}
	
	/**
	 * Takes the String passed into the method and decrypts the String.
	 * @param string String to decrypt
	 * @return decrypted String
	 */
	public String decryptString(String string) {
		return null;
	}
}
