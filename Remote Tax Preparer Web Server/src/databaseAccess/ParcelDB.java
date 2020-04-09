package databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import domain.Parcel;
import exception.ConfigException;
import manager.UserManager;
import util.cesar.Debugger;

/**
 * 
 * Class Description: Class that establishes a connection and communicates
 * directly with the parcel table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class ParcelDB {

	final static String TAX_PREPARER = "tax_preparer";

	/**
	 * Establishes a connection with the database and inserts the Parcel object
	 * passed into this method into the parcel table.
	 * 
	 * @param parcel Parcel to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(Parcel parcel) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;
		
		try {

			String preparedQuery = "INSERT INTO parcel (parcel_id, subject, message, sender, receiver, date_sent, expiration_date, tax_return_year, requires_signature)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, parcel.getParcelID());
			ps.setString(2, parcel.getSubject());
			ps.setString(3, parcel.getMessage());
			ps.setString(4, parcel.getSender());
			ps.setString(5, parcel.getReceiver());
			ps.setTimestamp(6, new Timestamp(parcel.getDateSent().getTime()));
			ps.setTimestamp(7, new Timestamp(parcel.getExpirationDate().getTime()));
			ps.setInt(8, parcel.getTaxReturnYear());
			ps.setString(9, parcel.isRequiresSignature()?"T":"F");

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
	 * Establishes a connection with the database and updates the Parcel in the
	 * parcel table that shares a Primary Key with the Parcel being passed into this
	 * method. All values of the Parcel in the database will be updated with the
	 * values of the object being passed assuming constraints are not violated.
	 * 
	 * @param parcel Parcel to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean update(Parcel parcel) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "UPDATE parcel subject = ?, message = ?, sender = ?, "
					+ "receiver = ?, date_sent = ?, expiration_date = ?, tax_return_year = ?, requires_signature = ? WHERE parcel_id = ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, parcel.getSubject());
			ps.setString(2, parcel.getMessage());
			ps.setString(3, parcel.getSender());
			ps.setString(4, parcel.getReceiver());
			ps.setTimestamp(5, new Timestamp(parcel.getDateSent().getTime()));
			ps.setTimestamp(6, new Timestamp(parcel.getExpirationDate().getTime()));
			ps.setInt(7, parcel.getTaxReturnYear());
			ps.setString(8, parcel.isRequiresSignature()?"T":"F");
			ps.setString(9, parcel.getParcelID());

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
	 * Establishes a connection with the database and removes the Parcel from the
	 * parcel table that has a Primary Key matching the parcelID being passed into
	 * this method.
	 * 
	 * @param parcelID parcelID of the Parcel to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(String parcelID) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "DELETE FROM parcel WHERE parcel_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, parcelID);

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
	 * Establishes a connection with the database and selects the Parcel in the
	 * parcel table that has a Primary Key matching the parcelID being passed into
	 * this method.
	 * 
	 * @param parcelID parcelID of the Parcel to retrieve from the database
	 * @return Parcel that contains the information of the requested Parcel
	 */
	public static Parcel get(String parcelID) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		Parcel parcel = null;

		try {

			String preparedQuery = "SELECT * FROM parcel WHERE parcel_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, parcelID);

			rs = ps.executeQuery();

			if (rs.next()) {

				boolean requireSignature = false;
				if(rs.getString("requires_signature").equals("T")) {
					requireSignature = true;
				}

				parcel = new Parcel(rs.getString("parcel_id"), rs.getString("subject"), rs.getString("message"),
						rs.getString("sender"), rs.getString("receiver"), rs.getTimestamp("date_sent"),
						rs.getTimestamp("expiration_date"), rs.getInt("tax_return_year"),
						DocumentDB.getByParcelID(rs.getString("parcel_id")), requireSignature);
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return parcel;
	}

	/**
	 * Establishes a connection with the database and selects all the Parcels within
	 * the parcel table. It then adds them to a newly created ArrayList and returns
	 * that to the calling object.
	 * 
	 * @return ArrayList<Parcel> containing all of the Parcels from the database
	 */
	public static ArrayList<Parcel> getAll() {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<Parcel> parcels = new ArrayList<>();

		try {

			String preparedQuery = "SELECT * FROM parcel";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			rs = ps.executeQuery();

			while (rs.next()) {

				boolean requireSignature = false;
				if(rs.getString("requires_signature").equals("T")) {
					requireSignature = true;
				}

				parcels.add(new Parcel(rs.getString("parcel_id"), rs.getString("subject"), rs.getString("message"),
						rs.getString("sender"), rs.getString("receiver"), rs.getTimestamp("date_sent"),
						rs.getTimestamp("expiration_date"), rs.getInt("tax_return_year"),
						DocumentDB.getByParcelID(rs.getString("parcel_id")), requireSignature));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return parcels;
	}

	/**
	 * Method that, using the supplied parameters, retrieves all parcels in the
	 * database with that information and returns a list to the calling program.
	 * 
	 * @param parcelID parcel ID to search
	 * @param sender sender to search
	 * @param receiver receiver to search
	 * @param dateSent date sent to search
	 * @param year year to search
	 * @return ArrayList<Parcel> containing all relevant Parcels from the database
	 */
	public static ArrayList<Parcel> getParcelsByParameter(String parcelID, String sender, String receiver, Date dateSent,
			int year) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs = null;
		Parcel parcel = null;
		ArrayList<Parcel> parcels = new ArrayList<>();
		String preparedQuery;
		
		try {

			if (parcelID == null && sender == null && receiver == null && dateSent == null && year < 0) {
				preparedQuery = "SELECT * FROM parcel";
				PreparedStatement ps = connection.prepareStatement(preparedQuery);
				rs = ps.executeQuery();
			}

			else {

				ArrayList<Object> parameters = new ArrayList<>();
				int count = 0;
				preparedQuery = "SELECT * FROM parcel WHERE ";

				if (parcelID != null) {
					preparedQuery += "parcel_id = ? ";
					parameters.add(parcelID);
					count++;
				}

				if (sender != null) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "sender = ? ";
					parameters.add(sender);
					count++;
				}

				if (receiver != null && !receiver.equals(TAX_PREPARER)) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "receiver = ? ";
					parameters.add(receiver);
					count++;
				}
				
				if (receiver != null && receiver.equals(TAX_PREPARER)) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "receiver IS NULL ";
					count++;
				}

				if (dateSent != null) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "dateSent = ? ";
					parameters.add(dateSent.toString());
				}

				if (year >= 0) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "year = ? ";
					parameters.add(year);
				}

				PreparedStatement ps = connection.prepareStatement(preparedQuery);

				for (int i = 0; i < parameters.size(); i++) {

					ps.setString(i + 1, (String) parameters.get(i));
				}

				rs = ps.executeQuery();
			}

			while (rs.next()) {

				boolean requireSignature = false;
				if(rs.getString("requires_signature").equals("T")) {
					requireSignature = true;
				}

				parcel = new Parcel(rs.getString("parcel_id"), rs.getString("subject"), rs.getString("message"),
						rs.getString("sender"), rs.getString("receiver"), rs.getTimestamp("date_sent"),
						rs.getTimestamp("expiration_date"), rs.getInt("tax_return_year"),
						DocumentDB.getByParcelID(rs.getString("parcel_id")), requireSignature);
				parcels.add(parcel);
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return parcels;
	}

	/**
	 * Method that, using the supplied email and parcelID, returns true if the
	 * associated user is allowed to view the aprcel associated with the ID
	 * 
	 * @param email the email to check against
	 * @param parcelID the parcelID to check against
	 * @return true if email is allowed to view this parcel. false if not.
	 * @throws ConfigException if config file is not found or cannot be opened
	 */
	public static boolean isVisibleToUser(String email, String parcelID) throws ConfigException {
		Parcel parcel = get(parcelID);
		
		Debugger.log(parcelID);
		
		if(parcel != null && parcel.getReceiver() != null && parcel.getReceiver().equals(email)) {
			return true;
		} else if(parcel != null && parcel.getReceiver() == null && UserManager.getUser(email).getPermissionLevel() >= 2) {
			return true;
		} else if(parcel.getSender() != null && parcel.getSender().equals(email)) {
			return true;
		} else
		return false;
	}
}
