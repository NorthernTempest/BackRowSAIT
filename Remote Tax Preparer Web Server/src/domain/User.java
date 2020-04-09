package domain;

import bean.UserBean;
import exception.ConfigException;
import service.EncryptionService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Class Description: User class that defines which attributes every type of
 * user has.
 *
 * @author Tristen Kreutz, Cesar Guzman, Jesse Goerzen
 */
public final class User {

	/**
	 * The email address of the user. This is unique to each user. Must be less than
	 * or equal to 100 characters.
	 */
	private String email;

	/**
	 * Whether the user is active in the system.
	 */
	private boolean active;
	/**
	 * The level of permission the user has in the system. Valid values are given by
	 * the constants DEFAULT, USER, TAX_PREPARER, ADMIN, and SYSADMIN.
	 */
	private int permissionLevel;

	/**
	 * The title by which the user wants to be addressed. Must be one of Na for not
	 * applicable, Mr for mister, Mrs for missus, Ms for miss, or Mx.
	 */
	private String title;
	/**
	 * The user's first name. Must be 25 characters or less.
	 */
	private String fName;
	/**
	 * The user's middle name. Must be 25 characters or less.
	 */
	private String mName;
	/**
	 * The user's last name. Must be 25 characters or less.
	 */
	private String lName;

	/**
	 * The hashed form of the user's password. Is based on the user's salt, as well
	 * as the password only known by them. Is 64 characters long.
	 */
	private String passHash;
	/**
	 * The random string of characters used as a salt for hashing the user's
	 * password. Is 44 characters long.
	 */
	private String passSalt;

	/**
	 * The day and time that the user was created.
	 */
	private Date creationDate;

	/**
	 * The user's phone number. Must be 15 characters or less. TODO Must follow ....
	 */
	private String phone;
	/**
	 * The user's fax number. Must be 15 characters or less.
	 */
	private String fax;

	/**
	 * The first line of the user's mailing street address. Must be 320 characters
	 * or fewer.
	 */
	private String streetAddress;
	/**
	 * The second line of the user's mailing street address. Must be 320 characters
	 * or fewer.
	 */
	private String streetAddress2;
	/**
	 * The city of the user's mailing address. Must be 100 characters or fewer.
	 */
	private String city;
	/**
	 * The province of the user's mailing address. Must follow the 2 character
	 * format of the ISO_3166-2 standard.
	 */
	private String province;
	/**
	 * The country of the user's mailing address. Must follow the 2 character format
	 * of the ISO_3166-2 standard.
	 */
	private String country;
	/**
	 * The postal code of the user's mailing address. Must be 10 characters long or
	 * fewer.
	 */
	private String postalCode;

	/**
	 * The user's preferred language. Must be either en for English, or es for
	 * Espa�ol.
	 */
	private String language;

	/**
	 * Whether or not the user's email address has been verified.
	 */
	private boolean verified;
	/**
	 * The String containing the UUID used to identify this user for verification
	 * attempts through email.
	 */
	private String verificationID;
	/**
	 * The time and date that the user last requested an action that required
	 * verification.
	 */
	private Date lastVerificationAttempt;
	/**
	 * The last type of request the user gave for something that required
	 * verification.
	 */
	private int lastVerificationType;

	/**
	 * The default user permission level. Should not be used.
	 */
	public static final int DEFAULT = 0;
	/**
	 * The user permission level for the client's customers.
	 */
	public static final int USER = 1;
	/**
	 * The user permission level for the tax preparers employed by the client.
	 */
	public static final int TAX_PREPARER = 2;
	/**
	 * The user permission level for the administrators of the system.
	 */
	public static final int ADMIN = 3;
	/**
	 * The user permission level for the system administrator of the system.
	 */
	public static final int SYSADMIN = 4;

	/**
	 * The code indicating that the last type of verification requested was for
	 * creating the account.
	 */
	public static final int VERIFY_TYPE_CREATE_ACCOUNT = 11;
	/**
	 * The code indicating that the last type of verification requested was for
	 * reseting the password.
	 */
	public static final int VERIFY_TYPE_PASS_RESET = 12;
	/**
	 * The code indicating that no verification has been requested or the last
	 * verification was completed.
	 */
	public static final int VERIFY_TYPE_NONE = -1;

	/**
	 * Constructs an empty User object.
	 */
	public User() {

	}

