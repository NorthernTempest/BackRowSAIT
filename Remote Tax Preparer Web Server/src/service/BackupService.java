package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import exception.ConfigException;

/**
 * Class Description: Service manager class that handles backup and restore
 * operations of the entire system.
 *
 * @author Tristen Kreutz, Jesse Goerzen
 *
 */
public final class BackupService {

	/**
	 * The directory used to contain the encrypted files.
	 */
	private static String encryptedFilesDirectoryPath;
	/**
	 * The filepath to the sql script used to recover the database to an earlier state.
	 */
	private static String dumpFile;
	/**
	 * The command to dump the entirety of the database to an sql script.
	 */
	private static String dumpCommand;
	/**
	 * The command to recover the database from an sql script.
	 */
	private static String recoverCommand;
	/**
	 * The username used to access the database.
	 */
	private static String username;
	/**
	 * The password used to access the database.
	 */
	private static String password;
	/**
	 * Whether the private static methods in BackupService are initialized.
	 */
	private static boolean init = false;
	
	/**
	 * Initializes the private static methods in BackupService.
	 * 
	 * @throws ConfigException if the config file is missing.
	 */
	private static void init() throws ConfigException {
		if (!init) {
			encryptedFilesDirectoryPath = ConfigService.fetchFromConfig("encfiles:");
			dumpFile = ConfigService.fetchFromConfig("dumpfile:");
			dumpCommand = ConfigService.fetchFromConfig("dumpcommand:");
			recoverCommand = ConfigService.fetchFromConfig("recovercommand:");
			username = ConfigService.fetchContents(ConfigService.fetchFromConfig("sqladminusernamepath:"));
			password = ConfigService.fetchContents(ConfigService.fetchFromConfig("sqladminpasswordpath:"));

			init = true;
		}
	}

	/**
	 * Creates a backup of the system and saves the backup to the specified
	 * location.
	 * 
	 * @return boolean based on whether or not the operation was a success
	 * @throws ConfigException       if the config file is missing.
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 */
	public static boolean backup(OutputStream out)
			throws ConfigException, FileNotFoundException, IOException, InterruptedException {
		init();
		boolean output = false;

		// Zip up encrypted files.
		ZipOutputStream zipOut = new ZipOutputStream(out);
		File toZip = new File(encryptedFilesDirectoryPath);

		output = zip(toZip, toZip.getName(), zipOut);

		// Dump database to a script.
		output = output && dump() == 0;

		// Add dump to zip.
		toZip = new File(dumpFile);

		output = output && zip(toZip, toZip.getName(), zipOut);

		zipOut.close();

		toZip.delete();

		return output;
	}

