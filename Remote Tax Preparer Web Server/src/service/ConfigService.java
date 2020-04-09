package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exception.ConfigException;

/**
 * A set of methods to get information from the config file.
 * 
 * @author Jesse Goerzen
 */
public final class ConfigService {
	/**
	 * The filepath to the config file.
	 */
	public static final String CONFIG_FILE_PATH = System.getProperty("catalina.home") + "/conf/wcl/config.txt";

	/**
	 * Fetches a <code>String</code> from the line with the given option from the config file.
	 * 
	 * @param option <code>String</code> The type of option to look for in the config file. Must include all characters on the line before the information stored in the file.
	 * @return <code>String</code> The piece of data stored in the config file including all characters after all the characters in option.
	 * @throws ConfigException if the config file cannot be found.
	 */
	public static String fetchFromConfig(String option) throws ConfigException {
		Scanner s = null;
		try {
			s = new Scanner(new File(CONFIG_FILE_PATH));
			String line = s.nextLine();
			while (s.hasNext() && !line.startsWith(option)) {
				line = s.nextLine();
			}
			s.close();
			if (line.startsWith(option))
				return line.substring(option.length());
			else {
				throw new ConfigException("End of config reached.");
			}
		} catch (FileNotFoundException e) {
			throw new ConfigException("Config file not found.");
		} finally {
			s.close();
		}
	}

	/**
	 * Fetches a <code>String</code> containing all the text in the given file.
	 * 
	 * @param filePath The path to the file whose contents to fetch.
	 * @return <code>String</code> The contents of the given file.
	 * @throws ConfigException if the given filePath cannot be found.
	 */
	public static String fetchContents(String filePath) throws ConfigException {
		Scanner s = null;
		try {
			s = new Scanner(new File(filePath));
			String line = s.nextLine();

			while (s.hasNext())
				line += "\n" + s.nextLine();

			return line;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ConfigException("Config file not found.");
		} finally {
			try {
				s.close();
			} catch (NullPointerException e) {
			}
		}
	}

}
