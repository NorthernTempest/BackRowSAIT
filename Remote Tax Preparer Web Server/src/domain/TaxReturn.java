package domain;

import java.util.ArrayList;

import bean.TaxReturnBean;

/**
 * 
 * Class Description: Class defining attributes and methods for a single tax return.
 *
 * @author Tristen Kreutz, Cesar Guzman, Taylor Hanlon
 *
 */
public final class TaxReturn {
	
	public static final char INSTANTIATED = 'i';
	public static final char REQUEST_SENT = 'r';
	public static final char ACCEPTED = 'a';
	public static final char PROCESSING = 'p';
	public static final char WAITING_FOR_CUSTOMER = 'c';
	public static final char WAITING_FOR_PAYMENT = 'w';
	public static final char PAID = '$';
	public static final char FILED = 'f';
	public static final char CANCELLED = 'x';
	
	private String email;
	
	private ArrayList<User> taxPreparers;
	
	private String status;
	
	private ArrayList<Payment> payments;
	
	private double cost;
	
	private int year;
	
	private int householdID;
	
	public TaxReturn (String email, String status, int year, double cost) {
		setEmail(email);
		setStatus(status);
		setYear(year);
		setCost(cost);
	}
	
	public TaxReturn (String email, String status, int year) {
		this.email = email;
		this.status = status;
		this.year = year;
	}
	
	/**
	 * Returns the user.
	 * @return the user
	 */
	public String getEmail() {
		return email;
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
	 * Sets the value of user.
	 * @param user the user to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	public int getHouseholdID() {
		return householdID;
	}

	public void setHouseholdID(int householdID) {
		this.householdID = householdID;
	}

	public TaxReturnBean copy() {
		TaxReturnBean output = new TaxReturnBean();
		
		output.setCost(cost);
		output.setYear(year);
		
		return output;
	}
}