	/**
	 * Restores the system to a previous backup that is specified by the user.
	 * 
	 * @param filepath filepath of the backup to restore to
	 * @return boolean based on whteher or not the operation was a success
	 * @throws ConfigException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static boolean restore(InputStream in) throws ConfigException, FileNotFoundException, IOException, InterruptedException {
		init();
		boolean output = false;

		// unzip files
		ZipInputStream zipIn = new ZipInputStream(in);
		ZipEntry zipEntry = zipIn.getNextEntry();
		
		boolean dumpFound = false;
		
		File encryptedFilesDirectory = new File( encryptedFilesDirectoryPath );
		
		while (zipEntry != null) {
			
			File newFile = null;
			if (zipEntry.getName().endsWith(".secure") && zipEntry.getName().startsWith(encryptedFilesDirectory.getName())) {
				
				newFile = new File(encryptedFilesDirectory.getParent(), zipEntry.getName());
			} else if (zipEntry.getName().endsWith(".sql")) {
				if( !dumpFound ) {
					newFile = new File(dumpFile);
					dumpFound = true;
				} else
					throw new IOException("Multiple dump files provided.");
			}
			if (newFile != null) {
				FileOutputStream fos = new FileOutputStream(newFile);

				IOUtils.copy(zipIn, fos);

				fos.close();
			}
			zipEntry = zipIn.getNextEntry();
		}
		zipIn.closeEntry();
		zipIn.close();
		
		if(dumpFound) {
			output = recover() == 0;
			new File(dumpFile).delete();
		}

		return output;
	}
	
	/**
	 * Adds files and directories to the given ZipOutputStream. Recursively adds the contents of directories too.
	 * 
	 * @param toZip File The file to be added to the ZipOutputStream.
	 * @param fileName String The entire path to place the file in inside the zip file.
	 * @param zipOut ZipOutputStream The ZipOutputStream to be added to.
	 * @return true if the file and all its contents were added to the drive.
	 * @throws FileNotFoundException if one of the files to be added are not found.
	 * @throws IOException if the file failed to be added to the zip file.
	 */
	private static boolean zip(File toZip, String fileName, ZipOutputStream zipOut)
			throws FileNotFoundException, IOException {
		boolean output = true;
		if (toZip.isHidden())
			return true;
		if (toZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			File[] children = toZip.listFiles();
			for (File child : children) {
				output = output && zip(child, fileName + "/" + child.getName(), zipOut);
			}

			return output;
		}
		try (FileInputStream fis = new FileInputStream(toZip)) {
			ZipEntry zipEntry = new ZipEntry(fileName);
			zipOut.putNextEntry(zipEntry);
			IOUtils.copy(fis, zipOut);
		} catch (FileNotFoundException e) {
			FileNotFoundException ex = new FileNotFoundException(
					toZip.getName() + " could not be added to the zip file");
			ex.initCause(e);
			throw ex;
		} catch (IOException e) {
			throw new IOException(toZip.getName() + " could not be added to the zip file", e);
		}
		return output;
	}
	
	/**
	 * Creates an sql scirpt containing all the database data.
	 * 
	 * @return int the exit value of the process that 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static int dump() throws IOException, InterruptedException {

		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		ProcessBuilder pb = new ProcessBuilder();

		File f = new File(dumpFile);

		String command = dumpCommand.replaceAll("%user%", username).replaceAll("%pass%", password).replaceAll("%file%",
				dumpFile);

		if (isWindows) {
			pb.command("cmd.exe", "/c", command);
		} else {
			pb.command("sh", "-c", command);
		}

		pb.directory(f.getParentFile());

		Process p = pb.start();

		Thread t = new Thread(new Runnable() {

			InputStream in;
			Consumer<String> consumer;

			@Override
			public void run() {
				new BufferedReader(new InputStreamReader(in)).lines().forEach(consumer);
			}

			public Runnable init(InputStream in, Consumer<String> consumer) {
				this.in = in;
				this.consumer = consumer;
				return this;
			}

		}.init(p.getErrorStream(), System.out::println));

		t.start();

		return p.waitFor();
	}
	
	/**
	 * Recovers the database from the dump file.
	 * 
	 * @return int The exit value of the recovery command.
	 * @throws InterruptedException if the process that recovers the database is interrupted.
	 * @throws IOException if the directory containing the dump file is not a valid directory.
	 */
	private static int recover() throws InterruptedException, IOException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		
		ProcessBuilder pb = new ProcessBuilder();
		
		File f = new File(dumpFile);
		
		String command = recoverCommand.replaceAll("%user%", username).replaceAll("%pass%", password).replaceAll("%file%", dumpFile);
		
		if (isWindows) {
			pb.command("cmd.exe", "/c", command);
		} else {
			pb.command("sh", "-c", command);
		}
		
		pb.directory(f.getParentFile());
		
		Process p = pb.start();
		
		Thread t = new Thread(new Runnable() {
			
			InputStream in;
			Consumer<String> consumer;

			@Override
			public void run() {
				new BufferedReader(new InputStreamReader(in)).lines().forEach(consumer);
			}

			public Runnable init(InputStream in, Consumer<String> consumer) {
				this.in = in;
				this.consumer = consumer;
				return this;
			}
			
		}.init(p.getErrorStream(), System.out::println));
		
		t.start();
		
		return p.waitFor();
	}
}
