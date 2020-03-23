package manager;

import databaseAccess.DocumentDB;
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

	public static Document get(String filePath) {
		return DocumentDB.get(filePath);
	}
	
	
}
