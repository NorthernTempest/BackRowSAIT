package domain;

import java.util.Date;

/**
 * 
 * Class Description: Class defining attributes and method for a single payment.
 *
 * @author Tristen Kreutz
 *
 */
public final class Payment {
	
	private int paymentID;
	
	private String paymentType;
	
	private double amount;
	
	private Date date;
	
	private String billingInfo;

	/**
	 * Returns the paymentID.
	 * @return the paymentID
	 */
	public int getPaymentID() {
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
	public void setPaymentID(int paymentID) {
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
}
