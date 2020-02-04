package manager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import domain.Document;

/**
 * Class Description: Class that contains methods that hash passwords,
 * encrypt/decrypt files, and encrypt/decrypt String inputs for transit and
 * security.
 *
 * @author Cesar Guzman, Jesse Goerzen, Taylor Hanlon, Tristen Kreutz
 *
 */
public class EncryptionService {
	
	private static final String CONFIG_FILE_PATH = "res/config.txt";
	private static final String CONFIG_CIPHER = "cipher:";
	private static final String CONFIG_KEY_PATH = "keypath:";
	private static final String CONFIG_SALT_PATH = "saltpath:";
	private static final String KEY_ALGORITHM = "PBBKDF2WithHmacSHA1";
	private static final String TEXT_FORMAT = "UTF8";
	private static final int ITERATION_COUNT = 65536;
	private static final int KEY_LENGTH = 128;
	
	/**
	 * Takes the String passed into the method and hashes it for security purposes.
	 * 
	 * @param toHash String to hash
	 * @return hashed String
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public String hash(String toHash, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {

		byte[] hash = getKey( toHash, salt ).getEncoded();

		StringBuilder sb = new StringBuilder(hash.length * 2);

		for (byte b : hash) {
			int i = b & 0xff;
			if (i < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(i));
		}

		return sb.toString();
	}

	/**
	 * Takes the filepath passed into the method and encrypts the file.
	 * 
	 * @param filepath filepath of the file to encrypt
	 * @return encrypted file
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 */
	public Document encryptDocument(String filepath) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IOException {
		
		Cipher cipher = getCipher();
		SecretKey key = getKey( fetchFromConfig( CONFIG_KEY_PATH ), fetchFromConfig( CONFIG_SALT_PATH ) );
		
		String outputPath = null; //TODO
		
		try( FileInputStream in =  new FileInputStream( filepath ); FileOutputStream out = new FileOutputStream( outputPath ); CipherOutputStream cipherOut = new CipherOutputStream( out, cipher ) ) {
			
		}
		
		return null;
	}

	/**
	 * Takes the filepath of an encrypted file passed into the method and decrypts
	 * the file.
	 * 
	 * @param filepath filepath of the file to decrypt
	 * @return decrypted file
	 */
	public Document decryptDocument(String filepath) {
		return null;
	}

	/**
	 * Takes the String passed into the method and encrypts the String.
	 * 
	 * @param string String to encrypt
	 * @return encrypted String
	 */
	public String encryptString(String string) {
		return null;
	}

	/**
	 * Takes the String passed into the method and decrypts the String.
	 * 
	 * @param string String to decrypt
	 * @return decrypted String
	 */
	public String decryptString(String string) {
		return null;
	}

	/**
	 * Returns a salt string
	 * 
	 * @return the salt
	 */
	public static String getSalt() {
		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}
	
	private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		return Cipher.getInstance( fetchFromConfig( CONFIG_CIPHER ) );
	}
	
	private static SecretKey getKey( String toHash, String salt ) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		KeySpec spec = new PBEKeySpec( toHash.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH );
		SecretKeyFactory skf = SecretKeyFactory.getInstance( KEY_ALGORITHM );
		
		return skf.generateSecret( spec );
	}
	
	private static String fetchFromConfig( String option )
	{
		Scanner s = new Scanner( CONFIG_FILE_PATH );
		String line = s.nextLine();
		
		while( line != null && !line.equals( "" ) && !line.startsWith( option ) )
		{
			line = s.nextLine();
		}
		
		return line.substring( option.length() );
	}
}