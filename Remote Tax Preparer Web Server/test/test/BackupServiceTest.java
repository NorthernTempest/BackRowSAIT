package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import exception.ConfigException;
import service.BackupService;

public class BackupServiceTest {
	
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