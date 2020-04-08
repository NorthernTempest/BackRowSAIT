package manager;

import databaseAccess.DocumentDB;
import databaseAccess.ParcelDB;
import domain.Document;
import domain.Parcel;
import exception.ConfigException;

/**
 * 
 * Class Description: 	Class that communicates with the DocumentDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public class DocumentManager {

	/**
	 * Retrieves and returns the document with the passed file path.
	 * @param filePath file path of document to retrieve
	 * @return document
	 */
	public static Document get(String filePath) {
		return DocumentDB.get(filePath);
	}
	
	/**
	 * Deletes documents from a specific parcel that is identified by the parcel ID
	 * that is passed.
	 * @param parcelID parcelID to set
	 * @return boolean was operation successful
	 */
	public static boolean delete(String parcelID) {
		Parcel parcel = null;
		boolean complete = true;
		try {
			parcel = ParcelManager.get(parcelID);
		} catch (ConfigException e) {
			e.printStackTrace();
			return false;
		}
		
		for(Document document : parcel.getDocuments()) {
			if(!DocumentDB.delete(document.getFilePath())) {
				complete = false;
			}
			
		}
		return complete;
	}
}
