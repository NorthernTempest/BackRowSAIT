package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import exception.ConfigException;
import service.BackupService;

/**
 * A set of tests for backing up and restoring the system.
 * 
 * @author Jesse Goerzen
 */
public class BackupServiceTest {
	
	/**
	 * A test case for backing up the system.
	 * 
	 * @throws FileNotFoundException if any of the files cannot be accessed.
	 * @throws IOException if the zip file fails to be created.
	 * @throws ConfigException if the Config file cannot be found.
	 * @throws InterruptedException if the sql backup process is interrupted.
	 */
	@Test
	void backupTest() throws FileNotFoundException, IOException, ConfigException, InterruptedException {
		try (FileOutputStream fos = new FileOutputStream( "res/backup.zip" ) ) {
			try {
				assertTrue( BackupService.backup(fos) );
			} catch (FileNotFoundException e) {
				throw e;
			} catch (ConfigException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} catch (InterruptedException e) {
				throw e;
			}
		}
	}
	
	/**
	 * A test case for restoring the system from a zip file.
	 * 
	 * @throws FileNotFoundException if any of the files cannot be created.
	 * @throws IOException if the zip file fails to be created.
	 * @throws ConfigException if the Config file cannot be found.
	 * @throws InterruptedException if the sql restoration process is interrupted.
	 */
	@Test
	void recoverTest() throws FileNotFoundException, IOException, ConfigException, InterruptedException {
		try (FileInputStream fis = new FileInputStream( "res/backup.zip" )) {
			try {
				assertTrue( BackupService.restore(fis) );
			} catch (FileNotFoundException e) {
				throw e;
			} catch (ConfigException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} catch (InterruptedException e) {
				throw e;
			}
		}
	}
}