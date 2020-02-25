package databaseAccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the logEntry table in the database.
 *
 * @author Tristen Kreutz
 *
 */
import java.util.ArrayList;

import domain.LogEntry;

public class LogEntryDB {

	/**
	 * Establishes a connection with the database and inserts the LogEntry object passed
	 * into this method into the logEntry table.
	 * @param logEntry LogEntry to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(LogEntry logEntry) {
		
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;
		
		try {
			
			String preparedQuery = "INSERT INTO log (log_id, email, type, message, date, "
									+ "VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement ps = connection.prepareStatement(preparedQuery);
			
			ps.setInt(1, logEntry.getLogEntryID());
			ps.setString(2, logEntry.getEmail());
			ps.setString(3, logEntry.getType() + "");
			ps.setString(4, logEntry.getMessage());
			ps.setDate(5, new Date( logEntry.getDate().getTime() ));
			
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
	 * Establishes a connection with the database and selects the LogEntry in the
	 * logEntry table that has a Primary Key matching the logEntryID being passed
	 * into this method.
	 * @param logEntryID logEntryID of the LogEntry to retrieve from the database
	 * @return LogEntry that contains the information of the requested Log
	 */
	public static LogEntry get(int logEntryID) {
		
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs = null;
		LogEntry logEntry = null;
		
		try {
			
			String preparedQuery = "SELECT * FROM log WHERE log_id = ?";
			
			PreparedStatement ps = connection.prepareStatement(preparedQuery);
			
			ps.setInt(1, logEntryID);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				logEntry = new LogEntry(logEntryID, rs.getString("email"), rs.getString("message"), rs.getString("type").charAt(0),
										rs.getDate("date"), rs.getString("ip"));
			}
		}
		
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
		finally {
			
			pool.closeConnection(connection);
		}
		
		return logEntry;
	}
	
	/**
	 * Establishes a connection with the database and selects all Logs in the
	 * logEntry table that have parameters matching the parameters being passed
	 * into this method.
	 * @param email
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param ip
	 * @return
	 */
	public static ArrayList<LogEntry> getByParameters(String email, char type, java.util.Date startDate, java.util.Date endDate, String ip){
		/*
		 * note to whoever is writing this code, do some clever string appends within 
		 * if statements here to get the select statement right.
		 * null should be a wildcard so if(param = null) dont append.
		 */
		
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs = null;
		LogEntry logEntry = null;
		ArrayList<LogEntry> logEntries = new ArrayList<>();
		String preparedQuery;
		
		try {
			
			if (email == null && !Character.isLetter(type) && startDate == null && endDate == null && ip == null) {
				preparedQuery = "SELECT * FROM log";
				PreparedStatement ps = connection.prepareStatement(preparedQuery);
				rs = ps.executeQuery();
			}
			
			else {
				
				ArrayList<String> parameters = new ArrayList<>();
				int count = 0;
				preparedQuery = "SELECT * FROM log WHERE ";
				
				if (email != null) {
					preparedQuery += "email = ? ";
					parameters.add(email);
					count++;
				}
				
				if (Character.isLetter(type)) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "type = ? ";
					parameters.add(type + "");
					count++;
				}
				
				if (startDate != null && endDate != null) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "date BETWEEN ? AND ? ";
					parameters.add(startDate.toString());
					parameters.add(endDate.toString());
					count++;
				}
					
				if (ip != null) {
					if (count > 0)
						preparedQuery += "AND ";
					preparedQuery += "ip = ? ";
					parameters.add(ip);
				}
				
				PreparedStatement ps = connection.prepareStatement(preparedQuery);
				
				for (int i = 0; i < parameters.size(); i++) {
					
					ps.setString(i + 1, parameters.get(i));
				}
				
				rs = ps.executeQuery();
			}
			
			while (rs.next()) { 
				
				logEntry = new LogEntry(rs.getInt("log_id"), rs.getString("email"), rs.getString("message"),
						rs.getString("type").charAt(0), rs.getDate("date"), rs.getString("ip"));
				
				logEntries.add(logEntry);
			}
		}
		
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
		finally {
			
			pool.closeConnection(connection);
		}
		
		return logEntries;
	}
	
	/**
	 * Establishes a connection with the database and selects all Logs in the
	 * logEntry table that has an email matching the email being passed
	 * into this method.
	 * @param email email of the Logs to retrieve from the database
	 * @return ArrayList<Log> that contains information of the requested Logs
	 */
	public static ArrayList<LogEntry> getByEmail(String email) {
		
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs = null;
		LogEntry logEntry = null;
		ArrayList<LogEntry> logEntries = new ArrayList<>();
		
		try {
			
			String preparedQuery = "SELECT * FROM log WHERE email = ?";
			
			PreparedStatement ps = connection.prepareStatement(preparedQuery);
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while (rs.next()) { 
				
				logEntry = new LogEntry(rs.getInt("log_id"), rs.getString("email"), rs.getString("message"),
						rs.getString("type").charAt(0), rs.getDate("date"), rs.getString("ip"));
				
				logEntries.add(logEntry);
			}
		}
		
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
		finally {
			
			pool.closeConnection(connection);
		}
		
		return logEntries;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Logs
	 * within the logEntry table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<LogEntry> containing all of the Logs from the database
	 */
	public static ArrayList<LogEntry> getAll() {
		
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs = null;
		LogEntry logEntry = null;
		ArrayList<LogEntry> logEntries = new ArrayList<>();
		
		try {
			
			String preparedQuery = "SELECT * FROM log";
			
			PreparedStatement ps = connection.prepareStatement(preparedQuery);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				logEntry = new LogEntry(rs.getInt("log_id"), rs.getString("email"), rs.getString("message"), 
										rs.getString("type").charAt(0), rs.getDate("date"), rs.getString("ip"));
				
				logEntries.add(logEntry);
			}
		}
		
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
		finally {
			
			pool.closeConnection(connection);
		}
		
		return logEntries;
	}
}
