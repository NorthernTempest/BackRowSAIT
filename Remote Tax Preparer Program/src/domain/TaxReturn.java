package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * Class Description: Class defining attributes and methods for a single tax return.
 *
 * @author Tristen Kreutz
 *
 */
public class TaxReturn {
	
	private int taxReturnID;
	
	private User user;
	
	private ArrayList<User> taxPreparers;
	
	private String status;
	
	private ArrayList<Payment> payments;
	
	private double cost;
	
	private int year;

	/**
	 * Returns the taxReturnID.
	 * @return the taxReturnID
	 */
	public int getTaxReturnID() {
		return taxReturnID;
	}

	/**
	 * Returns the user.
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Returns the taxPreparers.
	 * @return the taxPreparers
	 */
	public ArrayList<User> getTaxPreparers() {
		return taxPreparers;
	}

	/**
	 * Returns the status.
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Returns the payments.
	 * @return the payments
	 */
	public ArrayList<Payment> getPayments() {
		return payments;
	}

	/**
	 * Returns the cost.
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Returns the year.
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the value of taxReturnID.
	 * @param taxReturnID the taxReturnID to set
	 */
	public void setTaxReturnID(int taxReturnID) {
		this.taxReturnID = taxReturnID;
	}

	/**
	 * Sets the value of user.
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Sets the value of taxPreparers.
	 * @param taxPreparers the taxPreparers to set
	 */
	public void setTaxPreparers(ArrayList<User> taxPreparers) {
		this.taxPreparers = taxPreparers;
	}

	/**
	 * Sets the value of status.
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Sets the value of payments.
	 * @param payments the payments to set
	 */
	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * Sets the value of cost.
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Sets the value of year.
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	
}
