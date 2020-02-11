package domain;

import java.util.Date;

/**
 * 
 * Class Description: User class that defines which attributes every type of
 * user has.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public class User {

	private String email;

	private String fName;

	private String mName;

	private String lName;

	private int permissionLevel;

	private String phone;

	private String passHash;

	private String passSalt;

	private int ID;

	private String title;

	private Date creationDate;

	private String fax;

	private boolean active;

	private String language;

	private String address;

	private boolean verified;

	private int verificationID;

	private boolean recovering;

	private final int DEFAULT = 0;

	private final int USER = 1;

	private final int TAX_PREPARER = 2;

	private final int ADMIN = 3;

	private final int SYSADMIN = 4;
	
	public User() {
		
	}

	public User(String email, String f_name, String l_name, String phone, String pass_hash, String pass_salt) {
		
		setEmail(email);
		setfName(f_name);
		setlName(l_name);
		setPhone(phone);
		setPassHash(pass_hash);
		setPassSalt(pass_salt);
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the mName
	 */
	public String getmName() {
		return mName;
	}

	/**
	 * @param mName the mName to set
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
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
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
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
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the verificationID
	 */
	public int getVerificationID() {
		return verificationID;
	}

	/**
	 * @param verificationID the verificationID to set
	 */
	public void setVerificationID(int verificationID) {
		this.verificationID = verificationID;
	}

	/**
	 * @return the recovering
	 */
	public boolean isRecovering() {
		return recovering;
	}

	/**
	 * @param recovering the recovering to set
	 */
	public void setRecovering(boolean recovering) {
		this.recovering = recovering;
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
			this.permissionLevel = DEFAULT;
		}
	}

	/**
	 * @return the dEFAULT
	 */
	public int getDEFAULT() {
		return DEFAULT;
	}

	/**
	 * @return the uSER
	 */
	public int getUSER() {
		return USER;
	}

	/**
	 * @return the tAX_PREPARER
	 */
	public int getTAX_PREPARER() {
		return TAX_PREPARER;
	}

	/**
	 * @return the aDMIN
	 */
	public int getADMIN() {
		return ADMIN;
	}

	/**
	 * @return the sYSADMIN
	 */
	public int getSYSADMIN() {
		return SYSADMIN;
	}

}
