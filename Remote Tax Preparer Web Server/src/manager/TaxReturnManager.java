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
	
	/**
	 * @param email the email to check if return exists for
	 * @param taxYear the tax year to check if return exists for
	 * @return true if user has a tax return for that year, false if they do not
	 * @throws ConfigException
	 */
	public static boolean hasReturnForYear(String email, int taxYear) throws ConfigException {
		boolean hasReturn = false;
		
		ArrayList<TaxReturn> taxReturns = getByEmail(email);
		for(TaxReturn taxReturn : taxReturns) {
			if (taxReturn.getYear() == taxYear) {
				hasReturn = true;
			}
		}
		
		return hasReturn;
	}
}
