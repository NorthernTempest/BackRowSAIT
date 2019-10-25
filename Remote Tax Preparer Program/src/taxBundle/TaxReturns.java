package taxBundle;

import java.util.ArrayList;
import java.util.Date;

import remoteTaxPreparerBackend.User;

public class TaxReturns {
	
	private String status;
	private Date year;
	private double cost;
	private User customer;
	
	private ArrayList<Payment> payments;
	private ArrayList<User> taxPreparers;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getYear() {
		return year;
	}
	
	public void setYear(Date year) {
		this.year = year;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public User getCustomer() {
		return customer;
	}
	
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	public void addPayment() {
		
	}
	
	public void removePayment() {
		
	}
	
	public void addTaxPreparer() {
		
	}
	
	public void removeTaxPreparer() {
		
	}
	
	public double amtDue() {
		return 0;
	}

}
