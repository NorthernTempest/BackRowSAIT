package remoteTaxPreparerBackend;

import java.util.ArrayList;
import java.util.Date;

public class Household {
	
	private ArrayList<User> customers;
	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void addCustomer() {
		
	}
	
	public void removeCustomer() {
		
	}
}
