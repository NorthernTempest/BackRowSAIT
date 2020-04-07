package databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.TaxReturn;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the tax_return table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class TaxReturnDB {

	/**
	 * Establishes a connection with the database and inserts the TaxReturn object passed
	 * into this method into the tax_return table.
	 * @param taxReturn TaxReturn to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(TaxReturn taxReturn) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "INSERT INTO tax_return (email, status, cost, year) "
					+ "VALUES (?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, taxReturn.getEmail());
			ps.setString(2, taxReturn.getStatus());
			ps.setDouble(3, taxReturn.getCost());
			ps.setInt(4, taxReturn.getYear());

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
	 * Establishes a connection with the database and updates the TaxReturn in the tax_return
	 * table that shares a Primary Key with the TaxReturn being passed into this method.
	 * All values of the TaxReturn in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param taxReturn TaxReturn to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean update(TaxReturn taxReturn) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "UPDATE tax_return SET household_id = ?, status = ?, cost = ?"
					+ " WHERE email = ? AND year = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setInt(1, taxReturn.getHouseholdID());
			ps.setString(2, taxReturn.getStatus());
			ps.setDouble(3, taxReturn.getCost());
			ps.setString(4, taxReturn.getEmail());
			ps.setInt(5, taxReturn.getYear());

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
	 * Establishes a connection with the database and removes the TaxReturn from the
	 * tax_return table that has a Primary Key matching the taxReturnID being passed
	 * into this method.
	 * @param taxReturnID taxReturnID of the TaxReturn to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(String email, int year) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "DELETE FROM tax_return WHERE email = ? AND year = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, email);
			ps.setInt(2, year);

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
	 * Establishes a connection with the database and selects the TaxReturn in the
	 * tax_return table that has a Primary Key matching the taxReturnID being passed
	 * into this method.
	 * @param taxReturnID taxReturnID of the TaxReturn to retrieve from the database
	 * @return TaxReturn that contains the information of the requested TaxReturn
	 */
	public static TaxReturn get(String email, int year) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		TaxReturn taxReturn = null;

		try {

			String preparedQuery = "SELECT * FROM tax_return WHERE email = ? AND year = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, email);
			ps.setInt(2, year);

			rs = ps.executeQuery();

			if (rs.next()) {

				taxReturn = new TaxReturn(rs.getString("email"), rs.getString("status"), rs.getInt("year"), rs.getDouble("cost"));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return taxReturn;
	}
	
	/**
	 * Establishes a connection with the database and selects the TaxReturns in the
	 * tax_return table that have a user matching the User being passed
	 * into this method.
	 * @param user the User of the TaxReturn to retrieve from the database
	 * @return TaxReturn that contains the information of the requested TaxReturn
	 */
	public static ArrayList<TaxReturn> getByUser(String email) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<TaxReturn> taxReturns = new ArrayList<>();
		TaxReturn taxReturn = null;
		
		try {
			
			String preparedQuery = "SELECT * FROM tax_return WHERE email = ?";
			
			PreparedStatement ps = connection.prepareStatement(preparedQuery);
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				taxReturn = new TaxReturn(rs.getString("email"), rs.getString("status"), rs.getInt("year"), rs.getDouble("cost"));
				taxReturns.add(taxReturn);
			}
		}
		
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
		finally {
			
			pool.closeConnection(connection);
		}
		
		return taxReturns;
	}
	
	/**
	 * Establishes a connection with the database and selects all the TaxReturns
	 * within the tax_return table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<TaxReturn> containing all of the TaxReturns from the database
	 */
	public static ArrayList<TaxReturn> getAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<TaxReturn> taxReturns = new ArrayList<>();

		try {

			String preparedQuery = "SELECT * FROM tax_return";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			rs = ps.executeQuery();

			while (rs.next()) {

				taxReturns.add(new TaxReturn(rs.getString("email"), rs.getString("status"), rs.getInt("year"), rs.getDouble("cost")));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return taxReturns;
	}
}
