package domain;

import java.util.ArrayList;

import bean.TaxReturnBean;

/**
 * Class Description: Class defining attributes and methods for a single tax return.
 *
 * @author Tristen Kreutz, Cesar Guzman, Taylor Hanlon, Jesse Goerzen
 *
 */
public final class TaxReturn {
	/**
	 * The status of a tax return that has just been instantiated.
	 */
	public static final String INSTANTIATED = "i";
	/**
	 * The status of a tax return that has just sent their request.
	 */
	public static final String REQUEST_SENT = "r";
	/**
	 * The status of a tax return that has just been accepted by a tax preparer.
	 */
	public static final String ACCEPTED = "a";
	/**
	 * The status of a tax return that is being worked on.
	 */
	public static final String PROCESSING = "p";
	/**
	 * The status of a tax return that is waiting on input from the customer.
	 */
	public static final String WAITING_FOR_CUSTOMER = "c";
	/**
	 * The status of a tax return that is waiting for payment from the customer.
	 */
	public static final String WAITING_FOR_PAYMENT = "w";
	/**
	 * The status of a tax return that has been paid for.
	 */
	public static final String PAID = "$";
	/**
	 * The status of a tax return that has been filed.
	 */
	public static final String FILED = "f";
	/**
	 * The status of a tax return that has been cancelled.
	 */
	public static final String CANCELLED = "x";
	
	/**
	 * The email of the owner of this tax return.
	 */
	private String email;
	/**
	 * The list of tax preparers working on the tax return.
	 */
	private ArrayList<User> taxPreparers;
	/**
	 * The status of the tax return.
	 */
	private String status;
	/**
	 * The list of payments the customer has made for this return.
	 */
	private ArrayList<Payment> payments;
	/**
	 * The total cost of this tax return that is to be paid.
	 */
	private double cost;
	/**
	 * The tax year for this tax return.
	 */
	private int year;
	/**
	 * The id of the household this tax return belongs to.
	 */
	private int householdID;
	
	/**
	 * Constructs a complete tax return.
	 * 
	 * @param email String The email of the owner of this tax return.
	 * @param status String The status of the tax return.
	 * @param year int The tax year of the tax return.
	 * @param cost double The total cost of the tax return.
	 */
	public TaxReturn (String email, String status, int year, double cost) {
		setEmail(email);
		setStatus(status);
		setYear(year);
		setCost(cost);
	}
	
	/**
	 * Constructs a tax return without a specified cost.
	 * 
	 * @param email String The email of the owner of this tax return.
	 * @param status String The status of the tax return.
	 * @param year int The tax year of the tax return.
	 */
	public TaxReturn (String email, String status, int year) {
		setEmail(email);
		setStatus(status);
		setYear(year);
	}
	
	/**
	 * Returns the tax return's owner's email.
	 * @return the tax return's owner's email
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
			switch(status) {
			case INSTANTIATED:
			case REQUEST_SENT:
			case ACCEPTED:
			case PROCESSING:
			case WAITING_FOR_CUSTOMER:
			case WAITING_FOR_PAYMENT:
			case PAID:
			case FILED:
			case CANCELLED:
				this.status = status;
			default:
				break;
		}
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
	
	/**
	 * Gets the id of the return's household.
	 * @return int The id of the return's household
	 */
	public int getHouseholdID() {
		return householdID;
	}
	
	/**
	 * Sets the id of the return's household.
	 * @param householdID int The id of the return's household.
	 */
	public void setHouseholdID(int householdID) {
		this.householdID = householdID;
	}
	
	/**
	 * Makes a copy of the tax return with the minimum amount of information necessary for the user.
	 * 
	 * @return TaxReturnBean A copy of this return.
	 */
	public TaxReturnBean copy() {
		TaxReturnBean output = new TaxReturnBean();
		
		output.setCost(cost);
		output.setYear(year);
		
		return output;
	}
}
