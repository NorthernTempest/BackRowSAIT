package manager;

import java.util.ArrayList;
import java.util.Collection;

import bean.PaymentBean;
import bean.TaxReturnBean;
import databaseAccess.PaymentDB;
import databaseAccess.SessionDB;
import databaseAccess.TaxReturnDB;
import domain.Payment;
import domain.TaxReturn;

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
	
	public static Collection<TaxReturnBean> getReturns(String sessionID) {
		ArrayList<TaxReturnBean> output = new ArrayList<>();
		
		String email = SessionDB.getEmail(sessionID);
		
		ArrayList<TaxReturn> returns = TaxReturnDB.getByUser(email);
		
		ArrayList<Payment> payments = PaymentDB.getByEmail(email);
		
		for( TaxReturn tr : returns ) {
			double totalPaid = 0.0;
			
			for( Payment p : payments ) {
				if (tr.getYear() == p.getYear())
					totalPaid += p.getAmount();
			}
			
			if(totalPaid < tr.getCost()) {
				TaxReturnBean bean = tr.copy();
				bean.setAmountOwed(tr.getCost() - totalPaid);
				output.add( bean );
			}
		}
		
		return output;
	}
}
