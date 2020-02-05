package test;

import static org.junit.jupiter.api.Assertions.*;

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
	void hashTest() {
		String salt = EncryptionService.getSalt();
	}
}
