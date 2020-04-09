package bean;

import java.io.Serializable;

/**
 * @author Jesse Goerzen
 *
 */
public class TaxReturnBean implements Serializable {
	private static final long serialVersionUID = 1318112686440432126L;
	
	/**
	 * the year of the tax return
	 */
	private int year;
	/**
	 * the cost of the tax return
	 */
	private double cost;
	/**
	 * amount owed on the tax return
	 */
	private double amountOwed;
	
	/**
	 * Empty bean constructor
	 */
	public TaxReturnBean() { }
	
	/**
	 * Constructor for the taxReturnBean
	 * 
	 * @param year the year of the tax return
	 * @param the cost of the tax return
	 * @param amountOwed cost amount owed on the tax return
	 */
	public TaxReturnBean(int year, double cost, double amountOwed) {
		this.year = year;
		this.cost = cost;
		this.amountOwed = amountOwed;
	}

	/**
	 * Getter for the tax year
	 * 
	 * @return the tax year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Setter for the tax year
	 * 
	 * @param year the tax year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Getter for the tax return cost
	 * 
	 * @return the tax return cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Setter for the tax return cost
	 * 
	 * @param cost the tax return cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Getter for the amount owed
	 * 
	 * @return the amount owed
	 */
	public double getAmountOwed() {
		return amountOwed;
	}

	/**
	 * Setter for the amount owed
	 * 
	 * @param amountOwed the amount owed
	 */
	public void setAmountOwed(double amountOwed) {
		this.amountOwed = amountOwed;
	}
}
