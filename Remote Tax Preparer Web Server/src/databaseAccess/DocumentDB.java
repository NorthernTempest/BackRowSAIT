package databaseAccess;

import java.util.ArrayList;

import domain.Document;

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
	public boolean insert(Document doc) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and updates the Document in the database
	 * table that shares a Primary Key with the Document being passed into this method.
	 * All values of the Document in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param doc Document to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Document doc) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the Document from the
	 * document table that has a Primary Key matching the filePath being passed
	 * into this method.
	 * @param filePath filePath of the Document to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(String filePath) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the Document in the
	 * document table that has a Primary Key matching the filePath being passed
	 * into this method.
	 * @param filePath filePath of the Document to retrieve from the database
	 * @return Document that contains the information of the requested Document
	 */
	public Document get(String filePath) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Documents
	 * within the document table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Document> containing all of the Documents from the database
	 */
	public ArrayList<Document> getAll() {
		return null;
	}
}
