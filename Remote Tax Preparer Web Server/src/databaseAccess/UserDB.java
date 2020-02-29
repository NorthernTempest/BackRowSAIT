package databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.User;
import manager.LogEntryManager;

/**
 * 
 * Class Description: Class that establishes a connection and communicates
 * directly with the user table in the database.
 *
 * @author Tristen Kreutz, Cesar Guzman, Jesse Goerzen
 *
 */
public final class UserDB {

	/**
	 * Establishes a connection with the database and inserts the User object passed
	 * into this method into the user table.
	 * 
	 * @param user User to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(User user) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "INSERT INTO user "
					+ "values (email, "
							+ "f_name, "
							+ "l_name, "
							+ "permission_level, "
							+ "phone, "
							+ "pass_hash, "
							+ "title, "
							+ "creation_date, "
							+ "fax, "
							+ "active, "
							+ "verified, "
							+ "verification_id, "
							+ "last_verification_attempt, "
							+ "last_verification_type) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, user.getEmail());
			ps.setString(2, user.getFName());
			ps.setString(3, user.getLName());
			ps.setInt(4, user.getPermissionLevel());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getPassHash());
			ps.setString(7, user.getTitle());
			ps.setDate(8, new java.sql.Date(user.getCreationDate().getTime()));
			ps.setString(9, user.getFax());
			ps.setString(10, user.isActive()?"T":"F");
			ps.setString(11, user.isVerified()?"T":"F");
			ps.setString(12, user.getVerificationID());
			ps.setDate(13, new java.sql.Date( user.getLastVerificationAttempt().getTime() ));
			ps.setInt(14, user.getLastVerificationType());

			rows = ps.executeUpdate();
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		if (rows > 0) {

			return true;
		}

		return false;
	}

	/**
	 * Establishes a connection with the database and updates the User in the user
	 * table that shares a Primary Key with the User being passed into this method.
	 * All values of the User in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * 
	 * @param user User to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean update(User user) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "UPDATE user "
					+ "SET f_name = ?, "
					+ "l_name = ?, "
					+ "permission_level = ?, "
					+ "phone = ?, "
					+ "pass_hash = ?, "
					+ "title = ?, "
					+ "creation_date = ?, "
					+ "fax = ?, "
					+ "active = ?, "
					+ "verified = ?, "
					+ "verification_id = ?, "
					+ "last_verification_attempt = ?, "
					+ "last_verification_type = ? "
					+ "WHERE email = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, user.getFName());
			ps.setString(2, user.getLName());
			ps.setInt(3, user.getPermissionLevel());
			ps.setString(4, user.getPhone());
			ps.setString(5, user.getPassHash());
			ps.setString(6, user.getTitle());
			try {
				ps.setDate(7, new java.sql.Date(user.getCreationDate().getTime()));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			ps.setString(8, user.getFax());
			ps.setString(9, user.isActive()?"T":"F");
			ps.setString(10, user.isActive()?"T":"F");
			ps.setString(11, user.getVerificationID());
			ps.setDate(12, new java.sql.Date(user.getLastVerificationAttempt().getTime()));
			ps.setString(11, user.getEmail());

			rows = ps.executeUpdate();
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		if (rows > 0) {

			return true;
		}

		return false;
	}

	/**
	 * Establishes a connection with the database and removes the User from the user
	 * table that has a Primary Key matching the email being passed into this
	 * method.
	 * 
	 * @param email email of the User to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(String email) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "UPDATE user SET active = ? WHERE email = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setBoolean(1, false);
			ps.setString(2, email);

			rows = ps.executeUpdate();
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		if (rows > 0) {

			return true;
		}

		return false;
	}

	/**
	 * Establishes a connection with the database and selects the User in the user
	 * table that has a Primary Key matching the email being passed into this
	 * method.
	 * 
	 * @param email email of the User to retrieve from the database
	 * @return User that contains the information of the requested User
	 */
	public static User get(String email) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		User user = null;

		try {

			String preparedQuery = "SELECT * FROM user WHERE email = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, email);

			rs = ps.executeQuery();

			if (rs.next()) {
				user = createUser(rs);
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return user;
	}

	/**
	 * Establishes a connection with the database and selects all the Users within
	 * the user table. It then adds them to a newly created ArrayList and returns
	 * that to the calling object.
	 * 
	 * @return ArrayList<User> containing all of the Users from the database
	 */
	public static ArrayList<User> getAll() {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<User> users = new ArrayList<>();

		try {

			String preparedQuery = "SELECT * FROM user";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			rs = ps.executeQuery();

			while (rs.next()) 
				users.add(createUser(rs));
		}

		catch (SQLException e) {
			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return users;
	}
	
	public static User getByVerificationID(String verificationID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet set = null;
		
		User output = null;
		
		try {
			String query = "SELECT * FROM user WHERE verification_id = ?";
			
			PreparedStatement statement = connection.prepareStatement( query );
			
			statement.setString(1, verificationID);
			
			set = statement.executeQuery();
			
			if( set.next() )
				output = createUser(set);
				
		} catch(SQLException e) {
			LogEntryManager.logError(null, e, null);
			System.out.println(e);
		} finally {
			pool.closeConnection(connection);
		}
		return output;
	}
	
	/**
	 * 
	 * @param set
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 */
	private static User createUser(ResultSet set) throws IllegalArgumentException, SQLException {
		User output = new User(set.getString("email"),
				set.getString("f_name"),
				set.getString("m_name"),
				set.getString("l_name"),
				set.getInt("permission_level"),
				set.getString("phone"),
				set.getString("pass_hash"),
				set.getString("pass_salt"),
				set.getString("title"),
				new java.util.Date(set.getDate("creation_date").getTime()),
				set.getString("fax"),
				set.getString("active").equals("T"),
				set.getString("street_address_1"),
				set.getString("street_address_2"),
				set.getString("city"),
				set.getString("province"),
				set.getString("country"),
				set.getString("postal_code"),
				set.getString("language"),
				set.getString("verified").equals("T"),
				set.getString("verification_id"),
				new java.util.Date(set.getDate("last_verification_attempt").getTime()),
				set.getInt("last_verification_type"));
		
		return output;
	}
}
