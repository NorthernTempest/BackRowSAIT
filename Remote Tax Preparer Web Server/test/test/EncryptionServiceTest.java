package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;

import domain.Document;
import service.EncryptionService;

class EncryptionServiceTest {

	@Test
	void saltTest() {
		for (int i = 0; i < 100000; i++) {
			String salt1 = EncryptionService.getSalt();
			String salt2 = EncryptionService.getSalt();

			assertTrue(!salt1.equals(salt2), salt1 + " = " + salt2);
		}
	}

	@Test
	void hashTest()
			throws InvalidKeySpecException, NoSuchAlgorithmException, NumberFormatException, FileNotFoundException {
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
	void encrpytDocTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IOException, InvalidAlgorithmParameterException {
		String unencryptedFilePath = "C:\\Capstone\\TestFiles\\test_pdf_large.pdf";
		
		Document encryptedFile = EncryptionService.encryptDocument( unencryptedFilePath, false, false, 1 );
		
		String decryptedFilePath = EncryptionService.decryptDocument( encryptedFile );
	}
}