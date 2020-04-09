package bean;

import java.io.Serializable;

/**
 * The bean for the user class
 * 
 * @author Jesse Goerzen
 *
 */
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1920928974386805998L;

	/**
	 * the email of the user
	 */
	private String email;
	/**
	 * the title of the user
	 */
	private String title;
	/**
	 * the first name of the user
	 */
	private String fName;
	/**
	 * the middle name of the user
	 */
	private String mName;
	/**
	 * the last name of the user
	 */
	private String lName;
	/**
	 * the phone number of the user
	 */
	private String phone;
	/**
	 * the fax number of the user
	 */
	private String fax;
	/**
	 * the first street address field for the user
	 */
	private String streetAddress;
	/**
	 * the second street address field for the user
	 */
	private String streetAddress2;
	/**
	 * the city of the user
	 */
	private String city;
	/**
	 * the province of the user
	 */
	private String province;
	/**
	 * the country of the user
	 */
	private String country;
	/**
	 * the postal code of the user
	 */
	private String postalCode;
	/**
	 * the language of the user
	 */
	private String language;

	/**
	 * empty bean constructor
	 */
	public UserBean() {
	}

	/**
	 * the constructor for the user bean
	 * 
	 * @param email the email of the user
	 * @param title the title of the user
	 * @param fName the first name of the user
	 * @param mName the middle name of the user
	 * @param lName the last name of the user
	 * @param phone the phone number of the user
	 * @param fax the fax number of the user
	 * @param streetAddress the first street address field of the user
	 * @param streetAddress2 the second street address field of the user
	 * @param city the city of the user
	 * @param province the province of the user
	 * @param country the country of the user
	 * @param postalCode the postal code of the user
	 * @param language the language of the user
	 */
	public UserBean(String email, String title, String fName, String mName, String lName, String phone, String fax,
			String streetAddress, String streetAddress2, String city, String province, String country,
			String postalCode, String language) {
		this.email = email;
		this.title = title;
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.phone = phone;
		this.fax = fax;
		this.streetAddress = streetAddress;
		this.streetAddress2 = streetAddress2;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.language = language;
	}

	/**
	 * Getter for the email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for the email
	 * 
	 * @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for the title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the title
	 * 
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for the first name
	 * 
	 * @return the first name
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * Setter for the first name
	 * 
	 * @param fName the first name
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * Getter for the middle name
	 * 
	 * @return the middle name
	 */
	public String getmName() {
		return mName;
	}

	/**
	 * Setter for the middle name
	 * 
	 * @param mName the middle name
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}

	/**
	 * Getter for the last name
	 * 
	 * @return the last name
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * Setter for the last name
	 * 
	 * @param lName the last name
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * Getter for the phone number
	 * 
	 * @return the phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter for the phone number
	 * 
	 * @param phone the phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter for the fax number
	 * 
	 * @return the fax number
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Setter for the fax number
	 * 
	 * @param fax the fax number
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Getter for the first street address field
	 * 
	 * @return the first street address field
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Setter for the first street address field
	 * 
	 * @param streetAddress the first street address field
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * Getter for the second street address field
	 * 
	 * @return the second street address field
	 */
	public String getStreetAddress2() {
		return streetAddress2;
	}

	/**
	 * Setter for the second street address field
	 * 
	 * @param streetAddress2 the second street address field
	 */
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	/**
	 * Getter for the city
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Setter for the city
	 * 
	 * @param city the city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Getter for the province
	 * 
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Setter for the province
	 * 
	 * @param province the province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * Getter for the country
	 * 
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Setter for the country
	 * 
	 * @param country the country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Getter for the postal code
	 * 
	 * @return the postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Setter for the postal code
	 * 
	 * @param postalCode the postal code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Getter for the language
	 * 
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Setter for the language
	 * 
	 * @param language the language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
}
