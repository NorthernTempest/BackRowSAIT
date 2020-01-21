package managers;

import java.util.ArrayList;
import java.util.Properties;

import domain.User;

/**
 * 
 * Class Description: 	Class that communicates with the UserDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class UserManager {

	/**
	 * Takes the User object passed into the method and calls the insert method
	 * of the UserDB, passing the User.
	 * @param user User to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(User user) {
		return false;
	}
	
	/**
	 * Takes the User object passed into the method and calls the update method
	 * of the UserDB, passing the User.
	 * @param user User in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(User user) {
		return false;
	}
	
	/**
	 * Takes the email passed into the method and calls the delete method
	 * of the UserDB, passing the email.
	 * @param email email of the User in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(String email) {
		return false;
	}
	
	/**
	 * Takes the email passed into the method and calls the get method
	 * of the UserDB, passing the email.
	 * @param email email of the User in the database to retrieve
	 * @return User containing the information of the requested User
	 */
	public User get(String email) {
		return null;
	}
	
	/**
	 * Calls the getAll method of the UserDB.
	 * @return ArrayList<User> containing all the Users in the database
	 */
	public ArrayList<User> getAll() {
		return null;
	}
	
	/**
	 * Takes a User object and a password String to check against what exists
	 * in the user table to see if the information matches for
	 * authentication purposes
	 * @param user User to check
	 * @param password password
	 * @return boolean based on whether or not the information matches
	 */
	public boolean authenticate(User user, String password) {
		return false;
	}
	
	/**
	 * Takes an email, password, and ip to be utilized in logging in to the
	 * website assuming the password passed matches the User tied to the email
	 * passed.
	 * @param email email of User to login
	 * @param password password
	 * @param ip ip of login computer
	 * @return boolean based on whether or not the User can successfully login
	 */
	public boolean login(String email, String password, String ip) {
		return false;
	}
	
	/**
	 * Takes an email for a User and retrieves all account information for
	 * that User that is stored in the user table of the database.
	 * @param email email of User to retrieve information for
	 * @return Properties objects containing the account information of the request User
	 */
	public Properties getAccountInfo(String email) {
		return null;
	}
	
	/**
	 * Utilizing the parameters that have been passed, this method verifies with the
	 * User database to check whether or not the user is currently logged in somewhere.
	 * @param email email to check
	 * @param password password to check
	 * @param ip ip to check
	 * @return boolean
	 */
	public boolean loggedIn(String email, String password, String ip) {
		return false;
	}
	
	/**
	 * Method to generate and return a String value containing a recovery key for the
	 * user that is utilized in identity verification for recovering an account.
	 * @return recovery key
	 */
	public String generateRecoveryKey() {
		return null;
	}
	
	/**
	 * Sends an email to the specified user containing the generated recovery key.
	 * @return boolean
	 */
	public boolean sendRecoveryEmail() {
		return false;
	}
	
	/**
	 * Takes the recovery key that was entered by the user and compares it to the key
	 * that was generated and sent to their email.
	 * @return boolean
	 */
	public boolean verifyRecoveryKey() {
		return false;
	}
}
