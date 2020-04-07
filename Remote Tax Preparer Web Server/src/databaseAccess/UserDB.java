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

			String preparedQuery = "INSERT INTO user ("
					+ "email, "
					+ "f_name, "
					+ "m_name, "
					+ "l_name, "
					+ "permission_level, "
					+ "phone, "
					+ "pass_hash, "
					+ "pass_salt, "
					+ "title, "
					+ "creation_date, "
					+ "fax, "
					+ "active, "
					+ "street_address_1, "
					+ "street_address_2, "
					+ "city, "
					+ "province, "
					+ "country, "
					+ "postal_code, "
					+ "language, "
					+ "verified, "
					+ "verification_id, "
					+ "last_verification_attempt, "
					+ "last_verification_type) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			rows = prepare(user, ps, false).executeUpdate();
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
					+ "SET "
					+ "f_name = ?, "
					+ "m_name = ?, "
					+ "l_name = ?, "
					+ "permission_level = ?, "
					+ "phone = ?, "
					+ "pass_hash = ?, "
					+ "pass_salt = ?, "
					+ "title = ?, "
					+ "creation_date = ?, "
					+ "fax = ?, "
					+ "active = ?, "
					+ "street_address_1 = ?, "
					+ "street_address_2 = ?, "
					+ "city = ?, "
					+ "province = ?, "
					+ "country = ?, "
					+ "postal_code = ?, "
					+ "language = ?, "
					+ "verified = ?, "
					+ "verification_id = ?, "
					+ "last_verification_attempt = ?, "
					+ "last_verification_type = ? "
					+ "WHERE email = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);
			
			rows = prepare(user, ps, true).executeUpdate();
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

			ps.setString(1, "F");
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
	
	/**
	 * Establishes a connection with the database and retrieves the row from the
	 * user table that has a verificationID matching the one supplies by the
	 * calling method.
	 * @param verificationID verification ID to search
	 * @return User user with matching verification ID
	 */
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
	 * Method that takes in a result set and retrieves the appropriate values to
	 * construct a User object.
	 * @param set result set to retrieve values from
	 * @return User user that was created using the values from the result set
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
				set.getTimestamp("creation_date") != null ? new java.util.Date(set.getTimestamp("creation_date").getTime()) : null,
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
				set.getTimestamp("last_verification_attempt") != null ? new java.util.Date(set.getTimestamp("last_verification_attempt").getTime()) : null,
				set.getInt("last_verification_type"));
		
		return output;
	}
	
	/**
	 * Method that takes a User, PreparedStatement, and an isUpdate boolean to set up
	 * the PreparedStatement using the values from the supplied User to utilize in
	 * inserting or updating a User in the database. The isUpdate boolean determines
	 * whether the operation is an insert or update.
	 * @param user user to insert or update
	 * @param statement prepared statement for JPA usage
	 * @param isUpdate boolean defining whether it is an insert or update
	 * @return PreparedStatement that is now ready to use with the database
	 * @throws SQLException
	 */
	private static PreparedStatement prepare(User user, PreparedStatement statement, boolean isUpdate) throws SQLException {
		int add = isUpdate?0:1;
		statement.setString(isUpdate?23:1, user.getEmail());
		statement.setString(1 + add, user.getFName());
		statement.setString(2 + add, user.getMName());
		statement.setString(3 + add, user.getLName());
		statement.setInt(4 + add, user.getPermissionLevel());
		statement.setString(5 + add, user.getPhone());
		statement.setString(6 + add, user.getPassHash());
		statement.setString(7 + add, user.getPassSalt());
		statement.setString(8 + add, user.getTitle());
		statement.setTimestamp(9 + add, user.getCreationDate() != null ? new java.sql.Timestamp(user.getCreationDate().getTime()) : null);
		statement.setString(10 + add, user.getFax());
		statement.setString(11 + add, user.isActive()?"T":"F");
		statement.setString(12 + add, user.getStreetAddress());
		statement.setString(13 + add, user.getStreetAddress2());
		statement.setString(14 + add, user.getCity());
		statement.setString(15 + add, user.getProvince());
		statement.setString(16 + add, user.getCountry());
		statement.setString(17 + add, user.getPostalCode());
		statement.setString(18 + add, user.getLanguage());
		statement.setString(19 + add, user.isVerified()?"T":"F");
		statement.setString(20 + add, user.getVerificationID());
		statement.setTimestamp(21 + add, user.getLastVerificationAttempt() != null ? new java.sql.Timestamp( user.getLastVerificationAttempt().getTime() ) : null);
		statement.setInt(22 + add, user.getLastVerificationType());
		
		return statement;
	}
}
