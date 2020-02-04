package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
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
	
	final String key = "fM,w9~A8u>(B[uqH4<Z33e>G33&\"wxJm<enFZp-iGl!'0K:C0MUH+yFEa[2*2i_";

	/**
	 * Takes the String passed into the method and hashes it for security purposes.
	 * 
	 * @param toHash String to hash
	 * @return hashed String
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public String hash(String toHash, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		/*
		 * MessageDigest md = MessageDigest.getInstance("SHA-256"); md.reset();
		 * 
		 * md.update(toHash.getBytes());
		 * 
		 * byte[] mdArray = md.digest(); StringBuilder sb = new
		 * StringBuilder(mdArray.length * 2);
		 * 
		 * for (byte b : mdArray) { int i = b & 0xff; if (i < 16) { sb.append('0'); }
		 * sb.append(Integer.toHexString(i)); } return sb.toString();
		 */

		KeySpec spec = new PBEKeySpec(toHash.toCharArray(), salt.getBytes(), 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBBKDF2WithHmacSHA1");

		byte[] hash = factory.generateSecret(spec).getEncoded();

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
	 */
	public Document encryptDocument(String filepath) {
		//TODO
		//get key TODO 
		File inFile = new File(filepath);
		File outFile = new File(filepath + "outputTEMP");
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,  secretKey);
			
			FileInputStream fis = new FileInputStream(inFile);
			byte[] inBytes = new byte[(int) inFile.length()];
			fis.read(inBytes);
			
			byte[] outBytes = cipher.doFinal(inBytes);
			FileOutputStream fos = new FileOutputStream(outFile);
					
		}
		catch(  )
		
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
	 * Returns the salt
	 * 
	 * @return the salt
	 */
	public static String getSalt() {
		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}
	
}