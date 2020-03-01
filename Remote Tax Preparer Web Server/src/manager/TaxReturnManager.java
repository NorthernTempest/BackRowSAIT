package manager;

import java.util.ArrayList;

import databaseAccess.TaxReturnDB;
import domain.TaxReturn;
import exception.ConfigException;

/**
 * 
 * Class Description: Class that communicates with the TaxReturnDB class as a
 * proxy to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public final class TaxReturnManager {

	/**
	 * Get all tax returns for a user with email as a parameter
	 * 
	 * @param email the email to get returns for
	 * @return a list of tax returns matching the given email
	 * @throws ConfigException 
	 */
	public static ArrayList<TaxReturn> getByEmail(String email) throws ConfigException {

		return TaxReturnDB.getByUser(email);
	}
}
