package remoteTaxPreparerBackend;

import java.util.Date;

/**
 * 
 * Class Description: User class that defines which attributes every type on user has.
 *
 * @author Tristen Kreutz
 *
 */
public class User {
	
	private String 	username,
					email,
					phone,
					passHash,
					title,
					fname,
					lname,
					faxNum,
					language,
					address;
	
	private int permissionLevel;
	private long id;
	private Boolean active;
	private Date creationDate;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPassHash() {
		return passHash;
	}
	
	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFname() {
		return fname;
	}
	
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getLname() {
		return lname;
	}
	
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public String getFaxNum() {
		return faxNum;
	}
	
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getPermissionLevel() {
		return permissionLevel;
	}
	
	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Boolean isActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void checkPass()
	{
		
	}
}
