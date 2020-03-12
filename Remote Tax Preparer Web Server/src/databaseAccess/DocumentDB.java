package databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Document;
import domain.Parcel;

/**
 * 
 * Class Description: 	EH! Class that establishes a connection and communicates directly
 * 						with the document table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public class DocumentDB {

	/**
	 * Establishes a connection with the database and inserts the Document object passed
	 * into this method into the document table. The Document being inserted must be a
	 * part of a Parcel object.
	 * @param doc Document to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(Document doc, String parcelID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "INSERT INTO document "
					+ "VALUES (?, ?, ?, ?)";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, doc.getFilePath());
			ps.setString(2, parcelID);
			ps.setString(3, doc.getFileName());
			ps.setLong(4, doc.getFileSize());

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
	 * Establishes a connection with the database and removes the Document from the
	 * document table that has a Primary Key matching the filePath being passed
	 * into this method.
	 * @param filePath filePath of the Document to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(String filePath) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int rows = 0;

		try {

			String preparedQuery = "DELETE FROM document WHERE file_path = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, filePath);

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
	 * Establishes a connection with the database and selects the Document in the
	 * document table that has a Primary Key matching the filePath being passed
	 * into this method.
	 * @param filePath filePath of the Document to retrieve from the database
	 * @return Document that contains the information of the requested Document
	 */
	public static Document get(String filePath) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		Document document = null;

		try {

			String preparedQuery = "SELECT * FROM document WHERE file_path = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, filePath);

			rs = ps.executeQuery();

			if (rs.next()) {

				document = new Document(filePath, rs.getString("file_name"), rs.getLong("file_size"));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return document;
	}
	
	/**
	 * Establishes a connection with the database and selects the Document in the
	 * document table that has a Primary Key matching the filePath being passed
	 * into this method.
	 * @param parcelID parcelID of the Documents to retrieve from the database
	 * @return Documents that contains the documents of the requested parcelID
	 */
	public static ArrayList<Document> getByParcelID(String parcelID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<Document> documents = new ArrayList<>();

		try {

			String preparedQuery = "SELECT * FROM document WHERE parcel_id = ?";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			ps.setString(1, parcelID);

			rs = ps.executeQuery();

			if (rs.next()) {

				documents.add(new Document(rs.getString("file_path"), rs.getString("file_name"), rs.getLong("file_size")));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return documents;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Documents
	 * within the document table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Document> containing all of the Documents from the database
	 */
	public static ArrayList<Document> getAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ResultSet rs;
		ArrayList<Document> documents = new ArrayList<>();

		try {

			String preparedQuery = "SELECT * FROM document";

			PreparedStatement ps = connection.prepareStatement(preparedQuery);

			rs = ps.executeQuery();

			while (rs.next()) {

				documents.add(new Document(rs.getString("file_path"), rs.getString("file_name"), rs.getLong("file_size")));
			}
		}

		catch (SQLException e) {

			System.out.println(e);
		}

		finally {

			pool.closeConnection(connection);
		}

		return documents;
	}
}
