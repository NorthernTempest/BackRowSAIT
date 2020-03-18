package generate;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import exception.ConfigException;
import service.EncryptionService;

public class PassGenerator {
	public static void main( String[] args ) throws NumberFormatException, NoSuchAlgorithmException, InvalidKeySpecException, ConfigException {
		String salt = EncryptionService.getSalt();
		System.out.println( "hash: " + EncryptionService.hash("password", salt) );
		System.out.println( "salt: " + salt );
	}
}