	/**
	 * Constructs a complete User object with all fields. Used in UserDB for reading
	 * users from the database.
	 * 
	 * @param email                   String The email address of the user. Used as
	 *                                the unique identifier for the user.
	 * @param fname                   String The user's first name.
	 * @param mname                   String The user's middle name.
	 * @param lname                   String The user's last name.
	 * @param permissionLevel         int The level of permission that the user has
	 *                                in the system.
	 * @param phone                   String The user's phone number.
	 * @param passHash                String The hashed for of the user's password
	 *                                that is used to identify whether correct
	 *                                passwords are entered.
	 * @param passSalt                String The salt string used to randomize the
	 *                                user's hashed password.
	 * @param title                   String The title by which the user wishes to
	 *                                be addressed.
	 * @param creationDate            Date The date and time on which the user
	 *                                created their account.
	 * @param fax                     String The fax number used to contact the
	 *                                user.
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
		setStreetAddress2(streetAddress2);
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

	/**
	 * Constructs a complete user with all fields.
	 * 
	 * @param email
	 * @param fname
	 * @param mname
	 * @param lname
	 * @param permissionLevel
	 * @param phone
	 * @param password
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
	 * @throws ConfigException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public User(String email, String fname, String mname, String lname, int permissionLevel, String phone,
			String password, String title, Date creationDate, String fax, boolean active, String streetAddress,
			String streetAddress2, String city, String province, String country, String postalCode, String language,
			boolean verified, String verificationID, Date lastVerificationAttempt, int lastVerificationType)
			throws IllegalArgumentException, ConfigException, NoSuchAlgorithmException, InvalidKeySpecException {

		setEmail(email);
		setFName(fname);
		setMName(mname);
		setLName(lname);
		setPermissionLevel(permissionLevel);
		setPhone(phone);
		setPassword(password);
		setTitle(title);
		setCreationDate(creationDate);
		setFax(fax);
		setActive(active);
		setStreetAddress(streetAddress);
		setStreetAddress2(streetAddress2);
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

	/**
	 * Returns the email.
	 * 
	 * @return <code>String</code> the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email of the user.
	 * @param email
	 */
	private void setEmail(String email) {
		if (email.length() > 100 || email.length() == 0) {
			throw new IllegalArgumentException("Email is invalid, try again");
		}
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
	 * Sets the password of this user after checking the validity based
	 * on different security requirements.
	 * 
	 * @param password password to check and set
	 * @throws NumberFormatException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws ConfigException
	 */
	public void setPassword(String password)
			throws NumberFormatException, NoSuchAlgorithmException, InvalidKeySpecException, ConfigException {
		if (password == null)
			throw new IllegalArgumentException("Password is null.");
		if (password.length() > 256)
			throw new IllegalArgumentException(
					"Password is too long. It must be less than or equal to 256 characters.");
		if (password.length() < 8)
			throw new IllegalArgumentException("Password is too short. It must be more than or equal to 8 characters.");

		Pattern p1 = Pattern.compile("[^\\w~!@#$%^&*()_\\-+=]");

		if (p1.matcher(password).find())
			throw new IllegalArgumentException(
					"Password contains illegal characters. Only the following are valid: letters, numbers, and any of !@#$%^&*()-_=+");

		Pattern p2 = Pattern.compile("\\d");
		Pattern p3 = Pattern.compile("[a-z]");
		Pattern p4 = Pattern.compile("[A-Z]");
		Pattern p5 = Pattern.compile("[~!@#$%^&*()_\\-+=]");

		if (!p2.matcher(password).find() || !p3.matcher(password).find() || !p4.matcher(password).find()
				|| !p5.matcher(password).find())
			throw new IllegalArgumentException(
					"Password must contain at least one upper case letter, one lower case letter, one number, and one of the following: !@#$%^&*()-_=+");

		Pattern p6 = Pattern.compile("(.)\\1\\1+");

		if (p6.matcher(password).find())
			throw new IllegalArgumentException("Password cannot have more than 2 consecutive repeating characters.");

		String passSalt = EncryptionService.getSalt();
		String passHash = EncryptionService.hash(password, passSalt);

		if (passHash.equals(this.passHash))
			throw new IllegalArgumentException("Your new password cannot match your old password.");

		this.passSalt = passSalt;
		this.passHash = passHash;
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
		if (fName.length() > 25 || fName.length() == 0) {
			throw new IllegalArgumentException("First Name is invalid, please try again");
		}
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
		if (mName.length() > 25) {
			throw new IllegalArgumentException("Middle Name is invalid, please try again");
		}
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
		if (lName.length() > 25 || lName.length() == 0) {
			throw new IllegalArgumentException("Last Name is invalid, please try again");
		}
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
		if (title.equals("Na") || title.equals("Mr") || title.equals("Mrs") || title.equals("Ms")
				|| title.equals("Mx")) {
			this.title = title;
		} else {
			throw new IllegalArgumentException("Title is invalid, please try again");
		}

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
		if (phone.length() > 15 || phone.length() == 0) {
			throw new IllegalArgumentException("Phone is invalid, please try again");
		}
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
		if (fax.length() > 15) {
			throw new IllegalArgumentException("Fax is invalid, please try again");
		}
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
		if (!language.equals("en") && !language.equals("es"))
			throw new IllegalArgumentException("Invalid Language, please try again");
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
		if (streetAddress.length() > 200 || streetAddress.length() == 0) {
			throw new IllegalArgumentException("Address 1 is invalid, please try again");
		}
		this.streetAddress = streetAddress;
	}

	/**
	 * Returns the 2nd street address.
	 * @return streetAddress2
	 */
	public String getStreetAddress2() {
		return streetAddress2;
	}

	/**
	 * Sets the 2nd street address.
	 * @param streetAddress2 streetAddress2 to set
	 */
	public void setStreetAddress2(String streetAddress2) {
		if (streetAddress2 != null && streetAddress2.length() > 200) {
			throw new IllegalArgumentException("Address 2 is invalid, please try again");
		}
		this.streetAddress2 = streetAddress2;
	}

	/**
	 * Returns the city,
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 * @param city city to set
	 */
	public void setCity(String city) {
		if (city.length() > 100 || city.length() == 0) {
			throw new IllegalArgumentException("City is invalid, please try again");
		}
		this.city = city;
	}

	/**
	 * Returns the province.
	 * @return province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Sets the province.
	 * @param province province to set
	 */
	public void setProvince(String province) {
		if (province.length() > 20 || province.length() == 0) {
			throw new IllegalArgumentException("Province is invalid, please try again");
		}
		this.province = province;
	}

	/**
	 * Returns the country.
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 * @param country country to set
	 */
	public void setCountry(String country) {
		if (country.length() != 2) {
			throw new IllegalArgumentException("Country is invalid, please try again");
		}
		this.country = country;
	}

	/**
	 * Returns the postal code.
	 * @return postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Sets the postal code.
	 * @param postalCode postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		if (postalCode.length() > 10) {
			throw new IllegalArgumentException("Postal Code is invalid, please try again");
		}
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
	 * Returns the verification ID
	 * @return verificationID
	 */
	public String getVerificationID() {
		return verificationID;
	}

	/**
	 * Sets the verification ID.
	 * @param verificationID verificationID to set
	 * @throws UserException
	 */
	public void setVerificationID(String verificationID) throws IllegalArgumentException {
		String UUIDRegex = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";
		if (verificationID != null && verificationID.matches(UUIDRegex))
			this.verificationID = verificationID;
		else if (verificationID == null)
			this.verificationID = null;
		else
			throw new IllegalArgumentException("Invalid Verification ID");
	}

	/**
	 * Returns the last verification attempt.
	 * @return lastVerificationAttempt
	 */
	public Date getLastVerificationAttempt() {
		return lastVerificationAttempt;
	}

	/**
	 * Sets the last verification attempt.
	 * @param lastVerificationAttempt lastVerificationAttempt to set
	 * @throws UserException
	 */
	public void setLastVerificationAttempt(Date lastVerificationAttempt) throws IllegalArgumentException {
		Calendar current = Calendar.getInstance();
		current.add(Calendar.SECOND, 1);
		if (lastVerificationAttempt != null
				&& lastVerificationAttempt
						.after(new Calendar.Builder().setDate(2020, Calendar.JANUARY, 1).build().getTime())
				&& lastVerificationAttempt.before(current.getTime()))
			this.lastVerificationAttempt = lastVerificationAttempt;
		else if (lastVerificationAttempt == null)
			this.lastVerificationAttempt = null;
		else
			throw new IllegalArgumentException("Invalid Verification Date");
	}

	/**
	 * Returns the last verification type.
	 * @return lastVerificationType
	 */
	public int getLastVerificationType() {
		return lastVerificationType;
	}

	/**
	 * Sets the last verification type.
	 * @param lastVerificationType lastVerificationType to set
	 */
	public void setLastVerificationType(int lastVerificationType) {
		this.lastVerificationType = lastVerificationType;
	}

	/**
	 * Copies the attributes of this object into a new object that is then returning to the
	 * calling method.
	 * @return UserBean copy of this object
	 */
	public UserBean copy() {
		UserBean output = new UserBean( email, title, fName, mName, lName, phone, fax, streetAddress, streetAddress2, city, province, country, postalCode, language );
		
		return output;
	}
}
