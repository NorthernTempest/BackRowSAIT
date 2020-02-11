package manager;

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

/**
 * Class Description: Class that contains methods that hash passwords,
 * encrypt/decrypt files, and encrypt/decrypt String inputs for transit and
 * security.
 *
 * @author Cesar Guzman, Jesse Goerzen, Taylor Hanlon, Tristen Kreutz
 */
public class EncryptionService {

	private static final String CIPHER_TRANSFORM = ConfigService.fetchFromConfig("cipher:");
	private static final String KEY_PATH = ConfigService.fetchFromConfig("keypath:");
	private static final String SALT_PATH = ConfigService.fetchFromConfig("saltpath:");
	private static final String KEY_ALGORITHM = ConfigService.fetchFromConfig("keyalgor:");
	private static final String CIPHER_KEY_ALGORITHM = ConfigService.fetchFromConfig("cipherkeyalgor:");
	private static final int ITERATION_COUNT = Integer.parseInt(ConfigService.fetchFromConfig("itercount:"));
	private static final int KEY_LENGTH = Integer.parseInt(ConfigService.fetchFromConfig("keylen:"));
	private static final String ENCRYPTED_FILES_DIR = ConfigService.fetchFromConfig("encfiles:");
	private static final String OUTPUT_FILES_DIR = ConfigService.fetchFromConfig("outfiles:");

	/**
	 * Takes the String passed into the method and hashes it for security purposes.
	 * 
	 * @param toHash The string to hash
	 * @return The hashed string
	 * @throws NoSuchAlgorithmException If the algorithm in res/config.txt isn't
	 *                                  valid according to
	 *                                  https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidKeySpecException
	 * @throws FileNotFoundException    If res/config.txt isn't found
	 */
	public static String hash(String toHash, String salt)
			throws InvalidKeySpecException, NoSuchAlgorithmException, FileNotFoundException {

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
	 */
	public static Document encryptDocument(String filepath, boolean isSigned, boolean requiresSignature, int parcelID) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, InvalidKeyException, IOException {
		Cipher cipher = getCipher();

		SecretKey key = getKey(ConfigService.fetchContents(KEY_PATH),
				ConfigService.fetchContents(SALT_PATH));

		cipher.init(Cipher.ENCRYPT_MODE,
				new SecretKeySpec(key.getEncoded(), CIPHER_KEY_ALGORITHM));

		String outputPath = getEncryptedFilepath();

		File f = new File(outputPath);
		f.createNewFile();
		File fileIn = new File( filepath );

		try (FileInputStream in = new FileInputStream(filepath);
				FileOutputStream out = new FileOutputStream(outputPath);
				CipherOutputStream cipherOut = new CipherOutputStream(out, cipher)) {
			out.write(cipher.getIV());
			
			IOUtils.copy(in, cipherOut);
		}

		return new Document( outputPath, isSigned, requiresSignature, parcelID, fileIn.getName(), fileIn.length() );
	}

	/**
	 * Takes the filepath of an encrypted file passed into the method and decrypts
	 * the file.
	 * 
	 * @param filepath filepath of the file to decrypt
	 * @return decrypted file path
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 */
	public static String decryptDocument(Document doc)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {

		String output = OUTPUT_FILES_DIR + doc.getFileName();

		CipherInputStream cipherIn = null;
		FileOutputStream out = null;

		try (FileInputStream fileIn = new FileInputStream(doc.getFilePath());) {
			byte[] fileIv = new byte[16];
			fileIn.read(fileIv);

			Cipher cipher = getCipher();

			SecretKey key = getKey(ConfigService.fetchContents(KEY_PATH),
					ConfigService.fetchContents(SALT_PATH));

			cipher.init(Cipher.DECRYPT_MODE,
					new SecretKeySpec(key.getEncoded(), CIPHER_KEY_ALGORITHM),
					new IvParameterSpec(fileIv));

			cipherIn = new CipherInputStream(fileIn, cipher);

			out = new FileOutputStream(OUTPUT_FILES_DIR + doc.getFileName());

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
	 */
	public static String getSalt() {
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
	 * @throws FileNotFoundException
	 */
	private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, FileNotFoundException {
		return Cipher.getInstance(CIPHER_TRANSFORM);
	}

	/**
	 * 
	 * @param toHash
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws FileNotFoundException
	 * @throws NumberFormatException
	 */
	private static SecretKey getKey(String toHash, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NumberFormatException, FileNotFoundException {
		KeySpec spec = new PBEKeySpec(toHash.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);

		SecretKeyFactory skf = SecretKeyFactory.getInstance(ConfigService.fetchFromConfig(KEY_ALGORITHM));

		return skf.generateSecret(spec);
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String getEncryptedFilepath() throws FileNotFoundException {
		return ENCRYPTED_FILES_DIR + UUID.randomUUID().toString() + ".secure";
	}
}