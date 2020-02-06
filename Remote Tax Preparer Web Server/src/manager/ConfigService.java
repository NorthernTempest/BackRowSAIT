package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigService {

	public static final String CONFIG_FILE_PATH = "res/config.txt";

	/**
	 * 
	 * @param option
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String fetchFromConfig(String option) throws FileNotFoundException {
		try (Scanner s = new Scanner(new File(CONFIG_FILE_PATH))) {
			String line = s.nextLine();
			while (s.hasNext() && !line.startsWith(option)) {
				line = s.nextLine();
			}
			if (line.startsWith(option))
				return line.substring(option.length());
			else
				throw new IndexOutOfBoundsException("End of config reached.");
		}
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static String fetchContents(String filePath) {
		try (Scanner s = new Scanner(filePath)) {
			String line = s.nextLine();

			while (s.hasNext()) {
				line += s.nextLine();
			}

			return line;
		}
	}

}
