package bean;

import java.io.Serializable;

public class TaxReturnBean implements Serializable {
	private static final long serialVersionUID = 1318112686440432126L;
	
	private int year;
	private double cost;
	private double amountOwed;
	
	public TaxReturnBean() { }
	
	public TaxReturnBean(int year, double cost, double amountOwed) {
		this.year = year;
		this.cost = cost;
		this.amountOwed = amountOwed;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getAmountOwed() {
		return amountOwed;
	}

	public void setAmountOwed(double amountOwed) {
		this.amountOwed = amountOwed;
	}
}
