package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import domain.Document;
import exception.ConfigException;

/**
 * Class Description: Class that contains methods that hash passwords,
 * encrypt/decrypt files, and encrypt/decrypt String inputs for transit and
 * security.
 *
 * @author Cesar Guzman, Jesse Goerzen, Taylor Hanlon, Tristen Kreutz
 */
public class EncryptionService {

	private static String cipherTransform;
	private static String keyPath;
	private static String saltPath;
	private static String keyAlgorithm;
	private static String cipherKeyAlgorithm;
	private static int iterationCount;
	private static int keyLength;
	private static String encryptedFilesDirectory;
	private static String outputFilesDirectory;

	/**
	 * Initializes encryption constants
	 * 
	 * @throws NumberFormatException
	 * @throws ConfigException
	 */
	private static void init() throws NumberFormatException, ConfigException {
		if (cipherTransform == null || keyPath == null || saltPath == null || keyAlgorithm == null
				|| cipherKeyAlgorithm == null || iterationCount == 0 || keyLength == 0
				|| encryptedFilesDirectory == null || outputFilesDirectory == null) {
			cipherTransform = ConfigService.fetchFromConfig("cipher:");
			keyPath = ConfigService.fetchFromConfig("keypath:");
			saltPath = ConfigService.fetchFromConfig("saltpath:");
			keyAlgorithm = ConfigService.fetchFromConfig("keyalgor:");
			cipherKeyAlgorithm = ConfigService.fetchFromConfig("cipherkeyalgor:");
			iterationCount = Integer.parseInt(ConfigService.fetchFromConfig("itercount:"));
			keyLength = Integer.parseInt(ConfigService.fetchFromConfig("keylen:"));
			encryptedFilesDirectory = ConfigService.fetchFromConfig("encfiles:");
			outputFilesDirectory = ConfigService.fetchFromConfig("outfiles:");
		}
	}

	/**
	 * Takes the String passed into the method and hashes it for security purposes.
	 * 
	 * @param toHash The string to hash
	 * @return The hashed string
	 * @throws NoSuchAlgorithmException If the algorithm in res/config.txt isn't
	 *                                  valid according to
	 *                                  https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidKeySpecException
	 * @throws ConfigException
	 * @throws NumberFormatException
	 */
	public static String hash(String toHash, String salt)
			throws InvalidKeySpecException, NoSuchAlgorithmException, NumberFormatException, ConfigException {
		init();
		byte[] hash = getKey(toHash, salt).getEncoded();

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
	 * @return String The filepath of the encrypted file
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws ConfigException
	 * @throws NumberFormatException
	 */
	public static Document encryptDocument(String filepath, boolean isSigned, boolean requiresSignature, int parcelID)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException,
			IOException, NumberFormatException, ConfigException {
		init();
		Cipher cipher = getCipher();

		SecretKey key = getKey(ConfigService.fetchContents(keyPath), ConfigService.fetchContents(saltPath));

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getEncoded(), cipherKeyAlgorithm));

		String outputPath = getEncryptedFilepath();

		File f = new File(outputPath);
		f.createNewFile();
		File fileIn = new File(filepath);

		try (FileInputStream in = new FileInputStream(filepath);
				FileOutputStream out = new FileOutputStream(outputPath);
				CipherOutputStream cipherOut = new CipherOutputStream(out, cipher)) {
			out.write(cipher.getIV());

			IOUtils.copy(in, cipherOut);
		}

		return new Document(outputPath, isSigned, requiresSignature, parcelID, fileIn.getName(), fileIn.length());
	}

	/**
	 * Takes the filepath of an encrypted file passed into the method and decrypts
	 * the file.
	 * 
	 * @param filepath filepath of the file to decrypt
	 * @return decrypted file path
	 * @throws FileNotFoundException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws ConfigException
	 * @throws InvalidKeySpecException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 */
	public static String decryptDocument(Document doc)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, ConfigException, InvalidKeyException, InvalidAlgorithmParameterException {
		init();

		String output = outputFilesDirectory + doc.getFileName();

		CipherInputStream cipherIn = null;
		FileOutputStream out = null;

		try (FileInputStream fileIn = new FileInputStream(doc.getFilePath());) {
			byte[] fileIv = new byte[16];
			fileIn.read(fileIv);

			Cipher cipher = getCipher();

			SecretKey key = getKey(ConfigService.fetchContents(keyPath), ConfigService.fetchContents(saltPath));

			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getEncoded(), cipherKeyAlgorithm),
					new IvParameterSpec(fileIv));

			cipherIn = new CipherInputStream(fileIn, cipher);

			out = new FileOutputStream(outputFilesDirectory + doc.getFileName());

			IOUtils.copy(cipherIn, out);

		} finally {
			cipherIn.close();
			out.close();
		}

		return output;
	}

	/**
	 * Takes the String passed into the method and encrypts the String.
	 * 
	 * @param string String to encrypt
	 * @return encrypted String
	 */
	public static String encryptString(String string) {
		return null;
	}

	/**
	 * Takes the String passed into the method and decrypts the String.
	 * 
	 * @param string String to decrypt
	 * @return decrypted String
	 */
	public static String decryptString(String string) {
		return null;
	}

	/**
	 * Returns a salt string
	 * 
	 * @return the salt
	 * @throws ConfigException
	 * @throws NumberFormatException
	 */
	public static String getSalt() throws NumberFormatException, ConfigException {
		init();

		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}

	/**
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
		return Cipher.getInstance(cipherTransform);
	}

	/**
	 * 
	 * 
	 * @param toHash
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws ConfigException
	 * @throws NumberFormatException
	 */
	private static SecretKey getKey(String toHash, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ConfigException {
		KeySpec spec = new PBEKeySpec(toHash.toCharArray(), salt.getBytes(), iterationCount, keyLength);

		SecretKeyFactory skf = SecretKeyFactory.getInstance(keyAlgorithm);

		return skf.generateSecret(spec);
	}

	/**
	 * Creates a valid filepath for encrypted files. The file name is random and
	 * unique and ends in the ".secure" extension
	 * 
	 * @return String A random and unique filepath in the encrypted files directory.
	 */
	private static String getEncryptedFilepath() {
		return encryptedFilesDirectory + UUID.randomUUID().toString() + ".secure";
	}
}