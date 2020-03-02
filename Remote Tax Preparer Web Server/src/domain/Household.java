package domain;

import java.util.ArrayList;

/**
 * 
 * Class Description: Class that defines the attributes and methods for a household object used in grouping customers together.
 *
 * @author Tristen Kreutz
 *
 */
public final class Household {
	
	private int householdID;
	
	private String householdName;
	
	private ArrayList<User> householdMembers;
	
	public Household (int householdID, String householdName) {
		setHouseholdID(householdID);
		setHouseholdName(householdName);
	}

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
}
