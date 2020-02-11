package domain;

import java.util.Calendar;
import java.util.Date;

import exception.UserException;

/**
 * 
 * Class Description: User class that defines which attributes every type of
 * user has.
 *
 * @author Tristen Kreutz, Cesar Guzman, Jesse Goerzen
 *
 */
public final class User {

	private String email;

	private boolean active;
	private int permissionLevel;

	private String title;
	private String fName;
	private String lName;

	private String passHash;
	private String passSalt;

	private Date creationDate;

	private String phone;
	private String fax;

	private String streetAddress;
	private String streetAddress2;
	private String city;
	private String province;
	private String country;
	private String postalCode;

	private String language;

	private String verificationID;

	private Date lastVerificationAttempt;

	private int lastVerificationType;

	public static final int DEFAULT = 0;
	public static final int USER = 1;
	public static final int TAX_PREPARER = 2;
	public static final int ADMIN = 3;
	public static final int SYSADMIN = 4;

	public static final int VERIFY_TYPE_CREATE_ACCOUNT = 11;
	public static final int VERIFY_TYPE_PASS_RESET = 12;
	
	/**
	 * Constructs an empty user.
	 */
	public User() {

	}
	
	/**
	 * Constructs a complete user with all fields.
	 * 
	 * @param email
	 * @param fname
	 * @param lname
	 * @param permissionLevel
	 * @param phone
	 * @param passHash
	 * @param passSalt
	 * @param title
	 * @param creationDate
	 * @param fax
	 * @param active
	 * @param streetAddress
	 * @param streetAddress2
	 * @param city
	 * @param province
	 * @param country
	 * @param postalCode
	 * @param language
	 * @param verificationID
	 * @param lastVerificationAttempt
	 * @param lastVerificationType
	 * @throws UserException
	 */
	public User(String email, String fname, String lname, int permissionLevel, String phone, String passHash,
			String passSalt, String title, Date creationDate, String fax, boolean active, String streetAddress,
			String streetAddress2, String city, String province, String country, String postalCode, String language,
			String verificationID, Date lastVerificationAttempt, int lastVerificationType) throws UserException {

		setEmail(email);
		setfName(fname);
		setlName(lname);
		setPermissionLevel(permissionLevel);
		setPhone(phone);
		setPassHash(passHash);
		setPassSalt(passSalt);
		setTitle(title);
		setCreationDate(creationDate);
		setFax(fax);
		setActive(active);
		setStreetAddress(streetAddress);
		setStreetAddress2(streetAddress);
		setCity(city);
		setProvince(province);
		setCountry(country);
		setPostalCode(postalCode);
		setLanguage(language);
		setVerificationID(verificationID);
		setLastVerificationAttempt(lastVerificationAttempt);
		setLastVerificationType(lastVerificationType);
	}

	/**
	 * Returns the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the fName.
	 * 
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * Returns the lName.
	 * 
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * Returns the permissionLevel.
	 * 
	 * @return the permissionLevel
	 */
	public int getPermissionLevel() {
		return permissionLevel;
	}

	/**
	 * Returns the phone.
	 * 
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Returns the passHash.
	 * 
	 * @return the passHash
	 */
	public String getPassHash() {
		return passHash;
	}

	/**
	 * Returns the salt for hashing the password.
	 * 
	 * @return the salt for hashing the password.
	 */
	public String getPassSalt() {
		return passSalt;
	}

	/**
	 * Returns the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the creationDate.
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Returns the fax.
	 * 
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Returns the active.
	 * 
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Returns the language.
	 * 
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Returns the address.
	 * 
	 * @return the address
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * 
	 * @return
	 */
	public String getStreetAddress2() {
		return streetAddress2;
	}

	/**
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 
	 * @return
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getVerificationID() {
		return verificationID;
	}

	/**
	 * 
	 * @return
	 */
	public Date getLastVerificationAttempt() {
		return lastVerificationAttempt;
	}

	/**
	 * 
	 * @return
	 */
	public int getLastVerificationType() {
		return lastVerificationType;
	}

	/**
	 * Sets the value of email.
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {

		this.email = email;
	}

	/**
	 * Sets the value of fName.
	 * 
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * Sets the value of lName.
	 * 
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * Sets the value of permissionLevel.
	 * 
	 * @param permissionLevel the permissionLevel to set
	 * @throws UserException 
	 */
	public void setPermissionLevel(int permissionLevel) throws UserException {
		switch (permissionLevel) {

		case USER:
		case TAX_PREPARER:
		case ADMIN:
		case SYSADMIN:
			this.permissionLevel = permissionLevel;
			break;

		default:
			throw new UserException("Invalid permission level");
		}
	}

	/**
	 * Sets the value of phone.
	 * 
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Sets the value of hash of the user's password.
	 * 
	 * @param passHash the passHash to set
	 */
	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	/**
	 * Sets the salt for calculating the user's password
	 * 
	 * @param passSalt
	 */
	private void setPassSalt(String passSalt) {
		this.passSalt = passSalt;
	}

	/**
	 * Sets the value of title.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the value of creationDate.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Sets the value of fax.
	 * 
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Sets the value of active.
	 * 
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Sets the value of language.
	 * 
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Sets the value of address.
	 * 
	 * @param address the address to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * 
	 * @param streetAddress2
	 */
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	/**
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * 
	 * @param verificationID
	 * @throws UserException
	 */
	public void setVerificationID(String verificationID) throws UserException {
		String UUIDRegex = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";
		if (verificationID.matches(UUIDRegex))
			this.verificationID = verificationID;
		else
			throw new UserException("Invalid Verification ID");
	}

	/**
	 * 
	 * @param lastVerificationAttempt
	 * @throws UserException
	 */
	public void setLastVerificationAttempt(Date lastVerificationAttempt) throws UserException {
		Calendar current = Calendar.getInstance();
		current.add(Calendar.SECOND, 1);
		if (lastVerificationAttempt.after(new Calendar.Builder().setDate(2020, Calendar.JANUARY, 1).build().getTime())
				&& lastVerificationAttempt.before( current.getTime() ))
			this.lastVerificationAttempt = lastVerificationAttempt;
		else
			throw new UserException("Invalid Verification Date");
	}

	/**
	 * 
	 * @param lastVerificationType
	 */
	public void setLastVerificationType(int lastVerificationType) {
		this.lastVerificationType = lastVerificationType;
	}
}
