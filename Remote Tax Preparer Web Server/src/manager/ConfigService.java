package manager;

import java.util.Scanner;

public class ConfigService {
	
	public static final String CONFIG_FILE_PATH = "res/config.txt";

	/**
	 * 
	 * @param option
	 * @return
	 */
	public static String fetchFromConfig(String option) {
		try (Scanner s = new Scanner( CONFIG_FILE_PATH )) {
			String line = s.nextLine();
	
			while (line != null && !line.equals("") && !line.startsWith(option)) {
				line = s.nextLine();
			}
	
			return line.substring(option.length());
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
	
			while (line != null && !line.equals("")) {
				line += s.nextLine();
			}
	
			return line;
		}
	}

}
