package databaseAccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Household;
import domain.Payment;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the payment table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class PaymentDB {

	/**
	 * Establishes a connection with the database and inserts the Payment object passed
	 * into this method into the payment table.
	 * @param payment Payment to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(Payment payment) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "INSERT INTO payment (email, year, payment_type, amount, date)"
					+ "VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, payment.getEmail());
			ps.setInt(2, payment.getYear());
			ps.setString(3, payment.getPaymentType());
			ps.setDouble(4, payment.getAmount());
			ps.setDate(5, (Date) payment.getDate());

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
	 * Establishes a connection with the database and removes the Payment from the
	 * payment table that has a Primary Key matching the paymentID being passed
	 * into this method.
	 * @param paymentID paymentID of the Payment to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(String paymentID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "DELETE FROM payment WHERE payment_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, paymentID);

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
	 * Establishes a connection with the database and selects the Payment in the
	 * payment table that has a Primary Key matching the paymentID being passed
	 * into this method.
	 * @param paymentID paymentID of the Payment to retrieve from the database
	 * @return Payment that contains the information of the requested Payment
	 */
	public static Payment get(String paymentID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		Payment payment = null;

		try {

			String preparedQuery = "SELECT * FROM payment WHERE payment_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, paymentID);

			rs = ps.executeQuery();

			if (rs.next()) {

				payment = new Payment(paymentID, rs.getString("payment_name"), rs.getInt("year"),
										rs.getString("payment_type"), rs.getDouble("amount"), rs.getDate("date"));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return payment;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Payments
	 * within the payment table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Payment> containing all of the Payments from the database
	 */
	public static ArrayList<Payment> getAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<Payment> payments = new ArrayList<>();

		try {

			String preparedQuery = "SELECT * FROM payment";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			rs = ps.executeQuery();

			while (rs.next()) {

				payments.add(new Payment(rs.getString("paymentID"), rs.getString("payment_name"), rs.getInt("year"),
						rs.getString("payment_type"), rs.getDouble("amount"), rs.getDate("date")));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return payments;
	}
}
