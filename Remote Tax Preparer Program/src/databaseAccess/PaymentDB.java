package databaseAccess;

import java.util.ArrayList;

import domain.Payment;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the payment table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class PaymentDB {

	/**
	 * Establishes a connection with the database and inserts the Payment object passed
	 * into this method into the payment table.
	 * @param payment Payment to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Payment payment) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and updates the Payment in the payment
	 * table that shares a Primary Key with the Payment being passed into this method.
	 * All values of the Payment in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param payment Payment to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Payment payment) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the Payment from the
	 * payment table that has a Primary Key matching the paymentID being passed
	 * into this method.
	 * @param paymentID paymentID of the Payment to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int paymentID) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the Payment in the
	 * payment table that has a Primary Key matching the paymentID being passed
	 * into this method.
	 * @param paymentID paymentID of the Payment to retrieve from the database
	 * @return Payment that contains the information of the requested Payment
	 */
	public Payment get(int paymentID) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Payments
	 * within the payment table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Payment> containing all of the Payments from the database
	 */
	public ArrayList<Payment> getAll() {
		return null;
	}
}
