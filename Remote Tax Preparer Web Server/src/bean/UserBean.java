package bean;

import java.io.Serializable;

public class UserBean implements Serializable {
	private static final long serialVersionUID = 1920928974386805998L;

	private String email;
	private String title;
	private String fName;
	private String mName;
	private String lName;
	private String phone;
	private String fax;
	private String streetAddress;
	private String streetAddress2;
	private String city;
	private String province;
	private String country;
	private String postalCode;
	private String language;

	public UserBean() {
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
