package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;

import domain.Document;
import exception.ConfigException;
import service.ConfigService;
import service.EncryptionService;

/**
 * A set of tests for EncryptionService.java
 * 
 * @author Jesse Goerzen
 */
class EncryptionServiceTest {
	
	/**
	 * Tests the getSalt() method from Encryption Service.
	 * 
	 * @throws NumberFormatException If a number from the config file is missing.
	 * @throws ConfigException If the config is missing.
	 */
	@Test
	void saltTest() throws NumberFormatException, ConfigException {
		for (int i = 0; i < 100000; i++) {
			String salt1 = EncryptionService.getSalt();
			String salt2 = EncryptionService.getSalt();

			assertTrue(!salt1.equals(salt2), salt1 + " = " + salt2);
		}
	}
	
	/**
	 * Tests the password hashing from the hashTest() method.
	 * 
	 * @throws InvalidKeySpecException If the key spec is invalid.
	 * @throws NoSuchAlgorithmException If the key algorithm is invalid.
	 * @throws NumberFormatException If a number from the config file is missing.
	 * @throws ConfigException If the config is missing.
	 */
	@Test
	void hashTest() throws InvalidKeySpecException, NoSuchAlgorithmException, NumberFormatException, ConfigException {
		String salt = EncryptionService.getSalt();
		String password1 = "password";

		assertFalse(password1.equals(EncryptionService.hash(password1, salt)));

		String password2 = "12345678";

		assertFalse(password2.equals(EncryptionService.hash(password2, salt)));
		assertFalse(EncryptionService.hash(password2, salt).equals(EncryptionService.hash(password1, salt)));

		String password3 = "password";

		assertFalse(password3.equals(EncryptionService.hash(password3, salt)));
		assertTrue(EncryptionService.hash(password3, salt).equals(EncryptionService.hash(password1, salt)));
		
		String salt2 = EncryptionService.getSalt();
		
		assertFalse(password1.equals(EncryptionService.hash(password1, salt2)));
		assertFalse(EncryptionService.hash(password1, salt2).equals(EncryptionService.hash(password1, salt)));
	}
	
	/**
	 * Tests the encryption of documents.
	 * 
	 * @throws InvalidKeySpecException If the key spec is invalid.
	 * @throws NoSuchAlgorithmException If the key algorithm is invalid.
	 * @throws NoSuchPaddingException If the padding type is invalid.
	 * @throws IOException If the file cannot be opened.
	 * @throws InvalidAlgorithmParameterException If the cipher algorithm is invalid.
	 * @throws ConfigException If the config is missing.
	 * @throws InvalidKeyException If the cipher's key is invalid.
	 */
	@Test
	void encryptDocTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IOException, InvalidAlgorithmParameterException, ConfigException {
		String unencryptedFilePath = "C:\\Capstone\\TestFiles\\test_pdf_large.pdf";

		Document encryptedFile = EncryptionService.encryptDocument(unencryptedFilePath);

		String decryptedFilePath = EncryptionService.decryptDocument(encryptedFile);

		System.out.println(decryptedFilePath);
	}
	
	/**
	 * Tests the encryption of strings.
	 * 
	 * @throws NumberFormatException If a number from the config file is missing.
	 * @throws InvalidKeyException If the cipher's key is invalid.
	 * @throws NoSuchAlgorithmException If the key algorithm is invalid.
	 * @throws NoSuchPaddingException If the padding type is invalid.
	 * @throws InvalidKeySpecException If the key spec is invalid.
	 * @throws IllegalBlockSizeException If the size of block is invalid.
	 * @throws BadPaddingException If the padding type is invalid.
	 * @throws ConfigException If the config is missing.
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException If the file cannot be opened.
	 */
	@Test
	void encryptStringTest()
			throws NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, ConfigException, InvalidAlgorithmParameterException, IOException {
		String unencryptedString = ConfigService.fetchContents(ConfigService.fetchFromConfig("teststringpath:"));

		String encryptedString = EncryptionService.encryptString(unencryptedString);
		
		System.out.println(encryptedString);

		assertFalse(unencryptedString.equals(encryptedString));

		String decryptedString = EncryptionService.decryptString(encryptedString);
		
		System.out.println(decryptedString);
		
		assertFalse(encryptedString.equals(decryptedString));
		
		assertTrue(unencryptedString.equals(decryptedString));
	}
}