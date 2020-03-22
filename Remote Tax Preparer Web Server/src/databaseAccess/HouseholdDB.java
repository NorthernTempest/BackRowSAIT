package databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Household;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the household table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class HouseholdDB {
	
	/**
	 * Establishes a connection with the database and inserts the Household object passed
	 * into this method into the household table.
	 * @param household Household to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(Household household) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "INSERT INTO household (household_name)"
					+ "VALUES (?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, household.getHouseholdName());

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
	 * Establishes a connection with the database and updates the Household in the household
	 * table that shares a Primary Key with the Household being passed into this method.
	 * All values of the Household in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param household Household to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean update(Household household) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "UPDATE household household_name = ? "
					+ " WHERE household_id = ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, household.getHouseholdName());
			ps.setInt(2, household.getHouseholdID());

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
	 * Establishes a connection with the database and removes the Household from the
	 * household table that has a Primary Key matching the householdID being passed
	 * into this method.
	 * @param householdID householdID of the Household to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(int householdID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "DELETE FROM household WHERE household_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setInt(1, householdID);

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
	 * Establishes a connection with the database and selects the Household in the
	 * household table that has a Primary Key matching the householdID being passed
	 * into this method.
	 * @param householdID householdID of the Household to retrieve from the database
	 * @return Household that contains the information of the requested Household
	 */
	public static Household get(int householdID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		Household household = null;

		try {

			String preparedQuery = "SELECT * FROM household WHERE household_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setInt(1, householdID);

			rs = ps.executeQuery();

			if (rs.next()) {

				household = new Household(householdID, rs.getString("household_name"));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return household;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Households
	 * within the household table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Household> containing all of the Households from the database
	 */
	public static ArrayList<Household> getAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<Household> households = new ArrayList<>();

		try {

			String preparedQuery = "SELECT * FROM household";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			rs = ps.executeQuery();

			while (rs.next()) {

				households.add(new Household(rs.getInt("household_id"), rs.getString("household_name")));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return households;
	}
}
