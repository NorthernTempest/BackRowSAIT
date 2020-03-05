package domain;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import exception.ConfigException;
import exception.UserException;
import service.EncryptionService;

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
	public static final int VERIFY_TYPE_NONE = -1;

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
			String password, String title, Date creationDate, String fax, boolean active,
			String streetAddress, String streetAddress2, String city, String province, String country,
			String postalCode, String language, boolean verified, String verificationID, Date lastVerificationAttempt,
			int lastVerificationType) throws IllegalArgumentException, ConfigException, NoSuchAlgorithmException, InvalidKeySpecException {

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
		if (email.length() > 100||email.length()==0) {
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
	
	public void setPassword(String password) throws NumberFormatException, NoSuchAlgorithmException, InvalidKeySpecException, ConfigException {
		if (password == null)
			throw new IllegalArgumentException("Password is null.");
		if (password.length() > 256)
			throw new IllegalArgumentException("Password is too long. It must be less than or equal to 256 characters.");
		if (password.length() < 8)
			throw new IllegalArgumentException("Password is too short. It must be more than or equal to 8 characters.");
		
		Pattern p1 = Pattern.compile("[^\\w~!@#$%^&*()_\\-+=]");
		
		if (p1.matcher(password).find())
			throw new IllegalArgumentException("Password contains illegal characters. Only letters, numbers, and top row punctuation are valid inputs.");

		Pattern p2 = Pattern.compile("\\d");
		Pattern p3 = Pattern.compile("\\p{Alpha}");
		Pattern p4 = Pattern.compile("[~!@#$%^&*()_\\-+=]");
		
		if (!p2.matcher(password).find() || !p3.matcher(password).find() || !p4.matcher(password).find())
			throw new IllegalArgumentException("Password must contain at least one each of letters, digits, and top row punctuation.");
		
		Pattern p5 = Pattern.compile("(.)\\1\\1+");
		
		if (p5.matcher(password).find())
			throw new IllegalArgumentException("Password cannot have more than 2 consecutive repeating characters.");
		
		String passSalt = EncryptionService.getSalt();
		String passHash = EncryptionService.hash(password, passSalt);
		
		if(passHash.equals(this.passHash))
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
		if (fName.length() > 25||fName.length()==0) {
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
		if (lName.length() > 25||lName.length()==0) {
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
		if (title.equals("N/A") || title.equals("Mr") || title.equals("Mrs") || title.equals("Ms")|| title.equals("Mx")) {
			throw new IllegalArgumentException("Title is invalid, please try again");
		}
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
		if (phone.length() > 15||phone.length()==0) {
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
		if (streetAddress.length() > 200||streetAddress.length()==0) {
			throw new IllegalArgumentException("Address 1 is invalid, please try again");
		}
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
		if (streetAddress2 != null && streetAddress2.length() > 200) {
			throw new IllegalArgumentException("Address 2 is invalid, please try again");
		}
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
		if (city.length() > 100||city.length()==0) {
			throw new IllegalArgumentException("City is invalid, please try again");
		}
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
		if (province.length() == 2) {
			throw new IllegalArgumentException("Province is invalid, please try again");
		}
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
		if (country.length() == 2) {
			throw new IllegalArgumentException("Country is invalid, please try again");
		}
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
		if (verificationID != null && verificationID.matches(UUIDRegex))
			this.verificationID = verificationID;
		else if( verificationID == null )
			this.verificationID = null;
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
		if (lastVerificationAttempt != null && lastVerificationAttempt.after(new Calendar.Builder().setDate(2020, Calendar.JANUARY, 1).build().getTime())
				&& lastVerificationAttempt.before(current.getTime()))
			this.lastVerificationAttempt = lastVerificationAttempt;
		else if (lastVerificationAttempt == null)
			this.lastVerificationAttempt = null;
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
