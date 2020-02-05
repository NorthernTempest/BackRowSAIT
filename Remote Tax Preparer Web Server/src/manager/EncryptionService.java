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

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

import domain.Document;

/**
 * Class Description: Class that contains methods that hash passwords,
 * encrypt/decrypt files, and encrypt/decrypt String inputs for transit and
 * security.
 *
 * @author Cesar Guzman, Jesse Goerzen, Taylor Hanlon, Tristen Kreutz
 */
public class EncryptionService {

	private static final String CONFIG_CIPHER = "cipher:";
	private static final String CONFIG_KEY_PATH = "keypath:";
	private static final String CONFIG_SALT_PATH = "saltpath:";
	private static final String CONFIG_KEY_ALGORITHM = "keyalgor:";
	private static final String CONFIG_ITERATION_COUNT = "itercount:";
	private static final String CONFIG_KEY_LENGTH = "keylen:";
	private static final String CONFIG_ENCRYPTED_FILES_DIR = "encfiles:";
	private static final String CONFIG_OUTPUT_FILES_DIR = "outfiles:";

	/**
	 * Takes the String passed into the method and hashes it for security purposes.
	 * 
	 * @param toHash String to hash
	 * @return hashed String
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String hash(String toHash, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {

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
	public static String encryptDocument(String filepath) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, InvalidKeyException, IOException {

		Cipher cipher = getCipher();

		cipher.init(Cipher.ENCRYPT_MODE, getKey(ConfigService.fetchContents(ConfigService.fetchFromConfig(CONFIG_KEY_PATH)),
				ConfigService.fetchContents(ConfigService.fetchFromConfig(CONFIG_SALT_PATH))));

		String outputPath = getEncryptedFilepath();

		File f = new File(outputPath);
		f.createNewFile();

		try (FileInputStream in = new FileInputStream(filepath);
				FileOutputStream out = new FileOutputStream(outputPath);
				CipherOutputStream cipherOut = new CipherOutputStream(out, cipher)) {
			out.write(cipher.getIV());

			byte[] content = new byte[(int) f.length()];
			in.read(content);

			cipherOut.write(content);
		}

		return outputPath;
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
	public static String decryptDocument(Document doc) throws FileNotFoundException, IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {

		String output = ConfigService.fetchFromConfig(CONFIG_OUTPUT_FILES_DIR) + doc.getFileName();

		CipherInputStream cipherIn = null;
		FileOutputStream out = null;

		try (FileInputStream fileIn = new FileInputStream(doc.getFilePath());) {
			byte[] fileIv = new byte[16];
			fileIn.read(fileIv);

			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, getKey(ConfigService.fetchContents(ConfigService.fetchFromConfig(CONFIG_KEY_PATH)),
					ConfigService.fetchContents(ConfigService.fetchFromConfig(CONFIG_SALT_PATH))), new IvParameterSpec(fileIv));

			cipherIn = new CipherInputStream(fileIn, cipher);

			out = new FileOutputStream(ConfigService.fetchFromConfig(CONFIG_OUTPUT_FILES_DIR) + doc.getFileName());

			File f = new File(doc.getFilePath());

			byte[] content = new byte[(int) f.length()];
			cipherIn.read(content);

			out.write(content);

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
	 */
	private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
		return Cipher.getInstance(ConfigService.fetchFromConfig(CONFIG_CIPHER));
	}

	/**
	 * 
	 * @param toHash
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static SecretKey getKey(String toHash, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(toHash.toCharArray(), salt.getBytes(),
				Integer.parseInt(ConfigService.fetchFromConfig(CONFIG_ITERATION_COUNT)),
				Integer.parseInt(ConfigService.fetchFromConfig(CONFIG_KEY_LENGTH)));
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ConfigService.fetchFromConfig(CONFIG_KEY_ALGORITHM));

		return skf.generateSecret(spec);
	}

	/**
	 * 
	 * @return
	 */
	private static String getEncryptedFilepath() {
		return ConfigService.fetchFromConfig(CONFIG_ENCRYPTED_FILES_DIR) + getSalt() + ".secure";
	}
}