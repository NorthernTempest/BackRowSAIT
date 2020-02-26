package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exception.ConfigException;

public final class ConfigService {

	public static final String CONFIG_FILE_PATH = "C:\\Capstone\\BackRowSAIT\\Remote Tax Preparer Web Server\\res\\config.txt";

	/**
	 * 
	 * @param option
	 * @return
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
	 * 
	 * @param filePath
	 * @return
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
