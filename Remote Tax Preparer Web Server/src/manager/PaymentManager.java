package manager;

import java.util.ArrayList;
import java.util.Collection;

import bean.PaymentBean;
import databaseAccess.PaymentDB;
import databaseAccess.SessionDB;
import domain.Payment;

/**
 * 
 * Class Description: 	Class that communicates with the PaymentDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Jesse Goerzen
 *
 */
public class PaymentManager {
	
	/**
	 * Retrieves all payments associated with a given user.
	 * 
	 * @param sessionID String The session ID of the user trying to look at their payments.
	 * @return Collection\<PaymentBean\> The list of payments belonging to the user with the given session ID.
	 */
	public static Collection<PaymentBean> getPayments(String sessionID) {
		Collection<PaymentBean> output = new ArrayList<>();
		
		String email = SessionDB.getEmail(sessionID);
		
		ArrayList<Payment> payments = PaymentDB.getByEmail(email);
		
		for(Payment p : payments)
			output.add(p.copy());
		
		return output;
	}
}
