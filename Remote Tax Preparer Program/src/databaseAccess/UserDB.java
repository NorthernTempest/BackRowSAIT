package databaseAccess;

import java.util.ArrayList;

import domain.User;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the user table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class UserDB {

	/**
	 * Establishes a connection with the database and inserts the User object passed
	 * into this method into the user table.
	 * @param user User to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(User user) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and updates the User in the user
	 * table that shares a Primary Key with the User being passed into this method.
	 * All values of the User in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param user User to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(User user) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the User from the
	 * user table that has a Primary Key matching the email being passed
	 * into this method.
	 * @param email email of the User to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(String email) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the User in the
	 * user table that has a Primary Key matching the email being passed
	 * into this method.
	 * @param email email of the User to retrieve from the database
	 * @return User that contains the information of the requested User
	 */
	public User get(String email) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Users
	 * within the user table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<User> containing all of the Users from the database
	 */
	public ArrayList<User> getAll() {
		return null;
	}
}
