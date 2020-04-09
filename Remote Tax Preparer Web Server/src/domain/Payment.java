package domain;

import java.util.Date;
import java.util.UUID;

import bean.PaymentBean;

/**
 * Class Description: A record of a user paying for their tax return.
 *
 * @author Tristen Kreutz, Jesse Goerzen
 */
public final class Payment {
	/**
	 * The id of the payment.
	 */
	private String paymentID;
	/**
	 * The type of payment made by the user.
	 */
	private String paymentType;
	/**
	 * The amount that the user paid.
	 */
	private double amount;
	/**
	 * The date and time that the payment was made.
	 */
	private Date date;
	/**
	 * The tax year of the tax return for which this payment was made.
	 */
	private int year;
	/**
	 * The email of the user whose tax return was paid for.
	 */
	private String email;
	/**
	 * Any additional information about the bill of sale.
	 */
	private String billingInfo;
	
	/**
	 * Constructs a new payment record.
	 * 
	 * @param email The email of the user whose tax return was paid for.
	 * @param year The tax year of the tax return for which this payment was made.
	 * @param paymentType The type of payment made by the user.
	 * @param amount The amount that the user paid.
	 * @param date The date and time that the payment was made.
	 */
	public Payment (String email, int year, String paymentType, double amount, Date date) {
		paymentID = UUID.randomUUID().toString();
		setEmail(email);
		setYear(year);
		setPaymentType(paymentType);
		setAmount(amount);
		setDate(date);
	}
	
	/**
	 * Constructs a payment record from the database.
	 * 
	 * @param paymentID
	 * @param email
	 * @param year
	 * @param paymentType
	 * @param amount
	 * @param date
	 */
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
