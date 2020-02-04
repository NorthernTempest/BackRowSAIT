package manager;

import java.util.ArrayList;

import domain.Payment;

/**
 * 
 * Class Description: 	Class that communicates with the PaymentDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class PaymentManager {

	/**
	 * Takes the Payment object passed into the method and calls the insert method
	 * of the PaymentDB, passing the Payment.
	 * @param payment Payment to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Payment payment) {
		return false;
	}
	
	/**
	 * Takes the Payment object passed into the method and calls the update method
	 * of the PaymentDB, passing the Payment.
	 * @param payment Payment in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Payment payment) {
		return false;
	}
	
	/**
	 * Takes the paymentID passed into the method and calls the delete method
	 * of the PaymentDB, passing the paymentID.
	 * @param paymentID paymentID of the Payment in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int paymentID) {
		return false;
	}
	
	/**
	 * Takes the paymentID passed into the method and calls the get method
	 * of the PaymentDB, passing the paymentID.
	 * @param paymentID paymentID of the Payment in the database to retrieve
	 * @return Payment containing the information of the requested Payment
	 */
	public Payment get(int paymentID) {
		return null;
	}
	
	/**
	 * Calls the getAll method of the PaymentDB.
	 * @return ArrayList<Payment> containing all the Payments in the database
	 */
	public ArrayList<Payment> getAll() {
		return null;
	}
}
