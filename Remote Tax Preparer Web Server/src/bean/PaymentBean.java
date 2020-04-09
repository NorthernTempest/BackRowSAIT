package bean;

import java.io.Serializable;
import java.util.Date;

/**
 * A copy of the Payment domain class used to pass necessary info to display to the user.
 * 
 * @author Jesse Goerzen
 */
public final class PaymentBean implements Serializable {
	private static final long serialVersionUID = -1168441519386944499L;
	
	/**
	 * The tax year the payment was made for
	 */
	private int year;
	/**
	 * The type of payment being made
	 */
	private String paymentType;
	/**
	 * The amount payed
	 */
	private double amount;
	/**
	 * The date on which the payment was made
	 */
	private Date date;
	/**
	 * The billing information for the person who made the payment
	 */
	private String billingInfo;
	
	/**
	 * Constructor for the PaymentBean Object
	 * 
	 * @param year The tax year the payment was made for
	 * @param paymentType The type of payment being made
	 * @param amount The amount payed
	 * @param date The date on which the payment was made
	 * @param billingInfo The billing information for the person who made the payment
	 */
	public PaymentBean(int year, String paymentType, double amount, Date date, String billingInfo) {
		this.year = year;
		this.paymentType = paymentType;
		this.amount = amount;
		this.date = date;
		this.billingInfo = billingInfo;
	}
	
	/**
	 * getter for the year parameter
	 * 
	 * @return the tax year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * Setter for the year parameter
	 * 
	 * @param year the tax year
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * getter for the payment type
	 * 
	 * @return the payment type
	 */
	public String getPaymentType() {
		return paymentType;
	}
	/**
	 * setter for payment type
	 * 
	 * @param paymentType the payment type
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * getter for payment amount
	 * 
	 * @return the payment amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * getter for payment amount
	 * 
	 * @param amount the payment amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * getter for the payment date
	 * 
	 * @return the payment date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * setter for the payment date
	 * 
	 * @param date the payment
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * getter for billing info
	 * 
	 * @return the billing info
	 */
	public String getBillingInfo() {
		return billingInfo;
	}
	/**
	 * setter for billing info
	 * 
	 * @param billingInfo the billing info
	 */
	public void setBillingInfo(String billingInfo) {
		this.billingInfo = billingInfo;
	}
}
