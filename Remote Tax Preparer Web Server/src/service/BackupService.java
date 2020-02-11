package service;

/**
 * Class Description: 	Service manager class that handles backup and restore operations of the entire
 * 						system.
 *
 * @author Tristen Kreutz
 *
 */
public class BackupService {

	/**
	 * Creates a backup of the system and saves the backup to the specified location.
	 * @param filepath filepath to save to
	 * @return boolean based on whether or not the operation was a success
	 */
	public boolean backup(String filepath) {
		return false;
	}
	
	/**
	 * Restores the system to a previous backup that is specified by the user.
	 * @param filepath filepath of the backup to restore to
	 * @return boolean based on whteher or not the operation was a success
	 */
	public boolean restore(String filepath) {
		return false;
	}
}
