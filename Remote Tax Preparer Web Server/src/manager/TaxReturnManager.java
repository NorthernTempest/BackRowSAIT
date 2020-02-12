package manager;

import java.util.ArrayList;

import databaseAccess.TaxReturnDB;
import domain.TaxReturn;
import domain.User;
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
		User user = UserManager.getUser(email);

		return TaxReturnDB.getByUser(user);
	}

	/**
	 * Get most recent tax return for user with email as parameter
	 * 
	 * @param email the email to get return for
	 * @return the most recent tax return matching the given email or -1 if not found
	 * @throws ConfigException 
	 */
	public static int getID(String email, int year) throws ConfigException {
		ArrayList<TaxReturn> taxReturns = TaxReturnManager.getByEmail(email);

		for (TaxReturn taxReturn : taxReturns) {
			if (taxReturn.getYear() == year) {
				return taxReturn.getTaxReturnID();
			}
		}

		return -1;
	}
}
