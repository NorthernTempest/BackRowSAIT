package service;

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
			s = new Scanner(CONFIG_FILE_PATH);
			String line = s.nextLine();
			while (s.hasNext() && !line.startsWith(option)) {
				line = s.nextLine();
			}
			s.close();
			if (line.startsWith(option))
				return line.substring(option.length());
			else
				throw new ConfigException("End of config reached.");
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
			s = new Scanner(filePath);
			String line = s.nextLine();

			while (s.hasNext()) {
				line += s.nextLine();
			}

			return line;
		} finally {
			s.close();
		}
	}

}
