package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * Class Description: Class that defines the attributes and methods for a household object used in grouping customers together.
 *
 * @author Tristen Kreutz
 *
 */
public class Household {
	
	private int householdID;
	
	private String householdName;
	
	private ArrayList<User> householdMembers;
	
	private Date startDate;
	
	private Date endDate;

	/**
	 * Returns the householdID.
	 * @return the householdID
	 */
	public int getHouseholdID() {
		return householdID;
	}

	/**
	 * Returns the householdName.
	 * @return the householdName
	 */
	public String getHouseholdName() {
		return householdName;
	}

	/**
	 * Returns the householdMembers.
	 * @return the householdMembers
	 */
	public ArrayList<User> getHouseholdMembers() {
		return householdMembers;
	}

	/**
	 * Returns the startDate.
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Returns the endDate.
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the value of householdID.
	 * @param householdID the householdID to set
	 */
	public void setHouseholdID(int householdID) {
		this.householdID = householdID;
	}

	/**
	 * Sets the value of householdName.
	 * @param householdName the householdName to set
	 */
	public void setHouseholdName(String householdName) {
		this.householdName = householdName;
	}

	/**
	 * Sets the value of householdMembers.
	 * @param householdMembers the householdMembers to set
	 */
	public void setHouseholdMembers(ArrayList<User> householdMembers) {
		this.householdMembers = householdMembers;
	}

	/**
	 * Sets the value of startDate.
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Sets the value of endDate.
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
