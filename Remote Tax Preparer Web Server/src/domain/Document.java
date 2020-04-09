package domain;

/**
 * Class Description: A record of files that have been encrypted and saved to the database.
 *
 * @author Tristen Kreutz, Cesar Guzman
 */
public final class Document {
	/**
	 * The universally unique id used to create the encrypted file's name.
	 */
	private transient String filePath;
	/**
	 * The name of the original file.
	 */
	private String fileName;
	/**
	 * The size of the file in bytes.
	 */
	private long fileSize;
	
	/**
	 * Constructs a complete document.
	 * 
	 * @param filePath String The universally unique id used to create the encrypted file's name.
	 * @param fileName String The name of the original file.
	 * @param fileSize long The size of the file in bytes.
	 */
	public Document(String filePath, String fileName, long fileSize) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileSize = fileSize;
	}

	/**
	 * Gets the universally unique id used to create the encrypted file's name.
	 * 
	 * @return filePath String The universally unique id used to create the encrypted file's name.
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Gets the name of the original file.
	 * @return filename String The name of the original file.
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Gets the size of the file in bytes.
	 * @return long The size of the file in bytes.
	 */
	public long getFileSize() {
		return fileSize;
	}
}
