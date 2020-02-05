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
	
	private String email;
	
	private String fName;
	
	private String lName;
	
	private int permissionLevel;
	
	private String phone;
	
	private String passHash;
	
	private int ID;
	
	private String title;
	
	private Date creationDate;
	
	private String fax;
	
	private boolean active;
	
	private String language;
	
	private String address;
	
	private final int DEFAULT = 0;
	
	private final int USER = 1;
	
	private final int TAX_PREPARER = 2;
	
	private final int ADMIN = 3;
	
	private final int SYSADMIN = 4;
	
	public User() {
		
	}
	
	public User(String email, String fname, String lname, String phone, String passHash) {
		
		setEmail(email);
		setfName(fname);
		setlName(lname);
		setPhone(phone);
		setPassHash(passHash);
	}

	/**
	 * Returns the email.
	 * @return the email
	 */
	public String getEmail() {
		
		return email;
	}

	/**
	 * Returns the fName.
	 * @return the fName
	 */
	public String getfName() {
		
		return fName;
	}

	/**
	 * Returns the lName.
	 * @return the lName
	 */
	public String getlName() {
		
		return lName;
	}

	/**
	 * Returns the permissionLevel.
	 * @return the permissionLevel
	 */
	public int getPermissionLevel() {
		
		return permissionLevel;
	}

	/**
	 * Returns the phone.
	 * @return the phone
	 */
	public String getPhone() {
		
		return phone;
	}

	/**
	 * Returns the passHash.
	 * @return the passHash
	 */
	public String getPassHash() {
		
		return passHash;
	}

	/**
	 * Returns the iD.
	 * @return the iD
	 */
	public int getID() {
		
		return ID;
	}

	/**
	 * Returns the title.
	 * @return the title
	 */
	public String getTitle() {
		
		return title;
	}

	/**
	 * Returns the creationDate.
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		
		return creationDate;
	}

	/**
	 * Returns the fax.
	 * @return the fax
	 */
	public String getFax() {
		
		return fax;
	}

	/**
	 * Returns the active.
	 * @return the active
	 */
	public boolean isActive() {
		
		return active;
	}

	/**
	 * Returns the language.
	 * @return the language
	 */
	public String getLanguage() {
		
		return language;
	}

	/**
	 * Returns the address.
	 * @return the address
	 */
	public String getAddress() {
		
		return address;
	}

	/**
	 * Sets the value of email.
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		
		this.email = email;
	}

	/**
	 * Sets the value of fName.
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		
		this.fName = fName;
	}

	/**
	 * Sets the value of lName.
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		
		this.lName = lName;
	}

	/**
	 * Sets the value of permissionLevel.
	 * @param permissionLevel the permissionLevel to set
	 */
	public void setPermissionLevel(int permissionLevel) {
		
		switch (permissionLevel) {
		
			case USER:
			case TAX_PREPARER:
			case ADMIN:
			case SYSADMIN:
				this.permissionLevel = permissionLevel;
				break;
				
			default:
				System.out.println("Incorrect role value.");
		}
	}

	/**
	 * Sets the value of phone.
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		
		this.phone = phone;
	}

	/**
	 * Sets the value of passHash.
	 * @param passHash the passHash to set
	 */
	public void setPassHash(String passHash) {
		
		this.passHash = passHash;
	}

	/**
	 * Sets the value of iD.
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		
		ID = iD;
	}

	/**
	 * Sets the value of title.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		
		this.title = title;
	}

	/**
	 * Sets the value of creationDate.
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		
		this.creationDate = creationDate;
	}

	/**
	 * Sets the value of fax.
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		
		this.fax = fax;
	}

	/**
	 * Sets the value of active.
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		
		this.active = active;
	}

	/**
	 * Sets the value of language.
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		
		this.language = language;
	}

	/**
	 * Sets the value of address.
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		
		this.address = address;
	}
}
