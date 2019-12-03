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
	
	private User taxReturnUser;
	
	private ArrayList<User> taxPreparersList;
	
	private ArrayList<Document> documents;
	
	private String status;
	
	private ArrayList<Payment> payments;
	
	private double cost;
	
	private Date year;

	/**
	 * Returns the taxReturnID.
	 * @return the taxReturnID
	 */
	public int getTaxReturnID() {
		return taxReturnID;
	}

	/**
	 * Returns the taxReturnUser.
	 * @return the taxReturnUser
	 */
	public User getTaxReturnUser() {
		return taxReturnUser;
	}

	/**
	 * Returns the taxPreparersList.
	 * @return the taxPreparersList
	 */
	public ArrayList<User> getTaxPreparersList() {
		return taxPreparersList;
	}

	/**
	 * Returns the documents.
	 * @return the documents
	 */
	public ArrayList<Document> getDocuments() {
		return documents;
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
	public Date getYear() {
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
	 * Sets the value of taxReturnUser.
	 * @param taxReturnUser the taxReturnUser to set
	 */
	public void setTaxReturnUser(User taxReturnUser) {
		this.taxReturnUser = taxReturnUser;
	}

	/**
	 * Sets the value of taxPreparersList.
	 * @param taxPreparersList the taxPreparersList to set
	 */
	public void setTaxPreparersList(ArrayList<User> taxPreparersList) {
		this.taxPreparersList = taxPreparersList;
	}

	/**
	 * Sets the value of documents.
	 * @param documents the documents to set
	 */
	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
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
	public void setYear(Date year) {
		this.year = year;
	}
}
