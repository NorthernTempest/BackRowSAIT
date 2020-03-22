package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
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

	/**
	 * The type of transform for Cipher objects to use.
	 */
	private static String cipherTransform;
	/**
	 * The path to the file containing the key for file and string encryption and
	 * decryption.
	 */
	private static String keyPath;
	/**
	 * The path to the file containing the salt for file and string encryption and
	 * decryption.
	 */
	private static String saltPath;
	/**
	 * The algorithm to generate SecretKey objects.
	 */
	private static String keyAlgorithm;
	/**
	 * The algorithm to generate SecretKey objects for use with Cipher objects.
	 */
	private static String cipherKeyAlgorithm;
	/**
	 * The number of iterations of the algorithm to perform.
	 */
	private static int iterationCount;
	/**
	 * The number of bytes in a key.
	 */
	private static int keyLength;
	/**
	 * The directory in which to store encrypted files.
	 */
	private static String encryptedFilesDirectory;
	/**
	 * The directory in which to store decrypted files.
	 */
	private static String outputFilesDirectory;
	/**
	 * Whether the encryption constants have been initialized from the config or
	 * not.
	 */
	private static boolean init;

	/**
	 * Initializes encryption constants from the config. Should be added to the
	 * beginning of each public method.
	 * 
	 * @throws NumberFormatException if iterationCount or keyLength are not integers
	 *                               in the config file.
	 * @throws ConfigException       if the config file is missing from the res
	 *                               folder.
	 */
	private static void init() throws NumberFormatException, ConfigException {
		if (!init) {
			cipherTransform = ConfigService.fetchFromConfig("cipher:");
			keyPath = ConfigService.fetchFromConfig("keypath:");
			saltPath = ConfigService.fetchFromConfig("saltpath:");
			keyAlgorithm = ConfigService.fetchFromConfig("keyalgor:");
			cipherKeyAlgorithm = ConfigService.fetchFromConfig("cipherkeyalgor:");
			iterationCount = Integer.parseInt(ConfigService.fetchFromConfig("itercount:"));
			keyLength = Integer.parseInt(ConfigService.fetchFromConfig("keylen:"));
			encryptedFilesDirectory = ConfigService.fetchFromConfig("encfiles:");
			outputFilesDirectory = ConfigService.fetchFromConfig("outfiles:");
			init = true;
		}
	}

	/**
	 * Takes the String passed into the method and hashes it for security purposes.
	 * 
	 * @param toHash The password or other String to create a hash for.
	 * @param salt   The salt to be used in the hashing.
	 * @return The String of characters that is derived from the toHash and the
	 *         salt.
	 * @throws NumberFormatException    if iterationCount or keyLength are not
	 *                                  integers in the config file.
	 * @throws ConfigException          if the config file in the res directory
	 *                                  doesn't exist.
	 * @throws NoSuchAlgorithmException if the algorithm in the config file isn't
	 *                                  valid according to
	 *                                  https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidKeySpecException  if the key specification created from the
	 *                                  toHash and salt is invalid.
	 */
	public static String hash(String toHash, String salt)
			throws NumberFormatException, ConfigException, NoSuchAlgorithmException, InvalidKeySpecException {
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
	 * Encrypts the file at the given filepath and returns a record of the document.
	 * 
	 * @param filepath String The filepath of the file to encrypt.
	 * @return Document The record of the encrypted file.
	 * @throws ConfigException          if the config file in the res folder does
	 *                                  not exist.
	 * @throws NumberFormatException    if iterationCount or keyLength are not
	 *                                  integers in the config file.
	 * @throws NoSuchPaddingException   if the padding of the cipher transform given
	 *                                  in the config file is not valid according to
	 *                                  https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @throws NoSuchAlgorithmException if the algorithm of the cipher transform is
	 *                                  invalid according to
	 *                                  https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 *                                  or if the key is invalid according to
	 *                                  https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidKeySpecException  if the key specification created from the
	 *                                  key and the salt from the file are invalid.
	 * @throws InvalidKeyException      if the key used in the cipher is invalid.
	 * @throws IOException              if the file at the given filepath does not
	 *                                  exist or a file at the output directory
	 *                                  cannot be created.
	 */
	public static Document encryptDocument(String filepath)
			throws NumberFormatException, ConfigException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, InvalidKeyException, IOException {
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

		return new Document(outputPath, fileIn.getName(), fileIn.length());
	}
	
	/**
	 * Encrypts the incoming stream as an encrypted document with the given name and returns a record of the document.
	 * 
	 * @param in InputStream The input stream of the file to encrypt.
	 * @param fileName String The name of the file to encrypt.
	 * @return Document The record of the encrypted file.
	 * @throws ConfigException          if the config file in the res folder does
	 *                                  not exist.
	 * @throws NumberFormatException    if iterationCount or keyLength are not
	 *                                  integers in the config file.
	 * @throws NoSuchPaddingException   if the padding of the cipher transform given
	 *                                  in the config file is not valid according to
	 *                                  https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @throws NoSuchAlgorithmException if the algorithm of the cipher transform is
	 *                                  invalid according to
	 *                                  https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 *                                  or if the key is invalid according to
	 *                                  https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidKeySpecException  if the key specification created from the
	 *                                  key and the salt from the file are invalid.
	 * @throws InvalidKeyException      if the key used in the cipher is invalid.
	 * @throws IOException              if the file at the given filepath does not
	 *                                  exist or a file at the output directory
	 *                                  cannot be created.
	 */
	public static Document encryptDocument(InputStream in, String fileName) throws NumberFormatException, ConfigException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IOException {
		init();
		Cipher cipher = getCipher();
		
		SecretKey key = getKey(ConfigService.fetchContents(keyPath), ConfigService.fetchContents(saltPath));
		
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getEncoded(), cipherKeyAlgorithm));
		
		String outputPath = getEncryptedFilepath();
		
		File f = new File(outputPath);
		f.createNewFile();
		
		try (FileOutputStream out = new FileOutputStream(outputPath);
				CipherOutputStream cipherOut = new CipherOutputStream(out, cipher)) {
			out.write(cipher.getIV());
			
			IOUtils.copy(in, cipherOut);
		}
		
		return new Document(outputPath, fileName, f.length() - 16);
	}

	/**
	 * Decrypts the given document and returns the filepath of the resulting file.
	 * 
	 * @param doc Document The record of the encrypted file.
	 * @return String The filepath of the decrypted file.
	 * @throws NumberFormatException              if iterationCount or keyLength are
	 *                                            not integers in the config file.
	 * @throws FileNotFoundException              if the given document is not
	 *                                            present.
	 * @throws NoSuchPaddingException             if the padding of the cipher
	 *                                            transform given in the config file
	 *                                            is not valid according to
	 *                                            https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @throws NoSuchAlgorithmException           if the algorithm of the cipher
	 *                                            transform is invalid according to
	 *                                            https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 *                                            or if the key is invalid according
	 *                                            to
	 *                                            https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws ConfigException                    if the config file in the res
	 *                                            directory doesn't exist.
	 * @throws IOException                        if the file at the given filepath
	 *                                            does not exist or a file at the
	 *                                            output directory cannot be
	 *                                            created.
	 * @throws InvalidKeySpecException            if the key specification created
	 *                                            from the key and the salt from the
	 *                                            file are invalid.
	 * @throws InvalidAlgorithmParameterException if the cipher IV at the beginning
	 *                                            of the document is invalid.
	 * @throws InvalidKeyException                if the key used in the cipher is
	 *                                            invalid.
	 */
	public static String decryptDocument(Document doc)
			throws NumberFormatException, ConfigException, FileNotFoundException, IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
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
	 * Decrypts the given document and sends the resulting file over the given stream.
	 * 
	 * @param doc Document The record of the encrypted file.
	 * @param out OutputStream The stream of data to send the decrypted file over.
	 * @throws NumberFormatException              if iterationCount or keyLength are
	 *                                            not integers in the config file.
	 * @throws FileNotFoundException              if the given document is not
	 *                                            present.
	 * @throws NoSuchPaddingException             if the padding of the cipher
	 *                                            transform given in the config file
	 *                                            is not valid according to
	 *                                            https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @throws NoSuchAlgorithmException           if the algorithm of the cipher
	 *                                            transform is invalid according to
	 *                                            https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 *                                            or if the key is invalid according
	 *                                            to
	 *                                            https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws ConfigException                    if the config file in the res
	 *                                            directory doesn't exist.
	 * @throws IOException                        if the file at the given filepath
	 *                                            does not exist or a file at the
	 *                                            output directory cannot be
	 *                                            created.
	 * @throws InvalidKeySpecException            if the key specification created
	 *                                            from the key and the salt from the
	 *                                            file are invalid.
	 * @throws InvalidAlgorithmParameterException if the cipher IV at the beginning
	 *                                            of the document is invalid.
	 * @throws InvalidKeyException                if the key used in the cipher is
	 *                                            invalid.
	 */
	public static void decryptDocument(Document doc, OutputStream out)
			throws NumberFormatException, ConfigException, FileNotFoundException, IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
		init();
		
		CipherInputStream cipherIn = null;
		
		try (FileInputStream fileIn = new FileInputStream(doc.getFilePath())) {
			byte[] fileIv = new byte[16];
			fileIn.read(fileIv);

			Cipher cipher = getCipher();

			SecretKey key = getKey(ConfigService.fetchContents(keyPath), ConfigService.fetchContents(saltPath));

			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getEncoded(), cipherKeyAlgorithm),
					new IvParameterSpec(fileIv));

			cipherIn = new CipherInputStream(fileIn, cipher);
			
			IOUtils.copy(cipherIn, out);
		} finally {
			cipherIn.close();
		}
	}

	/**
	 * Encrypts the given String object into an array of bytes. The resulting bytes
	 * cannot be safely saved as a String.
	 * 
	 * @param unencrypted <code>String</code> The unencrypted String object.
	 * @return String The encrypted array of bytes in hexadecimal format.
	 * @throws ConfigException           if the config file in the res directory
	 *                                   doesn't exist.
	 * @throws NumberFormatException     if iterationCount or keyLength are not
	 *                                   integers in the config file.
	 * @throws NoSuchPaddingException    if the padding of the cipher transform
	 *                                   given in the config file is not valid
	 *                                   according to
	 *                                   https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @throws NoSuchAlgorithmException  if the algorithm of the cipher transform is
	 *                                   invalid according to
	 *                                   https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 *                                   or if the key is invalid according to
	 *                                   https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidKeySpecException   if the key specification created from the
	 *                                   key and the salt from the file are invalid.
	 * @throws InvalidKeyException       if the key used in the cipher is invalid.
	 * @throws BadPaddingException       if the padding option in the config file is
	 *                                   invalid.
	 * @throws IllegalBlockSizeException if the block size generated by the Cipher
	 *                                   object instance creator is invalid.
	 */
	public static String encryptString(String unencrypted)
			throws NumberFormatException, ConfigException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		init();

		Cipher cipher = getCipher();

		SecretKey key = getKey(ConfigService.fetchContents(keyPath), ConfigService.fetchContents(saltPath));

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getEncoded(), cipherKeyAlgorithm));

		byte[] bytes = cipher.doFinal(unencrypted.getBytes());

		byte[] encrypted = new byte[16 + bytes.length];

		byte[] iv = cipher.getIV();

		for (int i = 0; i < 16; i++)
			encrypted[i] = iv[i];
		for (int i = 16; i < bytes.length + 16; i++)
			encrypted[i] = bytes[i - 16];

		StringBuffer output = new StringBuffer();

		for (Byte b : encrypted) {
			output.append(String.format("%02X", b));
		}

		return output.toString();
	}

	/**
	 * Takes the String passed into the method and decrypts the String.
	 * 
	 * @param encrypted String The string of bytes in hexadecimal to be decrypted.
	 *                  This string must have been produced by the
	 *                  <code>encrpytString()</code> method.
	 * @return decrypted String The decrypted String object.
	 * @throws ConfigException                    if the config file in the res
	 *                                            directory doesn't exist.
	 * @throws NumberFormatException              if iterationCount or keyLength are
	 *                                            not integers in the config file.
	 * @throws NoSuchPaddingException             if the padding of the cipher
	 *                                            transform given in the config file
	 *                                            is not valid according to
	 *                                            https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @throws NoSuchAlgorithmException           if the algorithm of the cipher
	 *                                            transform is invalid according to
	 *                                            https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 *                                            or if the key is invalid according
	 *                                            to
	 *                                            https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidAlgorithmParameterException if the initialization vector
	 *                                            stored at the beginning of the
	 *                                            byte array is invalid.
	 * @throws InvalidKeySpecException            if the key specification created
	 *                                            from the key and the salt from the
	 *                                            file are invalid.
	 * @throws InvalidKeyException                if the key used in the cipher is
	 *                                            invalid.
	 * @throws BadPaddingException                if the padding option in the
	 *                                            config file is invalid.
	 * @throws IllegalBlockSizeException          if the block size generated by the
	 *                                            Cipher object instance creator is
	 *                                            invalid.
	 */
	public static String decryptString(String encrypted) throws NumberFormatException, ConfigException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		init();

		byte[] encoded = new byte[encrypted.length() / 2];

		for (int i = 0; i < encrypted.length(); i += 2) {
			encoded[i / 2] = (byte) (Integer.parseInt(encrypted.substring(i, i + 2), 16));
		}

		byte[] iv = new byte[16];

		for (int i = 0; i < 16; i++)
			iv[i] = encoded[i];

		Cipher cipher = getCipher();

		SecretKey key = getKey(ConfigService.fetchContents(keyPath), ConfigService.fetchContents(saltPath));

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getEncoded(), cipherKeyAlgorithm),
				new IvParameterSpec(iv));

		StringBuffer sb = new StringBuffer();

		byte[] bytes = new byte[encoded.length - 16];

		for (int i = 16; i < encoded.length; i++)
			bytes[i - 16] = encoded[i];

		sb.append(new String(cipher.doFinal(bytes), Charset.defaultCharset()));

		return sb.toString();
	}

	/**
	 * Returns a random String object that is 32 characters long.
	 * 
	 * @return a random String object that is 32 characters long.
	 * @throws ConfigException       if the config file in the res directory doesn't
	 *                               exist.
	 * @throws NumberFormatException if iterationCount or keyLength are not integers
	 *                               in the config file.
	 */
	public static String getSalt() throws NumberFormatException, ConfigException {
		init();

		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}

	/**
	 * Creates an instance of a Cipher object that follows the rules set out in the
	 * config.
	 * 
	 * @return <code>Cipher</code> a Cipher object that follows the rules set out in
	 *         the config.
	 * @throws NoSuchAlgorithmException if the algorithm of the cipher transform is
	 *                                  invalid according to
	 *                                  https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @throws NoSuchPaddingException   if the padding of the cipher transform given
	 *                                  in the config file is not valid according to
	 *                                  https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
	 */
	private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
		return Cipher.getInstance(cipherTransform);
	}

	/**
	 * Creates an instance of a SecretKey object according to rules in the config
	 * file using the toHash and salt. The SecretKey object can be used to create a
	 * hashed password or a Cipher object.
	 * 
	 * @param toHash <code>String</code> The unhashed key or password to be used to
	 *               encrypt or decrypt data.
	 * @param salt   <code>String</code> The salt to be added to the key or
	 *               password.
	 * @return <code>SecretKey</code> The desired SecretKey object.
	 * @throws NoSuchAlgorithmException if the algorithm of the key is invalid
	 *                                  according to
	 *                                  https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
	 * @throws InvalidKeySpecException  if the key specification created from the
	 *                                  key and the salt are invalid.
	 * @throws ConfigException          if the config file in the res directory
	 *                                  doesn't exist.
	 * @throws NumberFormatException    if iterationCount or keyLength are not
	 *                                  integers in the config file.
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