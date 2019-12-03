package domain;

import java.util.Date;

/**
 * 
 * Class Description: User class that defines which attributes every type of user has.
 *
 * @author Tristen Kreutz
 *
 */
public class User {
	
	/**
	 * Username of the user.
	 */
	private String 	username;
	
	/**
	 * Email address of the user.
	 */
	private String	email;
	
	/**
	 * Phone number of the user.
	 */
	private String	phone;
	
	/**
	 * Password hash of the user.
	 */
	private String	passHash;
	
	/**
	 * Title, if applicable, for the user.
	 */
	private String	title;
	
	/**
	 * First name of the user.
	 */
	private String	fname;
	
	/**
	 * Last name of the user.
	 */
	private String	lname;
	
	/**
	 * Fax number, if applicable, of the user.
	 */
	private String	faxNum;
	
	/**
	 * Language preference of the user.
	 */
	private String	language;
	
	/**
	 * Address of the user.
	 */
	private String	address;
	
	/**
	 * Permission level of the user, defining which methods are available.
	 */
	private int permissionLevel;
	
	/**
	 * Unique identification of the user.
	 */
	private long id;
	
	/**
	 * Boolean representing whether or not the user account is active.
	 */
	private Boolean active;
	
	/**
	 * Date for when the user account was created.
	 */
	private Date creationDate;
	
	/**
	 * Returns the username of the user.
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username of the user.
	 * @param username username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Returns the email of the user.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email of the user.
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns the phone number of the user.
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * Sets the phone number of the user
	 * @param phone phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * Returns the password hash of the user.
	 * @return password hash
	 */
	public String getPassHash() {
		return passHash;
	}
	
	/**
	 * Sets the password hash of the user.
	 * @param passHash password hash
	 */
	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}
	
	/**
	 * Returns the title of the user.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of the user.
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns the first name of the user.
	 * @return first name
	 */
	public String getFname() {
		return fname;
	}
	
	/**
	 * Sets the first name of the user.
	 * @param fname first name
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	/**
	 * Returns the last name of the user.
	 * @return last name
	 */
	public String getLname() {
		return lname;
	}
	
	/**
	 * Sets the last name of the user.
	 * @param lname last name
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	/**
	 * Returns the fax number of the user.
	 * @return fax number
	 */
	public String getFaxNum() {
		return faxNum;
	}
	
	/**
	 * Sets the fax number of the user
	 * @param faxNum fax number
	 */
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}
	
	/**
	 * Returns the language preference of the user.
	 * @return language preference
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Sets the language preference of the user.
	 * @param language language preference
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * Returns the address of the user.
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the address of the user.
	 * @param address address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Returns the permission level of the user.
	 * @return permission level
	 */
	public int getPermissionLevel() {
		return permissionLevel;
	}
	
	/**
	 * Sets the permission level of the user.
	 * @param permissionLevel permission level
	 */
	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	
	/**
	 * Returns the identification number of the user.
	 * @return id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Sets the identification number of the user.
	 * @param id id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Returns boolean based on whether or not the account is active.
	 * @return active
	 */
	public Boolean isActive() {
		return active;
	}
	
	/**
	 * Sets the active boolean of the user.
	 * @param active active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	/**
	 * Returns the creation date of the user.
	 * @return creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * Validates the password for the specified user.
	 */
	public void checkPass()
	{
		
	}
}
