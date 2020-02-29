package domain;

import java.util.Calendar;
import java.util.Date;

import exception.UserException;

/**
 * Class Description: User class that defines which attributes every type of
 * user has.
 *
 * @author Tristen Kreutz, Cesar Guzman, Jesse Goerzen
 */
public final class User {

	private String email;

	private boolean active;
	private int permissionLevel;

	private String title;
	private String fName;
	private String mName;
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

	private boolean verified;
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
	 * @param mname
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
	 * @param verified
	 * @param verificationID
	 * @param lastVerificationAttempt
	 * @param lastVerificationType
	 * @throws IllegalArgumentException
	 */
	public User(String email, String fname, String mname, String lname, int permissionLevel, String phone,
			String passHash, String passSalt, String title, Date creationDate, String fax, boolean active,
			String streetAddress, String streetAddress2, String city, String province, String country,
			String postalCode, String language, boolean verified, String verificationID, Date lastVerificationAttempt,
			int lastVerificationType) throws IllegalArgumentException {

		setEmail(email);
		setFName(fname);
		setMName(mname);
		setLName(lname);
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
		setVerified(verified);
		setVerificationID(verificationID);
		setLastVerificationAttempt(lastVerificationAttempt);
		setLastVerificationType(lastVerificationType);
	}

	// This constructor exists only for testing purposes.
	public User(String email, String f_name, String l_name, String phone, String pass_hash, String pass_salt) {
		setEmail(email);
		setFName(f_name);
		setLName(l_name);
		setPhone(phone);
		setPassHash(pass_hash);
		setPassSalt(pass_salt);
	}

	/**
	 * Returns the email.
	 * 
	 * @return <code>String</code> the email
	 */
	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passHash
	 */
	public String getPassHash() {
		return passHash;
	}

	/**
	 * @param passHash the passHash to set
	 */
	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	/**
	 * @return the passSalt
	 */
	public String getPassSalt() {
		return passSalt;
	}

	/**
	 * @param passSalt the passSalt to set
	 */
	public void setPassSalt(String passSalt) {
		this.passSalt = passSalt;
	}

	/**
	 * @return the permissionLevel
	 */
	public int getPermissionLevel() {
		return permissionLevel;
	}

	/**
	 * Sets the value of permissionLevel.
	 * 
	 * @param permissionLevel the permissionLevel to set
	 * @throws UserException
	 */
	public void setPermissionLevel(int permissionLevel) throws IllegalArgumentException {

		switch (permissionLevel) {

		case USER:
		case TAX_PREPARER:
		case ADMIN:
		case SYSADMIN:
			this.permissionLevel = permissionLevel;
			break;
		default:
			throw new IllegalArgumentException("Invalid permissions");
		}
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	private void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the fName
	 */
	public String getFName() {
		return fName;
	}

	/**
	 * @param fName the fName to set
	 */
	public void setFName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the mName
	 */
	public String getMName() {
		return mName;
	}

	/**
	 * @param mName the mName to set
	 */
	public void setMName(String mName) {
		this.mName = mName;
	}

	/**
	 * @return the lName
	 */
	public String getLName() {
		return lName;
	}

	/**
	 * @param lName the lName to set
	 */
	public void setLName(String lName) {
		this.lName = lName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
	 * Sets the value of address.
	 * 
	 * @param address the address to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	 * @param streetAddress2
	 */
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
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
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
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
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
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
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
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
	 * @param verificationID
	 * @throws UserException
	 */
	public void setVerificationID(String verificationID) throws IllegalArgumentException {
		String UUIDRegex = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";
		if (verificationID.matches(UUIDRegex))
			this.verificationID = verificationID;
		else
			throw new IllegalArgumentException("Invalid Verification ID");
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
	 * @param lastVerificationAttempt
	 * @throws UserException
	 */
	public void setLastVerificationAttempt(Date lastVerificationAttempt) throws IllegalArgumentException {
		Calendar current = Calendar.getInstance();
		current.add(Calendar.SECOND, 1);
		if (lastVerificationAttempt.after(new Calendar.Builder().setDate(2020, Calendar.JANUARY, 1).build().getTime())
				&& lastVerificationAttempt.before(current.getTime()))
			this.lastVerificationAttempt = lastVerificationAttempt;
		else
			throw new IllegalArgumentException("Invalid Verification Date");
	}

	/**
	 * 
	 * @return
	 */
	public int getLastVerificationType() {
		return lastVerificationType;
	}

	/**
	 * 
	 * @param lastVerificationType
	 */
	public void setLastVerificationType(int lastVerificationType) {
		this.lastVerificationType = lastVerificationType;
	}
}
