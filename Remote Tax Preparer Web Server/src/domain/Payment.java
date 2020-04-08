package domain;

import java.util.Date;
import java.util.UUID;

import bean.PaymentBean;

/**
 * 
 * Class Description: Class defining attributes and methods for a single payment.
 *
 * @author Tristen Kreutz
 *
 */
public final class Payment {
	
	private String paymentID;
	
	private String paymentType;
	
	private double amount;
	
	private Date date;
	
	private int year;
	
	private String email;
	
	private String billingInfo;
	
	public Payment (String email, int year, String paymentType, double amount, Date date) {
		paymentID = UUID.randomUUID().toString();
		setEmail(email);
		setYear(year);
		setPaymentType(paymentType);
		setAmount(amount);
		setDate(date);
	}
	
	public Payment (String paymentID, String email, int year, String paymentType, double amount, Date date) {
		setPaymentID(paymentID);
		setEmail(email);
		setYear(year);
		setPaymentType(paymentType);
		setAmount(amount);
		setDate(date);
	}

	/**
	 * Returns the paymentID.
	 * @return the paymentID
	 */
	public String getPaymentID() {
		return paymentID;
	}

	/**
	 * Returns the paymentType.
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Returns the amount.
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Returns the date.
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns the billingInfo.
	 * @return the billingInfo
	 */
	public String getBillingInfo() {
		return billingInfo;
	}

	/**
	 * Sets the value of paymentID.
	 * @param paymentID the paymentID to set
	 */
	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}

	/**
	 * Sets the value of paymentType.
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Sets the value of amount.
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Sets the value of date.
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Sets the value of billingInfo.
	 * @param billingInfo the billingInfo to set
	 */
	public void setBillingInfo(String billingInfo) {
		this.billingInfo = billingInfo;
	}

	/**
	 * Returns the year.
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year of the Payment.
	 * @param year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Returns the email.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the Payment.
	 * @param email email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Method to copy the values in this object to a new object that is then returned.
	 * @return PaymentBean copy of this object
	 */
	public PaymentBean copy() {
		return new PaymentBean(year, paymentType, amount, date, billingInfo);
	}
}
