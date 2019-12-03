package domain;

import java.util.ArrayList;
import java.util.Date;

public class NewReturn {
	
	private int returnID;
	
	private User newReturnUser;
	
	private ArrayList<Document> documents;
	
	private double cost;
	
	private Date year;

	/**
	 * Returns the returnID.
	 * @return the returnID
	 */
	public int getReturnID() {
		return returnID;
	}

	/**
	 * Returns the newReturnUser.
	 * @return the newReturnUser
	 */
	public User getNewReturnUser() {
		return newReturnUser;
	}

	/**
	 * Returns the documents.
	 * @return the documents
	 */
	public ArrayList<Document> getDocuments() {
		return documents;
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
	 * Sets the value of returnID.
	 * @param returnID the returnID to set
	 */
	public void setReturnID(int returnID) {
		this.returnID = returnID;
	}

	/**
	 * Sets the value of newReturnUser.
	 * @param newReturnUser the newReturnUser to set
	 */
	public void setNewReturnUser(User newReturnUser) {
		this.newReturnUser = newReturnUser;
	}

	/**
	 * Sets the value of documents.
	 * @param documents the documents to set
	 */
	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
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
