package manager;

import java.util.ArrayList;

import databaseAccess.TaxReturnDB;
import domain.TaxReturn;
import domain.User;

/**
 * 
 * Class Description: 	Class that communicates with the TaxReturnDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public final class TaxReturnManager {
	
	/**
	 * Get all tax returns for a user with email as a parameter
	 * @param email the email to get returns for
	 * @return a list of tax returns matching the given email
	 */
	public static ArrayList<TaxReturn> getByEmail(String email) {
		User user = UserManager.getUser(email);
		
		return TaxReturnDB.getByUser(user);
	}
	
	/**
	 * Get most recent tax return for user with email as parameter
	 * @param email the email to get return for
	 * @return the most recent tax return matching the given email
	 */
	public static int getMostRecentID(String email) {
		ArrayList<TaxReturn> returns = TaxReturnManager.getByEmail(email);
		
		TaxReturn mostRecentReturn = returns.get(0);
		for (TaxReturn taxReturn : returns) {
			if (taxReturn.getYear() < mostRecentReturn.getYear()) {
				mostRecentReturn = taxReturn;
			}
		}
		
		return mostRecentReturn.getTaxReturnID();
	}
}
