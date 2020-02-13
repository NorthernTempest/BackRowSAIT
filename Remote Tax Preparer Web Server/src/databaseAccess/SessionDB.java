package databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Session;

/**
 * 
 * Class Description: Class that establishes a connection and communicates
 * directly with the session table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public final class SessionDB {

	/**
	 * Establishes a connection with the database and inserts the Session object
	 * passed into this method into the session table.
	 * 
	 * @param session Session to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(Session session) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		String statement = "INSERT INTO session(session_id, email, timeout) VALUES(?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(statement);

			ps.setString(1, session.getSessionID());
			ps.setString(2, session.getEmail());
			ps.setTimestamp(3, new java.sql.Timestamp( session.getTimeout().getTime() ));

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			pool.closeConnection(connection);
		}
		if (rows == 1)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	public static boolean update(Session session) {
		// TODO
		return false;
	}

	/**
	 * Establishes a connection with the database and removes the Session from the
	 * session table that has a Primary Key matching the sessionID being passed into
	 * this method.
	 * 
	 * @param sessionID sessionID of the Session to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(String sessionID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "DELETE session where sessionID = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, sessionID);

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(connection);
		}

		if (rows == 1)
			return true;
		else
			return false;
	}

	/**
	 * Establishes a connection with the database and selects the Session in the
	 * session table that has a Primary Key matching the sessionID being passed into
	 * this method.
	 * 
	 * @param sessionID sessionID of the Session to retrieve from the database
	 * @return Session that contains the information of the requested Session
	 */
	public static Session get(String sessionID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		Session session = null;

		try {

			String preparedQuery = "SELECT * FROM session WHERE session_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, sessionID);

			rs = ps.executeQuery();

			if (rs.next()) {
				session = new Session(
						rs.getString("email"),
						rs.getString("session_id"),
						rs.getTimestamp("timeout") );
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {

			pool.closeConnection(connection);
		}

		return session;
	}

	/**
	 * Establishes a connection with the database and selects all the Sessions
	 * within the session table. It then adds them to a newly created ArrayList and
	 * returns that to the calling object.
	 * 
	 * @return ArrayList<Session> containing all of the Sessions from the database
	 */
	public static ArrayList<Session> getAll() {
		return null;
	}
}
