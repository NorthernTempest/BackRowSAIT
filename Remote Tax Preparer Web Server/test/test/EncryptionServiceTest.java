package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.Charset;
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

class EncryptionServiceTest {

	@Test
	void saltTest() throws NumberFormatException, ConfigException {
		for (int i = 0; i < 100000; i++) {
			String salt1 = EncryptionService.getSalt();
			String salt2 = EncryptionService.getSalt();

			assertTrue(!salt1.equals(salt2), salt1 + " = " + salt2);
		}
	}

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
	}

	@Test
	void encryptDocTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IOException, InvalidAlgorithmParameterException, ConfigException {
		String unencryptedFilePath = "C:\\Capstone\\TestFiles\\test_pdf_large.pdf";

		Document encryptedFile = EncryptionService.encryptDocument(unencryptedFilePath, false, false, 1);

		String decryptedFilePath = EncryptionService.decryptDocument(encryptedFile);

		System.out.println(decryptedFilePath);
	}

	@Test
	void encryptStringTest()
			throws NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, ConfigException, InvalidAlgorithmParameterException, IOException {
		String unencryptedString = ConfigService.fetchContents(ConfigService.fetchFromConfig("teststringpath:"));

		byte[] encryptedBytes = EncryptionService.encryptString(unencryptedString);
		
		String encryptedString = new String( encryptedBytes, Charset.defaultCharset() );
		
		System.out.println(encryptedString);

		assertFalse(unencryptedString.equals(encryptedString));

		String decryptedString = EncryptionService.decryptString(encryptedBytes);
		
		System.out.println(decryptedString);
		
		assertFalse(encryptedString.equals(decryptedString));
		
		assertTrue(unencryptedString.equals(decryptedString));
	}
}