package manager;

import java.util.ArrayList;

import domain.Document;

/**
 * 
 * Class Description: 	Class that communicates with the DocumentDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class DocumentManager {
	
	/**
	 * Takes the Document object passed into the method and calls the insert method
	 * of the DocumentDB, passing the Document.
	 * @param doc Document to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Document doc) {
		return false;
	}
	
	/**
	 * Takes the Document object passed into the method and calls the update method
	 * of the DocumentDB, passing the Document.
	 * @param doc Document in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Document doc) {
		return false;
	}
	
	/**
	 * Takes the filePath String passed into the method and calls the delete method
	 * of the DocumentDB, passing the filePath.
	 * @param filePath filePath of the Document in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(String filePath) {
		return false;
	}
	
	/**
	 * Takes the filePath String passed into the method and calls the get method
	 * of the DocumentDB, passing the filePath.
	 * @param filePath filePath of the Document in the database to retrieve
	 * @return Document containing the information of the requested Document
	 */
	public Document get(String filePath) {
		return null;
	}
	
	/**
	 * Calls the getAll method of the DocumentDB.
	 * @return ArrayList<Document> containing all the Documents in the database
	 */
	public ArrayList<Document> getAll() {
		return null;
	}
}
