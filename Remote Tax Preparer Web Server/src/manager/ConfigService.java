package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exception.ConfigException;

public final class ConfigService {

	public static final String CONFIG_FILE_PATH = "res/config.txt";

	/**
	 * 
	 * @param option
	 * @return
	 */
	public static String fetchFromConfig(String option) {
		Scanner s = null;
		try {
			s = new Scanner(new File(CONFIG_FILE_PATH));
			String line = s.nextLine();
			while (s.hasNext() && !line.startsWith(option)) {
				line = s.nextLine();
			}
			if (line.startsWith(option))
				return line.substring(option.length());
			else
				throw new ConfigException("End of config reached.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ConfigException( "Config not found." );
		}
		finally
		{
			s.close();
		}
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static String fetchContents(String filePath) {
		Scanner s = null;
		try {
			s = new Scanner(filePath);
			String line = s.nextLine();

			while (s.hasNext()) {
				line += s.nextLine();
			}

			return line;
		}
		finally
		{
			s.close();
		}
	}

}
