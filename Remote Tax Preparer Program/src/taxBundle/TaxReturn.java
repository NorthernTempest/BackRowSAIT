package taxBundle;

import java.util.ArrayList;
import java.util.Date;

import remoteTaxPreparerBackend.User;

/**
 * 
 * Class Description: Class defining attributes and methods for a single tax return.
 *
 * @author Tristen Kreutz
 *
 */
public class TaxReturn {
	
	/**
	 * Status of the tax return.
	 */
	private String status;
	
	/**
	 * Year for which the tax return if for.
	 */
	private Date year;
	
	/**
	 * Cost for processing the tax return.
	 */
	private double cost;
	
	/**
	 * User of type customer associated with the tax return.
	 */
	private User customer;
	
	/**
	 * List containing all processed payments.
	 */
	private ArrayList<Payment> payments;
	
	/**
	 * List containing tax preparers assigned to the tax return.
	 */
	private ArrayList<User> taxPreparers;
	
	/**
	 * Returns the status of the tax return.
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status of the tax return.
	 * @param status status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Returns the year the tax return is for.
	 * @return year
	 */
	public Date getYear() {
		return year;
	}
	
	/**
	 * Sets the year the tax return is for.
	 * @param year year
	 */
	public void setYear(Date year) {
		this.year = year;
	}
	
	/**
	 * Returns the cost of processing the tax return.
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Sets the cost of processing the tax return.
	 * @param cost cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * Returns the user object of type customer that is associated with the tax return.
	 * @return customer
	 */
	public User getCustomer() {
		return customer;
	}
	
	/**
	 * Sets the user object of type customer that the tax return belongs to.
	 * @param customer customer
	 */
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	/**
	 * Adds a payment to the tax return.
	 * @param payment payment to add
	 */
	public void addPayment(Payment payment) {
		
	}
	
	/**
	 * Removes a payment from the tax return at the specified index.
	 * @param i index to remove
	 */
	public void removePayment(int i) {
		
	}
	
	/**
	 * Adds a user of type tax preparer to the tax return.
	 * @param taxPreparer tax preparer to add
	 */
	public void addTaxPreparer(User taxPreparer) {
		
	}
	
	/**
	 * Removes a user of type tax preparer from the tax return at the specified index.
	 * @param i index to remove
	 */
	public void removeTaxPreparer(int i) {
		
	}
	
	/**
	 * Returns the remaining amount due (cost) of the tax return.
	 * @return amount due
	 */
	public double amtDue() {
		return 0;
	}

}
