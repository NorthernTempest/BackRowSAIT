package manager;

import java.util.ArrayList;
import java.util.Collection;

import bean.PaymentBean;
import bean.PaymentDateComparator;
import bean.TaxReturnBean;
import databaseAccess.PaymentDB;
import databaseAccess.SessionDB;
import databaseAccess.TaxReturnDB;
import domain.Payment;
import domain.TaxReturn;
import service.SortService;

/**
 * 
 * Class Description: Class that communicates with the PaymentDB class as a
 * proxy to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Jesse Goerzen
 *
 */
public class PaymentManager {

	/**
	 * Retrieves all payments associated with a given user.
	 * 
	 * @param sessionID String The session ID of the user trying to look at their
	 *                  payments.
	 * @return Collection\<PaymentBean\> The list of payments belonging to the user
	 *         with the given session ID.
	 */
	public static Collection<PaymentBean> getPayments(String sessionID) {
		ArrayList<PaymentBean> output = new ArrayList<>();

		String email = SessionDB.getEmail(sessionID);

		ArrayList<Payment> payments = PaymentDB.getByEmail(email);

		for (Payment p : payments)
			output.add(p.copy());

		output = SortService.reverse(SortService.sort(output, new PaymentDateComparator()));

		return output;
	}

	/**
	 * Retrieves a list of tax returns from the database that are
	 * tied to the user that is logged in with the matching session ID.
	 * @param sessionID sessionID of user to retrieve
	 * @return Collection<TaxReturnBean> tax returns
	 */
	public static Collection<TaxReturnBean> getReturns(String sessionID) {
		ArrayList<TaxReturnBean> output = new ArrayList<>();

		String email = SessionDB.getEmail(sessionID);

		ArrayList<TaxReturn> returns = TaxReturnDB.getByUser(email);

		ArrayList<Payment> payments = PaymentDB.getByEmail(email);

		for (TaxReturn tr : returns) {
			double totalPaid = 0.0;

			for (Payment p : payments) {
				if (tr.getYear() == p.getYear())
					totalPaid += p.getAmount();
			}

			if (totalPaid < tr.getCost()) {
				TaxReturnBean bean = tr.copy();
				bean.setAmountOwed(tr.getCost() - totalPaid);
				output.add(bean);
			}
		}

		return output;
	}
}
