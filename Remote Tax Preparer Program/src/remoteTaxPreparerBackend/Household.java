package remoteTaxPreparerBackend;

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
	
	/**
	 * List of customers added to household.
	 */
	private ArrayList<User> customers;
	
	/**
	 * Date containing the value of when the household was created.
	 */
	private Date startDate;
	
	/**
	 * Date containing the value of when the household should be automatically deleted.
	 */
	private Date endDate;
	
	/**
	 * Returns the date for when the household was created.
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the date for when the household was created.
	 * @param startDate startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Returns the date for when the household should be automatically deleted.
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Sets the date for when the household should be automatically deleted.
	 * @param endDate endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Adds a customer object to the household.
	 * @param customer customer to add
	 */
	public void addCustomer(User customer) {
		
	}
	
	/**
	 * Removes a customer object from the household.
	 * @param i index to remove
	 */
	public void removeCustomer(int i) {
		
	}
}
