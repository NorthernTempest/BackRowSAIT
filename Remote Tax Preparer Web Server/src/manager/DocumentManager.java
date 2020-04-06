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

	public static Document get(String filePath) {
		return DocumentDB.get(filePath);
	}
	
	/**
	 * @param parcelID
	 * @return
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
