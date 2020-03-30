package bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Jesse Goerzen
 *
 */
public class PaymentBean implements Serializable {
	private static final long serialVersionUID = -1168441519386944499L;
	
	private int year;
	private String paymentType;
	private double amount;
	private Date date;
	private String billingInfo;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBillingInfo() {
		return billingInfo;
	}
	public void setBillingInfo(String billingInfo) {
		this.billingInfo = billingInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
