package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manager.EncryptionService;

class EncryptionServiceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

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
	
	
}
